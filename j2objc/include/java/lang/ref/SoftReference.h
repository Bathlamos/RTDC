//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/lang/ref/SoftReference.java
//

#ifndef _JavaLangRefSoftReference_H_
#define _JavaLangRefSoftReference_H_

@class JavaLangRefReferenceQueue;

#import "JreEmulation.h"
#include "java/lang/ref/Reference.h"

@interface JavaLangRefSoftReference : JavaLangRefReference {
 @public
  jboolean queued_;
}

- (instancetype)initWithId:(id)r;

- (instancetype)initWithId:(id)r
withJavaLangRefReferenceQueue:(JavaLangRefReferenceQueue *)q;

@end

__attribute__((always_inline)) inline void JavaLangRefSoftReference_init() {}

#endif // _JavaLangRefSoftReference_H_