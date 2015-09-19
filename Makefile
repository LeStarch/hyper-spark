SRC_FOLDER=src/main/c
fastread.so: $(SRC_FOLDER)/org_dia_benchmarking_spark_jni_FastNetwork.c $(SRC_FOLDER)/org_dia_benchmarking_spark_jni_FastNetwork.h
	gcc -I"$(JAVA_HOME)/include" -I"$(JAVA_HOME)/include/linux" $(SRC_FOLDER)/org_dia_benchmarking_spark_jni_FastNetwork.c -o libfastread.so -shared -fPIC
clean:
	rm libfastread.so
