//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/nio/IntArrayBuffer.java
//

#ifndef _JavaNioIntArrayBuffer_H_
#define _JavaNioIntArrayBuffer_H_

@class IOSIntArray;
@class JavaNioByteOrder;

#import "JreEmulation.h"
#include "java/nio/IntBuffer.h"

@interface JavaNioIntArrayBuffer : JavaNioIntBuffer {
}

- (instancetype)initWithIntArray:(IOSIntArray *)array;

- (JavaNioIntBuffer *)asReadOnlyBuffer;

- (JavaNioIntBuffer *)compact;

- (JavaNioIntBuffer *)duplicate;

- (JavaNioIntBuffer *)slice;

- (jboolean)isReadOnly;

- (IOSIntArray *)protectedArray;

- (jint)protectedArrayOffset;

- (jboolean)protectedHasArray;

- (jint)get;

- (jint)getWithInt:(jint)index;

- (JavaNioIntBuffer *)getWithIntArray:(IOSIntArray *)dst
                              withInt:(jint)dstOffset
                              withInt:(jint)intCount;

- (jboolean)isDirect;

- (JavaNioByteOrder *)order;

- (JavaNioIntBuffer *)putWithInt:(jint)c;

- (JavaNioIntBuffer *)putWithInt:(jint)index
                         withInt:(jint)c;

- (JavaNioIntBuffer *)putWithIntArray:(IOSIntArray *)src
                              withInt:(jint)srcOffset
                              withInt:(jint)intCount;

@end

__attribute__((always_inline)) inline void JavaNioIntArrayBuffer_init() {}

#endif // _JavaNioIntArrayBuffer_H_