//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/kstanger/j2objc-0.9.5/j2objc/testing/mockito/build_result/java/org/mockito/internal/matchers/ArrayEquals.java
//

#ifndef _OrgMockitoInternalMatchersArrayEquals_H_
#define _OrgMockitoInternalMatchersArrayEquals_H_

@class IOSObjectArray;
@protocol OrgHamcrestDescription;

#import "JreEmulation.h"
#include "org/mockito/internal/matchers/Equals.h"

#define OrgMockitoInternalMatchersArrayEquals_serialVersionUID -7167812844261087583LL

@interface OrgMockitoInternalMatchersArrayEquals : OrgMockitoInternalMatchersEquals {
}

- (instancetype)initWithId:(id)wanted;

- (jboolean)matchesWithId:(id)actual;

- (void)describeToWithOrgHamcrestDescription:(id<OrgHamcrestDescription>)description_;

- (void)appendArrayWithNSObjectArray:(IOSObjectArray *)array
          withOrgHamcrestDescription:(id<OrgHamcrestDescription>)description_;

+ (IOSObjectArray *)createObjectArrayWithId:(id)array;

@end

__attribute__((always_inline)) inline void OrgMockitoInternalMatchersArrayEquals_init() {}
FOUNDATION_EXPORT IOSObjectArray *OrgMockitoInternalMatchersArrayEquals_createObjectArrayWithId_(id array);

J2OBJC_STATIC_FIELD_GETTER(OrgMockitoInternalMatchersArrayEquals, serialVersionUID, jlong)

#endif // _OrgMockitoInternalMatchersArrayEquals_H_