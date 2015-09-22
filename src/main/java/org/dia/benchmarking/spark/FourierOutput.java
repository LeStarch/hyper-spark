package org.dia.benchmarking.spark;


import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.spark.api.java.function.Function;
import org.dia.benchmarking.spark.jni.FastNetwork;

public class FourierOutput implements Function<byte[],Void> {
    private static final long serialVersionUID = 1L;
    WriteSocketThread writer = null;
    int port;
    String host;
    public FourierOutput(String host,int port) {
        this.host = host;
        this.port = port;
    }
    private void setup() throws IOException {
        if (this.writer == null) {
            this.writer =  new WriteSocketThread(this.host,this.port);
            new Thread(this.writer).start();
        }
    }
    
    @Override
    public Void call(byte[] output) throws Exception {
        setup();
        this.writer.queueWrite(output);
        return null;
    }

    class WriteSocketThread implements Runnable {
        private static final long serialVersionUID = 1L;
        ConcurrentLinkedQueue<byte[]> queue;
        FastNetwork sock;
        
        WriteSocketThread(String host,int port) throws UnknownHostException, IOException {
            this.queue = new ConcurrentLinkedQueue<byte[]>();
            this.sock = new FastNetwork(host,port,FastNetwork.Type.CLIENT);
        }
        
        public void queueWrite(byte[] data) {
            this.queue.add(data);
        }
        
        
        @Override
        public void run() {
            while (true) {
                byte[] data = null;
                while(null == (data = this.queue.poll()));
                this.sock.write(data);
             }
        }
        
        public void close() {
            this.sock.close();
        }
    }
}
