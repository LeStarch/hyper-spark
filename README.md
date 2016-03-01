# Apache Spark at High-Speed

This is Spark running at 10Gb/s!
**Note:** The researched that created this work concluded with presentations at ApacheCon Europe 2015.

## Project  Goal
The goal of this project is to get a single stream in Apache Spark Streaming processing to a throughput of ~10Gb/s. To be clear, this is a single "processor" or "thread" (one map-reduce pipeline). It is independent of the inherant parallelism of the Map-Reduce style processing of Apache Spark.

**Why?** Given that Apache Spark is map-reduce style programing, why negate that to focusing on a single pipeline? In order to target the next-generation of distributed computing, it is not enough to use just parallelism. One must ensure that each of the individual pipelines must be optimized for maximum-thoughput and then parallelized. In order to support a project running on 40Gb/s networks and producing 13+ Gb/s of data, individual streams must process at ~1Gb/s.

## Implementation Quirks

JVM network stack provides an inefficient interface. The interface allows the reading on individual bytes from a Socket, and thus requries many function calls and a lot of copying of single bytes.

Thus a JNI solution that can read blocks of data off a Berkley socket was used in order to ensure that the networking layer runs at peak performance.
- https://github.com/LeStarch/hyper-spark/blob/master/src/main/java/org/dia/benchmarking/spark/jni/FastNetwork.java
- https://github.com/LeStarch/hyper-spark/blob/master/src/main/c/org_dia_benchmarking_spark_jni_FastNetwork.c

Originally, the implementation was intended to run Fourier tansforms at high-speed. However, in order to achieve bare-bone efficiency this code as been disabled.

## Current State

At the conclusion of the research, an individual Spark pipeline was recorded at processing around 500MB/s.  Much of the Fourier processing code is commented out in order to get bare-bones efficiency.

## Supported Research
- High-Throughput Apache Spark Streaming
  - At Apache Con Europe (http://sched.co/400u)
- High-Throughput Kafka and Kafka in HPC https://github.com/LeStarch/kafka-benchmarking

