//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/util/concurrent/Executor.java
//

#ifndef _JavaUtilConcurrentExecutor_H_
#define _JavaUtilConcurrentExecutor_H_

@protocol JavaLangRunnable;

#import "JreEmulation.h"

@protocol JavaUtilConcurrentExecutor < NSObject, JavaObject >

- (void)executeWithJavaLangRunnable:(id<JavaLangRunnable>)command;

@end

__attribute__((always_inline)) inline void JavaUtilConcurrentExecutor_init() {}

#endif // _JavaUtilConcurrentExecutor_H_