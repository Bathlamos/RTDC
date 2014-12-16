//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/nio/LongBuffer.java
//

#ifndef _JavaNioLongBuffer_H_
#define _JavaNioLongBuffer_H_

@class IOSLongArray;
@class JavaNioByteOrder;

#import "JreEmulation.h"
#include "java/lang/Comparable.h"
#include "java/nio/Buffer.h"

@interface JavaNioLongBuffer : JavaNioBuffer < JavaLangComparable > {
}

+ (JavaNioLongBuffer *)allocateWithInt:(jint)capacity OBJC_METHOD_FAMILY_NONE;

+ (JavaNioLongBuffer *)wrapWithLongArray:(IOSLongArray *)array;

+ (JavaNioLongBuffer *)wrapWithLongArray:(IOSLongArray *)array
                                 withInt:(jint)start
                                 withInt:(jint)longCount;

- (instancetype)initWithInt:(jint)capacity
                   withLong:(jlong)effectiveDirectAddress;

- (IOSLongArray *)array;

- (jint)arrayOffset;

- (JavaNioLongBuffer *)asReadOnlyBuffer;

- (JavaNioLongBuffer *)compact;

- (jint)compareToWithId:(JavaNioLongBuffer *)otherBuffer;

- (JavaNioLongBuffer *)duplicate;

- (jboolean)isEqual:(id)other;

- (jlong)get;

- (JavaNioLongBuffer *)getWithLongArray:(IOSLongArray *)dst;

- (JavaNioLongBuffer *)getWithLongArray:(IOSLongArray *)dst
                                withInt:(jint)dstOffset
                                withInt:(jint)longCount;

- (jlong)getWithInt:(jint)index;

- (jboolean)hasArray;

- (NSUInteger)hash;

- (jboolean)isDirect;

- (JavaNioByteOrder *)order;

- (IOSLongArray *)protectedArray;

- (jint)protectedArrayOffset;

- (jboolean)protectedHasArray;

- (JavaNioLongBuffer *)putWithLong:(jlong)l;

- (JavaNioLongBuffer *)putWithLongArray:(IOSLongArray *)src;

- (JavaNioLongBuffer *)putWithLongArray:(IOSLongArray *)src
                                withInt:(jint)srcOffset
                                withInt:(jint)longCount;

- (JavaNioLongBuffer *)putWithJavaNioLongBuffer:(JavaNioLongBuffer *)src;

- (JavaNioLongBuffer *)putWithInt:(jint)index
                         withLong:(jlong)l;

- (JavaNioLongBuffer *)slice;

@end

__attribute__((always_inline)) inline void JavaNioLongBuffer_init() {}
FOUNDATION_EXPORT JavaNioLongBuffer *JavaNioLongBuffer_allocateWithInt_(jint capacity);
FOUNDATION_EXPORT JavaNioLongBuffer *JavaNioLongBuffer_wrapWithLongArray_(IOSLongArray *array);
FOUNDATION_EXPORT JavaNioLongBuffer *JavaNioLongBuffer_wrapWithLongArray_withInt_withInt_(IOSLongArray *array, jint start, jint longCount);

#endif // _JavaNioLongBuffer_H_