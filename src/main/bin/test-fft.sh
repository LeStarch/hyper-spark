#!/bin/sh
cd `dirname $0`
CP=`find ../lib -name "*.jar" | tr '\n' ':'`
${JAVA_HOME}/bin/java -Xms12288m -Xmx12288m -cp $CP  org.dia.benchmarking.spark.Fourier $*
