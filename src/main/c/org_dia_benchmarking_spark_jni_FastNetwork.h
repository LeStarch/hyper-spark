/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class org_dia_benchmarking_spark_jni_FastNetwork */

#ifndef _Included_org_dia_benchmarking_spark_jni_FastNetwork
#define _Included_org_dia_benchmarking_spark_jni_FastNetwork
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     org_dia_benchmarking_spark_jni_FastNetwork
 * Method:    open
 * Signature: (Ljava/lang/String;II)I
 */
JNIEXPORT jint JNICALL Java_org_dia_benchmarking_spark_jni_FastNetwork_open
  (JNIEnv *, jobject, jstring, jint, jint);

/*
 * Class:     org_dia_benchmarking_spark_jni_FastNetwork
 * Method:    read
 * Signature: ()[B
 */
JNIEXPORT jbyteArray JNICALL Java_org_dia_benchmarking_spark_jni_FastNetwork_read
  (JNIEnv *, jobject);

/*
 * Class:     org_dia_benchmarking_spark_jni_FastNetwork
 * Method:    write
 * Signature: ([B)I
 */
JNIEXPORT jint JNICALL Java_org_dia_benchmarking_spark_jni_FastNetwork_write
  (JNIEnv *, jobject, jbyteArray);

/*
 * Class:     org_dia_benchmarking_spark_jni_FastNetwork
 * Method:    close
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_dia_benchmarking_spark_jni_FastNetwork_close
  (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif