//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/kstanger/j2objc-0.9.5/j2objc/testing/mockito/build_result/java/org/mockito/internal/matchers/CompareTo.java
//

#ifndef _OrgMockitoInternalMatchersCompareTo_H_
#define _OrgMockitoInternalMatchersCompareTo_H_

@protocol JavaLangComparable;
@protocol OrgHamcrestDescription;

#import "JreEmulation.h"
#include "org/mockito/ArgumentMatcher.h"

@interface OrgMockitoInternalMatchersCompareTo : OrgMockitoArgumentMatcher {
 @public
  id<JavaLangComparable> wanted_;
}

- (instancetype)initWithJavaLangComparable:(id<JavaLangComparable>)value;

- (jboolean)matchesWithId:(id)actual;

- (void)describeToWithOrgHamcrestDescription:(id<OrgHamcrestDescription>)description_;

- (NSString *)getName;

- (jboolean)matchResultWithInt:(jint)result;

- (void)dealloc;

- (void)copyAllFieldsTo:(OrgMockitoInternalMatchersCompareTo *)other;

@end

__attribute__((always_inline)) inline void OrgMockitoInternalMatchersCompareTo_init() {}

J2OBJC_FIELD_SETTER(OrgMockitoInternalMatchersCompareTo, wanted_, id<JavaLangComparable>)

#endif // _OrgMockitoInternalMatchersCompareTo_H_