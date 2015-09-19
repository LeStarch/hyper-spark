#!/bin/sh
cd `dirname $0`
rm -r ../hyper-spark-0.1;
mvn clean install
javah -classpath target/classes/ -d src/main/c/ org.dia.benchmarking.spark.jni.FastNetwork
make clean; make
tar -xzf target/hyper-spark-0.1-dist.tar.gz -C ..
mv libfastread.so ../hyper-spark-0.1/lib/
