//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/kstanger/j2objc-0.9.5/j2objc/guava/sources/com/google/common/collect/EmptyImmutableSet.java
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectEmptyImmutableSet_RESTRICT
#define ComGoogleCommonCollectEmptyImmutableSet_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectEmptyImmutableSet_RESTRICT
#if !defined (_ComGoogleCommonCollectEmptyImmutableSet_) && (ComGoogleCommonCollectEmptyImmutableSet_INCLUDE_ALL || ComGoogleCommonCollectEmptyImmutableSet_INCLUDE)
#define _ComGoogleCommonCollectEmptyImmutableSet_

@class ComGoogleCommonCollectImmutableList;
@class ComGoogleCommonCollectUnmodifiableIterator;
@class IOSObjectArray;
@protocol JavaUtilCollection;

#define ComGoogleCommonCollectImmutableSet_RESTRICT 1
#define ComGoogleCommonCollectImmutableSet_INCLUDE 1
#include "com/google/common/collect/ImmutableSet.h"


#define ComGoogleCommonCollectEmptyImmutableSet_serialVersionUID 0LL

@interface ComGoogleCommonCollectEmptyImmutableSet : ComGoogleCommonCollectImmutableSet {
}

- (jint)size;

- (jboolean)isEmpty;

- (jboolean)containsWithId:(id)target;

- (jboolean)containsAllWithJavaUtilCollection:(id<JavaUtilCollection>)targets;

- (ComGoogleCommonCollectUnmodifiableIterator *)iterator;

- (jboolean)isPartialView;

- (IOSObjectArray *)toArray;

- (IOSObjectArray *)toArrayWithNSObjectArray:(IOSObjectArray *)a;

- (ComGoogleCommonCollectImmutableList *)asList;

- (jboolean)isEqual:(id)object;

- (NSUInteger)hash;

- (jboolean)isHashCodeFast;

- (NSString *)description;

- (id)readResolve;


@end

FOUNDATION_EXPORT BOOL ComGoogleCommonCollectEmptyImmutableSet_initialized;
J2OBJC_STATIC_INIT(ComGoogleCommonCollectEmptyImmutableSet)

FOUNDATION_EXPORT ComGoogleCommonCollectEmptyImmutableSet *ComGoogleCommonCollectEmptyImmutableSet_INSTANCE_;
J2OBJC_STATIC_FIELD_GETTER(ComGoogleCommonCollectEmptyImmutableSet, INSTANCE_, ComGoogleCommonCollectEmptyImmutableSet *)

J2OBJC_STATIC_FIELD_GETTER(ComGoogleCommonCollectEmptyImmutableSet, serialVersionUID, jlong)
#endif