//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/kstanger/j2objc-0.9.5/j2objc/guava/sources/com/google/common/io/CharSource.java
//

#import "JreEmulation.h"

#if !ComGoogleCommonIoCharSource_RESTRICT
#define ComGoogleCommonIoCharSource_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonIoCharSource_RESTRICT
#if !defined (_ComGoogleCommonIoCharSource_) && (ComGoogleCommonIoCharSource_INCLUDE_ALL || ComGoogleCommonIoCharSource_INCLUDE)
#define _ComGoogleCommonIoCharSource_

@class ComGoogleCommonCollectImmutableList;
@class ComGoogleCommonIoCharSink;
@class JavaIoBufferedReader;
@class JavaIoReader;
@protocol JavaLangAppendable;


@interface ComGoogleCommonIoCharSource : NSObject {
}

- (JavaIoReader *)openStream;

- (JavaIoBufferedReader *)openBufferedStream;

- (jlong)copyToWithJavaLangAppendable:(id<JavaLangAppendable>)appendable OBJC_METHOD_FAMILY_NONE;

- (jlong)copyToWithComGoogleCommonIoCharSink:(ComGoogleCommonIoCharSink *)sink OBJC_METHOD_FAMILY_NONE;

- (NSString *)read;

- (NSString *)readFirstLine;

- (ComGoogleCommonCollectImmutableList *)readLines;

- (instancetype)init;

@end

__attribute__((always_inline)) inline void ComGoogleCommonIoCharSource_init() {}
#endif