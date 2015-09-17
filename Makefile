SRC_FOLDER=src/main/c
fastread.so: $(SRC_FOLDER)/org_dia_benchmarking_spark_jni_FastNetworkReader.c $(SRC_FOLDER)/org_dia_benchmarking_spark_jni_FastNetworkReader.h
	gcc -I"$(JAVA_HOME)/include" -I"$(JAVA_HOME)/include/linux" $(SRC_FOLDER)/org_dia_benchmarking_spark_jni_FastNetworkReader.c -o libfastread.so -shared
clean:
	rm libfastread.so
