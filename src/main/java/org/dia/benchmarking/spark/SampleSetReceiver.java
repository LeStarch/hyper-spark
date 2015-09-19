package org.dia.benchmarking.spark;

import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.receiver.Receiver;
import org.dia.benchmarking.spark.jni.FastNetwork;

public class SampleSetReceiver extends Receiver<byte[]> {

    public static final int SAMPLE_SIZE = 2048;
    public static final int BYTE_ARRAY_SIZE = SAMPLE_SIZE*8;
    
    private static final long serialVersionUID = 1L;
    private String host;
    private int port;
    
    public SampleSetReceiver(StorageLevel storage,String hostname,int port) throws UnknownHostException, IOException {
        super(storage);
        this.host = hostname;
        this.port = port;
    }

    @Override
    public void onStart() {
        try {
            Thread thread = new Thread(new ReadSocketThread(this.host,this.port));
            thread.start();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {}

    class ReadSocketThread implements Runnable {
        private static final long serialVersionUID = 1L;
        FastNetwork sock;
        
        ReadSocketThread(String host, int port) throws UnknownHostException, IOException {
            this.sock = new FastNetwork(host,port,FastNetwork.Type.CLIENT);
        }
        
        @Override
        public void run() {
            try {
                while (!SampleSetReceiver.this.isStopped()) {
                    byte[] bytes = this.sock.read();
                    if (bytes.length <= 0) {
                        throw new IOException("Bad read from connection");
                    }
                    SampleSetReceiver.this.store(bytes);
                }
            } catch(IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
        
        public void close() {
            this.sock.close();
        }
    }
}
