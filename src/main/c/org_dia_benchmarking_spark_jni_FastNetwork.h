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
 * Method:    openX
 * Signature: (Ljava/lang/String;II)I
 */
JNIEXPORT jint JNICALL Java_org_dia_benchmarking_spark_jni_FastNetwork_openX
  (JNIEnv *, jobject, jstring, jint, jint);

/*
 * Class:     org_dia_benchmarking_spark_jni_FastNetwork
 * Method:    readX
 * Signature: (I)[B
 */
JNIEXPORT jbyteArray JNICALL Java_org_dia_benchmarking_spark_jni_FastNetwork_readX
  (JNIEnv *, jobject, jint);

/*
 * Class:     org_dia_benchmarking_spark_jni_FastNetwork
 * Method:    writeX
 * Signature: (I[B)I
 */
JNIEXPORT jint JNICALL Java_org_dia_benchmarking_spark_jni_FastNetwork_writeX
  (JNIEnv *, jobject, jint, jbyteArray);

/*
 * Class:     org_dia_benchmarking_spark_jni_FastNetwork
 * Method:    closeX
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_org_dia_benchmarking_spark_jni_FastNetwork_closeX
  (JNIEnv *, jobject, jint);

#ifdef __cplusplus
}
#endif
#endif
