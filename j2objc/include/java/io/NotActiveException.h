//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/io/NotActiveException.java
//

#ifndef _JavaIoNotActiveException_H_
#define _JavaIoNotActiveException_H_

#import "JreEmulation.h"
#include "java/io/ObjectStreamException.h"

#define JavaIoNotActiveException_serialVersionUID -3893467273049808895LL

@interface JavaIoNotActiveException : JavaIoObjectStreamException {
}

- (instancetype)init;

- (instancetype)initWithNSString:(NSString *)detailMessage;

@end

__attribute__((always_inline)) inline void JavaIoNotActiveException_init() {}

J2OBJC_STATIC_FIELD_GETTER(JavaIoNotActiveException, serialVersionUID, jlong)

#endif // _JavaIoNotActiveException_H_