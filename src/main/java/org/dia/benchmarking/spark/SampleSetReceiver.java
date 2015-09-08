package org.dia.benchmarking.spark;

import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.receiver.Receiver;

public class SampleSetReceiver extends Receiver<byte[]> {

    public static final int SAMPLE_SIZE = 10;
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
        Socket sock;
        byte[] bytes = new byte[SampleSetReceiver.BYTE_ARRAY_SIZE];
        
        ReadSocketThread(String host, int port) throws UnknownHostException, IOException {
            this.sock = new Socket(host,port);
        }
        
        @Override
        public void run() {
            try {

                InputStream is = this.sock.getInputStream();
                int total = 0;
                while (!SampleSetReceiver.this.isStopped()) {
                    total = 0;
                    while (total < bytes.length) {
                        total += is.read(this.bytes, total, this.bytes.length - total);
                    }
                    SampleSetReceiver.this.store(bytes);
                    this.bytes = new byte[SampleSetReceiver.BYTE_ARRAY_SIZE];
                }
            } catch(IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
        
        public void close() {
            try {
                this.sock.close();
            } catch (IOException e) {}
        }
    }
}
