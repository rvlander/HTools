/* $Id: Jwintab.c,v 1.2 2000/02/01 05:40:21 rekimoto Exp $ */

#include <jni.h>
#include <stdio.h>

#include "hollermap_input_Jwintab.h"
#include "mywintab.h"

/*
 * Class:     Jwintab
 * Method:    close
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_hollermap_input_Jwintab_close
  (JNIEnv *env, jclass jc) {
    return (jint)mwClose();
}

/*
 * Class:     Jwintab
 * Method:    getPacket
 * Signature: ([I)I
 */
#define NUM_VAL 128
JNIEXPORT jint JNICALL Java_hollermap_input_Jwintab_getPacket
  (JNIEnv * env, jclass jc , jfloatArray jX, jfloatArray jY, jintArray jB, jlongArray jT) {
	float valsX[NUM_VAL];
	float valsY[NUM_VAL];
	int valsB[NUM_VAL];
	long valsT[NUM_VAL];

	int res = mwGetPacket(valsX,valsY,valsB,valsT); /* poll a packet */

	//printf("%d\n",res);

	if (res > 0) {
		//printf("res gt 0\n");
		jsize len = (*env)->GetArrayLength(env, jX);
		
		jfloat *bodyX = (*env)->GetFloatArrayElements(env, jX, 0);
		jfloat *bodyY = (*env)->GetFloatArrayElements(env, jY, 0);
		jint *bodyB = (*env)->GetIntArrayElements(env, jB, 0);
		jlong *bodyT = (*env)->GetLongArrayElements(env, jT, 0);

		//printf("pointers recup\n");

		
		if (len >= NUM_VAL && bodyX != NULL) {
			int i;
			for (i = 0; i < NUM_VAL; i++){
				bodyX[i] = valsX[i];
				bodyY[i] = valsY[i];
				bodyB[i] = valsB[i];
				bodyT[i] = valsT[i];
			}
		//	printf("assign OK\n");
		} else {
		//	printf("error\n");
			res = -1; /* bad parameter */
		}		
        (*env)->ReleaseFloatArrayElements(env, jX, bodyX, 0);
		(*env)->ReleaseFloatArrayElements(env, jY, bodyY, 0);
		(*env)->ReleaseIntArrayElements(env, jB, bodyB, 0);
		(*env)->ReleaseLongArrayElements(env, jT, bodyT, 0);
    } 
	return (jint)res;
}

/*
 * Class:     Jwintab
 * Method:    getVersion
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_hollermap_input_Jwintab_getVersion
  (JNIEnv *env, jclass jc) {
	/* return (jint)20000108; */
	return (jint)20000131;
}

/*
 * Class:     Jwintab
 * Method:    open
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_hollermap_input_Jwintab_open
(JNIEnv *env, jclass jc, jstring title){
	HWND hwnd = NULL;
 const char *str = NULL;

 str = (*env)->GetStringUTFChars(env, title, 0);
 printf("%s\n",str);
 hwnd = FindWindow(NULL,str);
 printf("%d\n",hwnd);
 (*env)->ReleaseStringUTFChars(env, title, str);


	return (jint)mwOpen(hwnd);
}


