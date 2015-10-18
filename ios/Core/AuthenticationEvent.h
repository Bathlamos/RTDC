//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/nicolasmenard/IdeaProjects/RTDC/core/src/main/java/rtdc/core/event/AuthenticationEvent.java
//

#ifndef _AuthenticationEvent_H_
#define _AuthenticationEvent_H_

#include "Event.h"
#include "EventHandler.h"
#include "J2ObjC_header.h"
#include "ObjectProperty.h"
#include "java/lang/Enum.h"

@class IOSObjectArray;
@class JSONJSONObject;
@class ModelUser;
@class RtdcCoreEventEventType;
@protocol ModelObjectProperty;

@interface RtdcCoreEventAuthenticationEvent : RtdcCoreEventEvent

#pragma mark Public

- (instancetype)initWithJSONJSONObject:(JSONJSONObject *)object;

- (instancetype)initWithModelUser:(ModelUser *)user
                     withNSString:(NSString *)authenticationToken;

- (NSString *)getAuthenticationToken;

- (IOSObjectArray *)getProperties;

- (NSString *)getType;

- (ModelUser *)getUser;

- (id)getValueWithModelObjectProperty:(id<ModelObjectProperty>)property;

#pragma mark Package-Private

- (void)fire;

@end

J2OBJC_STATIC_INIT(RtdcCoreEventAuthenticationEvent)

FOUNDATION_EXPORT RtdcCoreEventEventType *RtdcCoreEventAuthenticationEvent_TYPE_;
J2OBJC_STATIC_FIELD_GETTER(RtdcCoreEventAuthenticationEvent, TYPE_, RtdcCoreEventEventType *)

FOUNDATION_EXPORT void RtdcCoreEventAuthenticationEvent_initWithModelUser_withNSString_(RtdcCoreEventAuthenticationEvent *self, ModelUser *user, NSString *authenticationToken);

FOUNDATION_EXPORT RtdcCoreEventAuthenticationEvent *new_RtdcCoreEventAuthenticationEvent_initWithModelUser_withNSString_(ModelUser *user, NSString *authenticationToken) NS_RETURNS_RETAINED;

FOUNDATION_EXPORT void RtdcCoreEventAuthenticationEvent_initWithJSONJSONObject_(RtdcCoreEventAuthenticationEvent *self, JSONJSONObject *object);

FOUNDATION_EXPORT RtdcCoreEventAuthenticationEvent *new_RtdcCoreEventAuthenticationEvent_initWithJSONJSONObject_(JSONJSONObject *object) NS_RETURNS_RETAINED;

J2OBJC_TYPE_LITERAL_HEADER(RtdcCoreEventAuthenticationEvent)

typedef NS_ENUM(NSUInteger, RtdcCoreEventAuthenticationEvent_Properties) {
  RtdcCoreEventAuthenticationEvent_Properties_user = 0,
  RtdcCoreEventAuthenticationEvent_Properties_authenticationToken = 1,
};

@interface RtdcCoreEventAuthenticationEvent_PropertiesEnum : JavaLangEnum < NSCopying, ModelObjectProperty >

#pragma mark Package-Private

+ (IOSObjectArray *)values;
FOUNDATION_EXPORT IOSObjectArray *RtdcCoreEventAuthenticationEvent_PropertiesEnum_values();

+ (RtdcCoreEventAuthenticationEvent_PropertiesEnum *)valueOfWithNSString:(NSString *)name;
FOUNDATION_EXPORT RtdcCoreEventAuthenticationEvent_PropertiesEnum *RtdcCoreEventAuthenticationEvent_PropertiesEnum_valueOfWithNSString_(NSString *name);

- (id)copyWithZone:(NSZone *)zone;

@end

J2OBJC_STATIC_INIT(RtdcCoreEventAuthenticationEvent_PropertiesEnum)

FOUNDATION_EXPORT RtdcCoreEventAuthenticationEvent_PropertiesEnum *RtdcCoreEventAuthenticationEvent_PropertiesEnum_values_[];

#define RtdcCoreEventAuthenticationEvent_PropertiesEnum_user RtdcCoreEventAuthenticationEvent_PropertiesEnum_values_[RtdcCoreEventAuthenticationEvent_Properties_user]
J2OBJC_ENUM_CONSTANT_GETTER(RtdcCoreEventAuthenticationEvent_PropertiesEnum, user)

#define RtdcCoreEventAuthenticationEvent_PropertiesEnum_authenticationToken RtdcCoreEventAuthenticationEvent_PropertiesEnum_values_[RtdcCoreEventAuthenticationEvent_Properties_authenticationToken]
J2OBJC_ENUM_CONSTANT_GETTER(RtdcCoreEventAuthenticationEvent_PropertiesEnum, authenticationToken)

J2OBJC_TYPE_LITERAL_HEADER(RtdcCoreEventAuthenticationEvent_PropertiesEnum)

@protocol RtdcCoreEventAuthenticationEvent_Handler < RtdcCoreEventEventHandler, NSObject, JavaObject >

- (void)onAuthenticateWithRtdcCoreEventAuthenticationEvent:(RtdcCoreEventAuthenticationEvent *)event;

@end

J2OBJC_EMPTY_STATIC_INIT(RtdcCoreEventAuthenticationEvent_Handler)

J2OBJC_TYPE_LITERAL_HEADER(RtdcCoreEventAuthenticationEvent_Handler)

#endif // _AuthenticationEvent_H_
