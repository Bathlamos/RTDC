//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/kstanger/j2objc-0.9.5/j2objc/junit/build_result/java/junit/framework/TestSuite.java
//

#ifndef _JunitFrameworkTestSuite_H_
#define _JunitFrameworkTestSuite_H_

@class IOSClass;
@class IOSObjectArray;
@class JavaLangReflectConstructor;
@class JavaLangReflectMethod;
@class JavaLangThrowable;
@class JavaUtilVector;
@class JunitFrameworkTestResult;
@protocol JavaUtilEnumeration;
@protocol JavaUtilList;

#import "JreEmulation.h"
#include "junit/framework/Test.h"
#include "junit/framework/TestCase.h"

@interface JunitFrameworkTestSuite : NSObject < JunitFrameworkTest > {
 @public
  NSString *fName_;
  JavaUtilVector *fTests_;
}

+ (id<JunitFrameworkTest>)createTestWithIOSClass:(IOSClass *)theClass
                                    withNSString:(NSString *)name;

+ (JavaLangReflectConstructor *)getTestConstructorWithIOSClass:(IOSClass *)theClass;

+ (id<JunitFrameworkTest>)warningWithNSString:(NSString *)message;

+ (NSString *)exceptionToStringWithJavaLangThrowable:(JavaLangThrowable *)t;

- (instancetype)init;

- (instancetype)initWithIOSClass:(IOSClass *)theClass;

- (void)addTestsFromTestCaseWithIOSClass:(IOSClass *)theClass;

- (instancetype)initWithIOSClass:(IOSClass *)theClass
                    withNSString:(NSString *)name;

- (instancetype)initWithNSString:(NSString *)name;

- (instancetype)initWithIOSClassArray:(IOSObjectArray *)classes;

- (id<JunitFrameworkTest>)testCaseForClassWithIOSClass:(IOSClass *)each;

- (instancetype)initWithIOSClassArray:(IOSObjectArray *)classes
                         withNSString:(NSString *)name;

- (void)addTestWithJunitFrameworkTest:(id<JunitFrameworkTest>)test;

- (void)addTestSuiteWithIOSClass:(IOSClass *)testClass;

- (jint)countTestCases;

- (NSString *)getName;

- (void)runWithJunitFrameworkTestResult:(JunitFrameworkTestResult *)result;

- (void)runTestWithJunitFrameworkTest:(id<JunitFrameworkTest>)test
         withJunitFrameworkTestResult:(JunitFrameworkTestResult *)result;

- (void)setNameWithNSString:(NSString *)name;

- (id<JunitFrameworkTest>)testAtWithInt:(jint)index;

- (jint)testCount;

- (id<JavaUtilEnumeration>)tests;

- (NSString *)description;

- (void)addTestMethodWithJavaLangReflectMethod:(JavaLangReflectMethod *)m
                              withJavaUtilList:(id<JavaUtilList>)names
                                  withIOSClass:(IOSClass *)theClass;

- (jboolean)isPublicTestMethodWithJavaLangReflectMethod:(JavaLangReflectMethod *)m;

- (jboolean)isTestMethodWithJavaLangReflectMethod:(JavaLangReflectMethod *)m;

- (void)dealloc;

- (void)copyAllFieldsTo:(JunitFrameworkTestSuite *)other;

@end

__attribute__((always_inline)) inline void JunitFrameworkTestSuite_init() {}

J2OBJC_FIELD_SETTER(JunitFrameworkTestSuite, fName_, NSString *)
J2OBJC_FIELD_SETTER(JunitFrameworkTestSuite, fTests_, JavaUtilVector *)
FOUNDATION_EXPORT id<JunitFrameworkTest> JunitFrameworkTestSuite_createTestWithIOSClass_withNSString_(IOSClass *theClass, NSString *name);
FOUNDATION_EXPORT JavaLangReflectConstructor *JunitFrameworkTestSuite_getTestConstructorWithIOSClass_(IOSClass *theClass);
FOUNDATION_EXPORT id<JunitFrameworkTest> JunitFrameworkTestSuite_warningWithNSString_(NSString *message);

@interface JunitFrameworkTestSuite_$1 : JunitFrameworkTestCase {
 @public
  NSString *val$message_;
}

- (void)runTest;

- (instancetype)initWithNSString:(NSString *)arg$0
                    withNSString:(NSString *)capture$0;

- (void)dealloc;

- (void)copyAllFieldsTo:(JunitFrameworkTestSuite_$1 *)other;

@end

__attribute__((always_inline)) inline void JunitFrameworkTestSuite_$1_init() {}

J2OBJC_FIELD_SETTER(JunitFrameworkTestSuite_$1, val$message_, NSString *)

#endif // _JunitFrameworkTestSuite_H_