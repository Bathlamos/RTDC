//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/io/DataOutput.java
//

#ifndef _JavaIoDataOutput_H_
#define _JavaIoDataOutput_H_

@class IOSByteArray;

#import "JreEmulation.h"

@protocol JavaIoDataOutput < NSObject, JavaObject >

- (void)writeWithByteArray:(IOSByteArray *)buffer;

- (void)writeWithByteArray:(IOSByteArray *)buffer
                   withInt:(jint)offset
                   withInt:(jint)count;

- (void)writeWithInt:(jint)oneByte;

- (void)writeBooleanWithBoolean:(jboolean)val;

- (void)writeByteWithInt:(jint)val;

- (void)writeBytesWithNSString:(NSString *)str;

- (void)writeCharWithInt:(jint)val;

- (void)writeCharsWithNSString:(NSString *)str;

- (void)writeDoubleWithDouble:(jdouble)val;

- (void)writeFloatWithFloat:(jfloat)val;

- (void)writeIntWithInt:(jint)val;

- (void)writeLongWithLong:(jlong)val;

- (void)writeShortWithInt:(jint)val;

- (void)writeUTFWithNSString:(NSString *)str;

@end

__attribute__((always_inline)) inline void JavaIoDataOutput_init() {}

#endif // _JavaIoDataOutput_H_