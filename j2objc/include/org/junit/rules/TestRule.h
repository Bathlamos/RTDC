//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/kstanger/j2objc-0.9.5/j2objc/junit/build_result/java/org/junit/rules/TestRule.java
//

#ifndef _OrgJunitRulesTestRule_H_
#define _OrgJunitRulesTestRule_H_

@class OrgJunitRunnerDescription;
@class OrgJunitRunnersModelStatement;

#import "JreEmulation.h"

@protocol OrgJunitRulesTestRule < NSObject, JavaObject >

- (OrgJunitRunnersModelStatement *)applyWithOrgJunitRunnersModelStatement:(OrgJunitRunnersModelStatement *)base
                                            withOrgJunitRunnerDescription:(OrgJunitRunnerDescription *)description_;

@end

__attribute__((always_inline)) inline void OrgJunitRulesTestRule_init() {}

#endif // _OrgJunitRulesTestRule_H_