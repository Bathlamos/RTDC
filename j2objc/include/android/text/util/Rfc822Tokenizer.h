//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/frameworks/base/core/java/android/text/util/Rfc822Tokenizer.java
//

#ifndef _AndroidTextUtilRfc822Tokenizer_H_
#define _AndroidTextUtilRfc822Tokenizer_H_

@class IOSObjectArray;
@class JavaLangStringBuilder;
@protocol JavaLangCharSequence;
@protocol JavaUtilCollection;

#import "JreEmulation.h"

@interface AndroidTextUtilRfc822Tokenizer : NSObject {
}

+ (void)tokenizeWithJavaLangCharSequence:(id<JavaLangCharSequence>)text
                  withJavaUtilCollection:(id<JavaUtilCollection>)outArg;

+ (IOSObjectArray *)tokenizeWithJavaLangCharSequence:(id<JavaLangCharSequence>)text;

- (jint)findTokenStartWithJavaLangCharSequence:(id<JavaLangCharSequence>)text
                                       withInt:(jint)cursor;

- (jint)findTokenEndWithJavaLangCharSequence:(id<JavaLangCharSequence>)text
                                     withInt:(jint)cursor;

- (id<JavaLangCharSequence>)terminateTokenWithJavaLangCharSequence:(id<JavaLangCharSequence>)text;

- (instancetype)init;

@end

__attribute__((always_inline)) inline void AndroidTextUtilRfc822Tokenizer_init() {}
FOUNDATION_EXPORT void AndroidTextUtilRfc822Tokenizer_tokenizeWithJavaLangCharSequence_withJavaUtilCollection_(id<JavaLangCharSequence> text, id<JavaUtilCollection> outArg);
FOUNDATION_EXPORT IOSObjectArray *AndroidTextUtilRfc822Tokenizer_tokenizeWithJavaLangCharSequence_(id<JavaLangCharSequence> text);

#endif // _AndroidTextUtilRfc822Tokenizer_H_