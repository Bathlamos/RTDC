//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/nicolasmenard/IdeaProjects/RTDC/core/src/main/java/rtdc/core/service/Service.java
//

#ifndef _Service_H_
#define _Service_H_

#include "J2ObjC_header.h"

@class ModelAction;
@class ModelUnit;
@class ModelUser;

@interface ServiceService : NSObject

#pragma mark Public

+ (void)authenticateUserWithNSString:(NSString *)username
                        withNSString:(NSString *)password;

+ (void)deleteActionWithInt:(jint)actionId;

+ (void)deleteUnitWithInt:(jint)unitId;

+ (void)deleteUserWithInt:(jint)userId;

+ (void)getActions;

+ (void)getUnits;

+ (void)getUsers;

+ (void)isAuthTokenValid;

+ (void)logout;

+ (void)updateOrSaveActionsWithModelAction:(ModelAction *)action;

+ (void)updateOrSaveUnitWithModelUnit:(ModelUnit *)unit;

+ (void)updateOrSaveUserWithModelUser:(ModelUser *)user
                         withNSString:(NSString *)password;

@end

J2OBJC_STATIC_INIT(ServiceService)

FOUNDATION_EXPORT void ServiceService_authenticateUserWithNSString_withNSString_(NSString *username, NSString *password);

FOUNDATION_EXPORT void ServiceService_isAuthTokenValid();

FOUNDATION_EXPORT void ServiceService_logout();

FOUNDATION_EXPORT void ServiceService_getUnits();

FOUNDATION_EXPORT void ServiceService_updateOrSaveUnitWithModelUnit_(ModelUnit *unit);

FOUNDATION_EXPORT void ServiceService_deleteUnitWithInt_(jint unitId);

FOUNDATION_EXPORT void ServiceService_getUsers();

FOUNDATION_EXPORT void ServiceService_updateOrSaveUserWithModelUser_withNSString_(ModelUser *user, NSString *password);

FOUNDATION_EXPORT void ServiceService_deleteUserWithInt_(jint userId);

FOUNDATION_EXPORT void ServiceService_getActions();

FOUNDATION_EXPORT void ServiceService_updateOrSaveActionsWithModelAction_(ModelAction *action);

FOUNDATION_EXPORT void ServiceService_deleteActionWithInt_(jint actionId);

J2OBJC_TYPE_LITERAL_HEADER(ServiceService)

@compatibility_alias RtdcCoreServiceService ServiceService;

#endif // _Service_H_
