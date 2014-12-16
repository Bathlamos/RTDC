//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/util/concurrent/FutureTask.java
//

#ifndef _JavaUtilConcurrentFutureTask_H_
#define _JavaUtilConcurrentFutureTask_H_

@class JavaLangThread;
@class JavaLangThrowable;
@class JavaUtilConcurrentFutureTask_WaitNode;
@class JavaUtilConcurrentTimeUnitEnum;
@class SunMiscUnsafe;
@protocol JavaLangRunnable;
@protocol JavaUtilConcurrentCallable;

#import "JreEmulation.h"
#include "java/util/concurrent/RunnableFuture.h"

#define JavaUtilConcurrentFutureTask_CANCELLED 4
#define JavaUtilConcurrentFutureTask_COMPLETING 1
#define JavaUtilConcurrentFutureTask_EXCEPTIONAL 3
#define JavaUtilConcurrentFutureTask_INTERRUPTED 6
#define JavaUtilConcurrentFutureTask_INTERRUPTING 5
#define JavaUtilConcurrentFutureTask_NEW 0
#define JavaUtilConcurrentFutureTask_NORMAL 2

@interface JavaUtilConcurrentFutureTask : NSObject < JavaUtilConcurrentRunnableFuture > {
}

- (instancetype)initWithJavaUtilConcurrentCallable:(id<JavaUtilConcurrentCallable>)callable;

- (instancetype)initWithJavaLangRunnable:(id<JavaLangRunnable>)runnable
                                  withId:(id)result;

- (jboolean)isCancelled;

- (jboolean)isDone;

- (jboolean)cancelWithBoolean:(jboolean)mayInterruptIfRunning;

- (id)get;

- (id)getWithLong:(jlong)timeout
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit;

- (void)done;

- (void)setWithId:(id)v;

- (void)setExceptionWithJavaLangThrowable:(JavaLangThrowable *)t;

- (void)run;

- (jboolean)runAndReset;

@end

FOUNDATION_EXPORT BOOL JavaUtilConcurrentFutureTask_initialized;
J2OBJC_STATIC_INIT(JavaUtilConcurrentFutureTask)

J2OBJC_STATIC_FIELD_GETTER(JavaUtilConcurrentFutureTask, NEW, jint)

J2OBJC_STATIC_FIELD_GETTER(JavaUtilConcurrentFutureTask, COMPLETING, jint)

J2OBJC_STATIC_FIELD_GETTER(JavaUtilConcurrentFutureTask, NORMAL, jint)

J2OBJC_STATIC_FIELD_GETTER(JavaUtilConcurrentFutureTask, EXCEPTIONAL, jint)

J2OBJC_STATIC_FIELD_GETTER(JavaUtilConcurrentFutureTask, CANCELLED, jint)

J2OBJC_STATIC_FIELD_GETTER(JavaUtilConcurrentFutureTask, INTERRUPTING, jint)

J2OBJC_STATIC_FIELD_GETTER(JavaUtilConcurrentFutureTask, INTERRUPTED, jint)

FOUNDATION_EXPORT SunMiscUnsafe *JavaUtilConcurrentFutureTask_UNSAFE_;
J2OBJC_STATIC_FIELD_GETTER(JavaUtilConcurrentFutureTask, UNSAFE_, SunMiscUnsafe *)

FOUNDATION_EXPORT jlong JavaUtilConcurrentFutureTask_stateOffset_;
J2OBJC_STATIC_FIELD_GETTER(JavaUtilConcurrentFutureTask, stateOffset_, jlong)

FOUNDATION_EXPORT jlong JavaUtilConcurrentFutureTask_runnerOffset_;
J2OBJC_STATIC_FIELD_GETTER(JavaUtilConcurrentFutureTask, runnerOffset_, jlong)

FOUNDATION_EXPORT jlong JavaUtilConcurrentFutureTask_waitersOffset_;
J2OBJC_STATIC_FIELD_GETTER(JavaUtilConcurrentFutureTask, waitersOffset_, jlong)

@interface JavaUtilConcurrentFutureTask_WaitNode : NSObject {
 @public
  JavaLangThread *thread_;
  JavaUtilConcurrentFutureTask_WaitNode *next_;
}

- (instancetype)init;

@end

__attribute__((always_inline)) inline void JavaUtilConcurrentFutureTask_WaitNode_init() {}

J2OBJC_FIELD_SETTER(JavaUtilConcurrentFutureTask_WaitNode, thread_, JavaLangThread *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentFutureTask_WaitNode, next_, JavaUtilConcurrentFutureTask_WaitNode *)

#endif // _JavaUtilConcurrentFutureTask_H_