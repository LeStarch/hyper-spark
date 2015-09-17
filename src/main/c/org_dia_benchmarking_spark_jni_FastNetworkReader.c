#include <jni.h>

#include <stdlib.h>
#include <stdio.h>
#include <sys/socket.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <sys/types.h>
#include <string.h>

#include "org_dia_benchmarking_spark_jni_FastNetworkReader.h"


#define BLOCK_SIZE 65536ull
//#define TYPE AF_UNIX
#define TYPE AF_INET
typedef enum {
    CLIENT,SERVER
} Type;

int conn = -1;
/**
 * Opens up an internet socket on geiven port
 * @param port - port number to open
 * @return socket descriptor
 */
int internet(short port,long address,Type type)
{
    //Get socket descriptor
    int sd = socket(TYPE,SOCK_STREAM,0);
    if (sd == -1) {
        return -1;
    }
    //Setup an address (port)
    struct sockaddr_in addr;
    memset(&addr,0,sizeof(addr));
    addr.sin_family = AF_INET;
    addr.sin_addr.s_addr = address;
    addr.sin_port = htons(port);
    int ret = -1;
    //Bind to that address (port)
    if (type == SERVER) {
        ret = bind(sd,(struct sockaddr*)&addr,sizeof(addr));
    } else if (type == CLIENT) {
        ret = connect(sd,(struct sockaddr*)&addr,sizeof(addr));
    }
    if (ret == -1) {
        return -1;
    }
    return sd;
}

/*
 * Class:     org_dia_benchmarking_spark_jni_FastNetworkReader
 * Method:    open
 * Signature: (Ljava/lang/String;I)V
 */
JNIEXPORT jint JNICALL Java_org_dia_benchmarking_spark_jni_FastNetworkReader_open(JNIEnv * env, jobject thiso, jstring host, jint port)
{
    const char *chost = (*env)->GetStringUTFChars(env, host, NULL);
    if (NULL == chost) return -1;
    conn = internet(port,inet_addr(chost),CLIENT);
    (*env)->ReleaseStringUTFChars(env, host, chost);
    return 0;
}

/*
 * Class:     org_dia_benchmarking_spark_jni_FastNetworkReader
 * Method:    read
 * Signature: ()[B
 */
JNIEXPORT jbyteArray JNICALL Java_org_dia_benchmarking_spark_jni_FastNetworkReader_read(JNIEnv * env, jobject thiso)
{
    jbyte buffer[BLOCK_SIZE];
    int ret = read(conn,buffer,BLOCK_SIZE);
    if (ret == -1) {
        jbyteArray jbar = (*env)->NewByteArray(env, 0);
        return jbar;
    }
    jbyteArray jbar = (*env)->NewByteArray(env, ret);
    (*env)->SetByteArrayRegion(env,jbar,0,ret,buffer);
    return jbar;
}

/*
 * Class:     org_dia_benchmarking_spark_jni_FastNetworkReader
 * Method:    close
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_dia_benchmarking_spark_jni_FastNetworkReader_close(JNIEnv * env, jobject thiso)
{
    close(conn);
    return;
}
