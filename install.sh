#!/bin/sh
cd `dirname $0`
rm -r ../hyper-spark-0.1; mvn clean install && tar -xzf target/hyper-spark-0.1-dist.tar.gz -C ..
