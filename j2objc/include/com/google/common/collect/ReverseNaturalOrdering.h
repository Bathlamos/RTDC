//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/kstanger/j2objc-0.9.5/j2objc/guava/sources/com/google/common/collect/ReverseNaturalOrdering.java
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectReverseNaturalOrdering_RESTRICT
#define ComGoogleCommonCollectReverseNaturalOrdering_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectReverseNaturalOrdering_RESTRICT
#if !defined (_ComGoogleCommonCollectReverseNaturalOrdering_) && (ComGoogleCommonCollectReverseNaturalOrdering_INCLUDE_ALL || ComGoogleCommonCollectReverseNaturalOrdering_INCLUDE)
#define _ComGoogleCommonCollectReverseNaturalOrdering_

@class IOSObjectArray;
@protocol JavaLangComparable;
@protocol JavaLangIterable;
@protocol JavaUtilIterator;

#define ComGoogleCommonCollectOrdering_RESTRICT 1
#define ComGoogleCommonCollectOrdering_INCLUDE 1
#include "com/google/common/collect/Ordering.h"

#define JavaIoSerializable_RESTRICT 1
#define JavaIoSerializable_INCLUDE 1
#include "java/io/Serializable.h"


#define ComGoogleCommonCollectReverseNaturalOrdering_serialVersionUID 0LL

@interface ComGoogleCommonCollectReverseNaturalOrdering : ComGoogleCommonCollectOrdering < JavaIoSerializable > {
}

- (jint)compareWithId:(id<JavaLangComparable>)left
               withId:(id<JavaLangComparable>)right;

- (ComGoogleCommonCollectOrdering *)reverse;

- (id)minWithId:(id<JavaLangComparable>)a
         withId:(id<JavaLangComparable>)b;

- (id)minWithId:(id<JavaLangComparable>)a
         withId:(id<JavaLangComparable>)b
         withId:(id<JavaLangComparable>)c
withNSObjectArray:(IOSObjectArray *)rest;

- (id)minWithJavaUtilIterator:(id<JavaUtilIterator>)iterator;

- (id)minWithJavaLangIterable:(id<JavaLangIterable>)iterable;

- (id)maxWithId:(id<JavaLangComparable>)a
         withId:(id<JavaLangComparable>)b;

- (id)maxWithId:(id<JavaLangComparable>)a
         withId:(id<JavaLangComparable>)b
         withId:(id<JavaLangComparable>)c
withNSObjectArray:(IOSObjectArray *)rest;

- (id)maxWithJavaUtilIterator:(id<JavaUtilIterator>)iterator;

- (id)maxWithJavaLangIterable:(id<JavaLangIterable>)iterable;

- (NSString *)description;

@end

FOUNDATION_EXPORT BOOL ComGoogleCommonCollectReverseNaturalOrdering_initialized;
J2OBJC_STATIC_INIT(ComGoogleCommonCollectReverseNaturalOrdering)

FOUNDATION_EXPORT ComGoogleCommonCollectReverseNaturalOrdering *ComGoogleCommonCollectReverseNaturalOrdering_INSTANCE_;
J2OBJC_STATIC_FIELD_GETTER(ComGoogleCommonCollectReverseNaturalOrdering, INSTANCE_, ComGoogleCommonCollectReverseNaturalOrdering *)

J2OBJC_STATIC_FIELD_GETTER(ComGoogleCommonCollectReverseNaturalOrdering, serialVersionUID, jlong)
#endif