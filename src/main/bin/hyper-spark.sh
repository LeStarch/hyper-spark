#!/bin/sh
cd `dirname $0`
${JAVA_HOME}/bin/java -Djava.library.path="../lib" -Xms12288m -Xmx100g -cp `find ../lib -name "*.jar" | tr '\n' ':'`  org.dia.benchmarking.spark.FourierSpark $*
