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
    private int conn = -1;
    public FastNetwork(String host,int port,Type type) throws IOException {
        this.conn = openX(host,port,type == Type.CLIENT?0:1);
        System.out.println("Opened port with file handler:"+this.conn);
        if (this.conn < 0) {
            throw new IOException("Failed to connect to: "+host+":"+port);
        }
    }
    public byte[] read() {
        return readX(this.conn);
    }
    public int write(byte[] data) {
        return writeX(this.conn,data);
    }
    public void close() {
        closeX(this.conn);
    }
    //Native functions
    private native int openX(String host,int port,int type);
    public native byte[] readX(int conn);
    public native int writeX(int conn,byte[] out);
    public native void closeX(int conn);
    
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
