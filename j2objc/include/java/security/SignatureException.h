//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/security/SignatureException.java
//

#ifndef _JavaSecuritySignatureException_H_
#define _JavaSecuritySignatureException_H_

@class JavaLangThrowable;

#import "JreEmulation.h"
#include "java/security/GeneralSecurityException.h"

#define JavaSecuritySignatureException_serialVersionUID 7509989324975124438LL

@interface JavaSecuritySignatureException : JavaSecurityGeneralSecurityException {
}

- (instancetype)initWithNSString:(NSString *)msg;

- (instancetype)init;

- (instancetype)initWithNSString:(NSString *)message
           withJavaLangThrowable:(JavaLangThrowable *)cause;

- (instancetype)initWithJavaLangThrowable:(JavaLangThrowable *)cause;

@end

__attribute__((always_inline)) inline void JavaSecuritySignatureException_init() {}

J2OBJC_STATIC_FIELD_GETTER(JavaSecuritySignatureException, serialVersionUID, jlong)

#endif // _JavaSecuritySignatureException_H_