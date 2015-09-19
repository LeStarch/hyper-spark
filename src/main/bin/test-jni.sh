#!/bin/sh
cd `dirname $0`
CP=`find ../lib -name "*.jar" | tr '\n' ':'`
if [[ "$#" != "2" ]]
then
     echo -e "$0 <hostname> <port>" 1>&2
     exit 1
fi
${JAVA_HOME}/bin/java -Xms12288m -Xmx12288m -Djava.library.path="../lib" -cp $CP  org.dia.benchmarking.spark.jni.FastNetwork $*
