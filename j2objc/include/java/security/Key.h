//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/security/Key.java
//

#ifndef _JavaSecurityKey_H_
#define _JavaSecurityKey_H_

@class IOSByteArray;

#import "JreEmulation.h"
#include "java/io/Serializable.h"

#define JavaSecurityKey_serialVersionUID 6603384152749567654LL

@protocol JavaSecurityKey < JavaIoSerializable, NSObject, JavaObject >
- (NSString *)getAlgorithm;

- (NSString *)getFormat;

- (IOSByteArray *)getEncoded;

@end

__attribute__((always_inline)) inline void JavaSecurityKey_init() {}

J2OBJC_STATIC_FIELD_GETTER(JavaSecurityKey, serialVersionUID, jlong)

#endif // _JavaSecurityKey_H_