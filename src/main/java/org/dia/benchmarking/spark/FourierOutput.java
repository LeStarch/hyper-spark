package org.dia.benchmarking.spark;


import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.spark.api.java.function.Function;

public class FourierOutput implements Function<byte[],Void> {
    private static final long serialVersionUID = 1L;
    ServerSocket ssock;
    Socket sock;
    OutputStream os;
    int port;
    
    public FourierOutput(int port) {
        this.port = port;
    }
    private void setup() throws IOException {
        this.ssock = new ServerSocket(this.port);
        this.sock = this.ssock.accept();
        this.os = this.sock.getOutputStream();
    }
    
    @Override
    public Void call(byte[] output) throws Exception {
        setup();
        this.os.write(output);
        return null;
    }
    
    public void close() {
        try {
            this.os.close();
            this.sock.close();
            this.ssock.close();
        } catch (IOException e) {}
    }
}