//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/util/regex/MatchResult.java
//

#ifndef _JavaUtilRegexMatchResult_H_
#define _JavaUtilRegexMatchResult_H_

#import "JreEmulation.h"

@protocol JavaUtilRegexMatchResult < NSObject, JavaObject >

- (jint)end;

- (jint)endWithInt:(jint)group;

- (NSString *)group;

- (NSString *)groupWithInt:(jint)group;

- (jint)groupCount;

- (jint)start;

- (jint)startWithInt:(jint)group;

@end

__attribute__((always_inline)) inline void JavaUtilRegexMatchResult_init() {}

#endif // _JavaUtilRegexMatchResult_H_