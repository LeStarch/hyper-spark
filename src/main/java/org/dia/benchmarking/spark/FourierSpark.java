package org.dia.benchmarking.spark;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import java.io.IOException;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;

public class FourierSpark {

    /**
     * Setup the driver
     */
    public static void driver(String inHost, int inPort,String master,String outHost, int outPort) {
        try {
            int duration = 100;
            String[] jars = new String[]{FourierSpark.class.getProtectionDomain().getCodeSource().getLocation().getPath()};
            //Math to run
            final Fourier fourier = new Fourier();
            //Communication in and out
            System.out.println("Attempting to gain input from: "+inHost+":"+inPort);
            System.out.println("Attempting to put output to: "+outHost+":"+outPort);
        
            final FourierOutput outFn = new FourierOutput(outHost,outPort);
            SampleSetReceiver samples = new SampleSetReceiver(StorageLevel.MEMORY_ONLY(),inHost,inPort);
            System.out.println("Starting up spark with duration: "+duration+" Sample size: "+SampleSetReceiver.SAMPLE_SIZE);
            //Spark configuration
            SparkConf c = new SparkConf();
            c.setAppName("Starch is Great.APP");
            c.setMaster(master);
            c.setJars(jars);
            c.set("spark.executor.extraLibraryPath","/home/03544/tg828439/hyper-spark-0.1/lib/");
            c.set("spark.driver.memory","100g");
            c.set("spark.driver.cores","24");
            c.set("spark.executor.memory","100g");
            c.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer");
            c.set("spark.kryoserializer.buffer.max", "512m");
            c.set("spark.kryoserializer.buffer", "512m");
            c.set("spark.kryoserializer.referenceTracking", "false");
            JavaSparkContext sc = new JavaSparkContext(c);
            JavaStreamingContext ssc = new JavaStreamingContext(sc, new Duration(duration));
            //Processing chain
            JavaDStream<byte[]> stream = ssc.receiverStream(samples);
            JavaDStream<byte[]> output = stream.map(fourier);
            output.foreachRDD(new Function<JavaRDD<byte[]>,Void>() 
                {
                    private static final long serialVersionUID = 1L;
                    @Override
                    public Void call(JavaRDD<byte[]> rdd) throws Exception {
                        for (byte[] bytes : rdd.collect()) {
                            outFn.call(bytes);
                        }
                        return null;
                    }                    
                });
            ssc.start();
            ssc.awaitTermination();
            ssc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        if (args.length != 5) {
            System.err.println("Usage:\n\tprogram <host> <input port> <spark master> <output host> <output port>");
            System.exit(-1);
        }
        driver(args[0],Integer.parseInt(args[1]),args[2],args[3],Integer.parseInt(args[4]));
    }
}
