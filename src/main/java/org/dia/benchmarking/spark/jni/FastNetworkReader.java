package org.dia.benchmarking.spark.jni;

import java.io.IOException;

public class FastNetworkReader {
    //Load up the dynamic C library
    static {
        System.out.println("Loading libs from: "+System.getProperty("java.library.path"));
        System.loadLibrary("fastread");
     }
    public FastNetworkReader(String host, int port) throws IOException {
        int i = open(host,port);
        if (i < 0) {
            throw new IOException("Failed to connect to: "+host+":"+port);
        }
    }

    //Native functions
    private native int open(String host,int port);
    public native byte[] read();
    public native void close();
    
    public static void main(String[] args) {
        
        try {
            FastNetworkReader fn = new FastNetworkReader(args[0],Integer.parseInt(args[1]));
            System.out.println("Opened connection");
            byte[] o = fn.read(); 
            fn.close();
        } catch (IOException e) {
            System.out.println("IO Exception: "+e.getMessage());
        }
    }

}
