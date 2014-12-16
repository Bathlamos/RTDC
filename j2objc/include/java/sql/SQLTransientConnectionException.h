//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/sql/SQLTransientConnectionException.java
//

#ifndef _JavaSqlSQLTransientConnectionException_H_
#define _JavaSqlSQLTransientConnectionException_H_

@class JavaLangThrowable;

#import "JreEmulation.h"
#include "java/sql/SQLTransientException.h"

#define JavaSqlSQLTransientConnectionException_serialVersionUID -2520155553543391200LL

@interface JavaSqlSQLTransientConnectionException : JavaSqlSQLTransientException {
}

- (instancetype)init;

- (instancetype)initWithNSString:(NSString *)reason;

- (instancetype)initWithNSString:(NSString *)reason
                    withNSString:(NSString *)sqlState;

- (instancetype)initWithNSString:(NSString *)reason
                    withNSString:(NSString *)sqlState
                         withInt:(jint)vendorCode;

- (instancetype)initWithJavaLangThrowable:(JavaLangThrowable *)cause;

- (instancetype)initWithNSString:(NSString *)reason
           withJavaLangThrowable:(JavaLangThrowable *)cause;

- (instancetype)initWithNSString:(NSString *)reason
                    withNSString:(NSString *)sqlState
           withJavaLangThrowable:(JavaLangThrowable *)cause;

- (instancetype)initWithNSString:(NSString *)reason
                    withNSString:(NSString *)sqlState
                         withInt:(jint)vendorCode
           withJavaLangThrowable:(JavaLangThrowable *)cause;


@end

__attribute__((always_inline)) inline void JavaSqlSQLTransientConnectionException_init() {}

J2OBJC_STATIC_FIELD_GETTER(JavaSqlSQLTransientConnectionException, serialVersionUID, jlong)

#endif // _JavaSqlSQLTransientConnectionException_H_