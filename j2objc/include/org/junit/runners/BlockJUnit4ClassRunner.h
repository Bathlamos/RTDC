//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/kstanger/j2objc-0.9.5/j2objc/junit/build_result/java/org/junit/runners/BlockJUnit4ClassRunner.java
//

#ifndef _OrgJunitRunnersBlockJUnit4ClassRunner_H_
#define _OrgJunitRunnersBlockJUnit4ClassRunner_H_

@class IOSClass;
@class OrgJunitRunnerDescription;
@class OrgJunitRunnerNotificationRunNotifier;
@class OrgJunitRunnersModelFrameworkMethod;
@class OrgJunitRunnersModelStatement;
@protocol JavaUtilList;
@protocol OrgJunitTest;

#import "JreEmulation.h"
#include "org/junit/internal/runners/model/ReflectiveCallable.h"
#include "org/junit/runners/ParentRunner.h"

@interface OrgJunitRunnersBlockJUnit4ClassRunner : OrgJunitRunnersParentRunner {
}

- (instancetype)initWithIOSClass:(IOSClass *)klass;

- (void)runChildWithId:(OrgJunitRunnersModelFrameworkMethod *)method
withOrgJunitRunnerNotificationRunNotifier:(OrgJunitRunnerNotificationRunNotifier *)notifier;

- (OrgJunitRunnerDescription *)describeChildWithId:(OrgJunitRunnersModelFrameworkMethod *)method;

- (id<JavaUtilList>)getChildren;

- (id<JavaUtilList>)computeTestMethods;

- (void)collectInitializationErrorsWithJavaUtilList:(id<JavaUtilList>)errors;

- (void)validateNoNonStaticInnerClassWithJavaUtilList:(id<JavaUtilList>)errors;

- (void)validateConstructorWithJavaUtilList:(id<JavaUtilList>)errors;

- (void)validateOnlyOneConstructorWithJavaUtilList:(id<JavaUtilList>)errors;

- (void)validateZeroArgConstructorWithJavaUtilList:(id<JavaUtilList>)errors;

- (jboolean)hasOneConstructor;

- (void)validateInstanceMethodsWithJavaUtilList:(id<JavaUtilList>)errors;

- (void)validateFieldsWithJavaUtilList:(id<JavaUtilList>)errors;

- (void)validateTestMethodsWithJavaUtilList:(id<JavaUtilList>)errors;

- (id)createTest;

- (NSString *)testNameWithOrgJunitRunnersModelFrameworkMethod:(OrgJunitRunnersModelFrameworkMethod *)method;

- (OrgJunitRunnersModelStatement *)methodBlockWithOrgJunitRunnersModelFrameworkMethod:(OrgJunitRunnersModelFrameworkMethod *)method;

- (OrgJunitRunnersModelStatement *)methodInvokerWithOrgJunitRunnersModelFrameworkMethod:(OrgJunitRunnersModelFrameworkMethod *)method
                                                                                 withId:(id)test;

- (OrgJunitRunnersModelStatement *)possiblyExpectingExceptionsWithOrgJunitRunnersModelFrameworkMethod:(OrgJunitRunnersModelFrameworkMethod *)method
                                                                                               withId:(id)test
                                                                    withOrgJunitRunnersModelStatement:(OrgJunitRunnersModelStatement *)next;

- (OrgJunitRunnersModelStatement *)withPotentialTimeoutWithOrgJunitRunnersModelFrameworkMethod:(OrgJunitRunnersModelFrameworkMethod *)method
                                                                                        withId:(id)test
                                                             withOrgJunitRunnersModelStatement:(OrgJunitRunnersModelStatement *)next;

- (OrgJunitRunnersModelStatement *)withBeforesWithOrgJunitRunnersModelFrameworkMethod:(OrgJunitRunnersModelFrameworkMethod *)method
                                                                               withId:(id)target
                                                    withOrgJunitRunnersModelStatement:(OrgJunitRunnersModelStatement *)statement;

- (OrgJunitRunnersModelStatement *)withAftersWithOrgJunitRunnersModelFrameworkMethod:(OrgJunitRunnersModelFrameworkMethod *)method
                                                                              withId:(id)target
                                                   withOrgJunitRunnersModelStatement:(OrgJunitRunnersModelStatement *)statement;

- (OrgJunitRunnersModelStatement *)withRulesWithOrgJunitRunnersModelFrameworkMethod:(OrgJunitRunnersModelFrameworkMethod *)method
                                                                             withId:(id)target
                                                  withOrgJunitRunnersModelStatement:(OrgJunitRunnersModelStatement *)statement;

- (OrgJunitRunnersModelStatement *)withMethodRulesWithOrgJunitRunnersModelFrameworkMethod:(OrgJunitRunnersModelFrameworkMethod *)method
                                                                                   withId:(id)target
                                                        withOrgJunitRunnersModelStatement:(OrgJunitRunnersModelStatement *)result;

- (id<JavaUtilList>)getMethodRulesWithId:(id)target;

- (id<JavaUtilList>)rulesWithId:(id)target;

- (OrgJunitRunnersModelStatement *)withTestRulesWithOrgJunitRunnersModelFrameworkMethod:(OrgJunitRunnersModelFrameworkMethod *)method
                                                                                 withId:(id)target
                                                      withOrgJunitRunnersModelStatement:(OrgJunitRunnersModelStatement *)statement;

- (id<JavaUtilList>)getTestRulesWithId:(id)target;

- (IOSClass *)getExpectedExceptionWithOrgJunitTest:(id<OrgJunitTest>)annotation;

- (jboolean)expectsExceptionWithOrgJunitTest:(id<OrgJunitTest>)annotation;

- (jlong)getTimeoutWithOrgJunitTest:(id<OrgJunitTest>)annotation;

@end

__attribute__((always_inline)) inline void OrgJunitRunnersBlockJUnit4ClassRunner_init() {}

@interface OrgJunitRunnersBlockJUnit4ClassRunner_$1 : OrgJunitInternalRunnersModelReflectiveCallable {
 @public
  OrgJunitRunnersBlockJUnit4ClassRunner *this$0_;
}

- (id)runReflectiveCall;

- (instancetype)initWithOrgJunitRunnersBlockJUnit4ClassRunner:(OrgJunitRunnersBlockJUnit4ClassRunner *)outer$;

- (void)dealloc;

- (void)copyAllFieldsTo:(OrgJunitRunnersBlockJUnit4ClassRunner_$1 *)other;

@end

__attribute__((always_inline)) inline void OrgJunitRunnersBlockJUnit4ClassRunner_$1_init() {}

J2OBJC_FIELD_SETTER(OrgJunitRunnersBlockJUnit4ClassRunner_$1, this$0_, OrgJunitRunnersBlockJUnit4ClassRunner *)

#endif // _OrgJunitRunnersBlockJUnit4ClassRunner_H_