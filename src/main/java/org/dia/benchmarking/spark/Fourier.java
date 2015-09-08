package org.dia.benchmarking.spark;

import java.nio.ByteBuffer;

import org.apache.spark.api.java.function.Function;
import org.nd4j.linalg.api.complex.IComplexNDArray;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.fft.FFT;

public class Fourier implements Function<byte[],byte[]> {
    private static final long serialVersionUID = 1L;

    @Override
    public byte[] call(byte[] in) throws Exception {
        double[] timeSeries = new double[in.length>>3];
        for (int i = 0; i < timeSeries.length; i++) {
            timeSeries[i] = ByteBuffer.wrap(in, i*8, 8).getDouble();
        }
        INDArray input = Nd4j.create(timeSeries,new int[]{timeSeries.length});
        IComplexNDArray output = FFT.fft(input);
        byte[] out = new byte[in.length];
        for (int i = 0; i < output.length();i++) {
            ByteBuffer.wrap(out, i*8, 8).putDouble(output.getReal(i));
        }
        return out;
    } 

    public static void main(String[] args) {
        Fourier f = new Fourier();
        try {
            byte[] out = f.call(new byte[]{125,120,12,6,33,103,21,12,-120,-34,1,2,3,4,5,6,7,8,9,0,1,1,1,1,1,0,0,0,0,0,0,0,1,1,1,1,1,10,2,2});
            System.out.print("Bytes:");
            for (int i = 0; i < out.length;i++)
                System.out.print(out[i]+" ");
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}