package org.dia.benchmarking.spark.jni;

import java.io.IOException;


public class FastNetwork {
    //Load up the dynamic C library
    static {
        System.out.println("Loading libs from: "+System.getProperty("java.library.path"));
        System.loadLibrary("fastread");
     }
    public enum Type {
        CLIENT,
        SERVER
    }

    public FastNetwork(String host,int port,Type type) throws IOException {
        int i = open(host,port,type == Type.CLIENT?0:1);
        System.out.println("Opened port with file handler:"+i);
        if (i < 0) {
            throw new IOException("Failed to connect to: "+host+":"+port);
        }
    }

    //Native functions
    private native int open(String host,int port,int type);
    public native byte[] read();
    public native int write(byte[] out);
    public native void close();
    
    public static void main(String[] args) {
        
        try {
            FastNetwork fn = new FastNetwork(args[0],Integer.parseInt(args[1]),Type.CLIENT);
            System.out.println("Opened connection");
            byte[] o = fn.read();
            String in = new String(o,"US-ASCII");
            System.out.println("Length: "+o.length+" Value: "+in);
            in = in + "ACKED!";
            fn.write(in.getBytes("US-ASCII"));
            fn.close();
        } catch (IOException e) {
            System.out.println("IO Exception: "+e.getMessage());
        }
    }

}
