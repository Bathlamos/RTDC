//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/nicolasmenard/IdeaProjects/RTDC/core/src/main/java/rtdc/core/service/Service.java
//

#include "Action.h"
#include "AsyncCallback.h"
#include "Bootstrapper.h"
#include "ErrorEvent.h"
#include "Event.h"
#include "Factory.h"
#include "HttpHeadersName.h"
#include "HttpRequest.h"
#include "HttpResponse.h"
#include "IOSClass.h"
#include "J2ObjC_source.h"
#include "JSONException.h"
#include "JSONObject.h"
#include "Message.h"
#include "Service.h"
#include "Unit.h"
#include "User.h"
#include "VoipController.h"
#include "java/util/logging/Logger.h"

@interface ServiceService ()

- (instancetype)init;

+ (void)executeRequestWithImplHttpRequest:(id<ImplHttpRequest>)request;

@end

static NSString *ServiceService_URL_ = @"http://10.137.215.94:8888/api/";
J2OBJC_STATIC_FIELD_GETTER(ServiceService, URL_, NSString *)

static JavaUtilLoggingLogger *ServiceService_logger_;
J2OBJC_STATIC_FIELD_GETTER(ServiceService, logger_, JavaUtilLoggingLogger *)

__attribute__((unused)) static void ServiceService_init(ServiceService *self);

__attribute__((unused)) static ServiceService *new_ServiceService_init() NS_RETURNS_RETAINED;

__attribute__((unused)) static void ServiceService_executeRequestWithImplHttpRequest_(id<ImplHttpRequest> request);

@interface ServiceService_$1 : NSObject < ServiceAsyncCallback >

- (void)onSuccessWithId:(id<ImplHttpResponse>)resp;

- (void)onErrorWithNSString:(NSString *)message;

- (instancetype)init;

@end

J2OBJC_EMPTY_STATIC_INIT(ServiceService_$1)

__attribute__((unused)) static void ServiceService_$1_init(ServiceService_$1 *self);

__attribute__((unused)) static ServiceService_$1 *new_ServiceService_$1_init() NS_RETURNS_RETAINED;

J2OBJC_TYPE_LITERAL_HEADER(ServiceService_$1)

J2OBJC_INITIALIZED_DEFN(ServiceService)

@implementation ServiceService

J2OBJC_IGNORE_DESIGNATED_BEGIN
- (instancetype)init {
  ServiceService_init(self);
  return self;
}
J2OBJC_IGNORE_DESIGNATED_END

+ (void)authenticateUserWithNSString:(NSString *)username
                        withNSString:(NSString *)password {
  ServiceService_authenticateUserWithNSString_withNSString_(username, password);
}

+ (void)isAuthTokenValid {
  ServiceService_isAuthTokenValid();
}

+ (void)logout {
  ServiceService_logout();
}

+ (void)getUnits {
  ServiceService_getUnits();
}

+ (void)updateOrSaveUnitWithModelUnit:(ModelUnit *)unit {
  ServiceService_updateOrSaveUnitWithModelUnit_(unit);
}

+ (void)deleteUnitWithInt:(jint)unitId {
  ServiceService_deleteUnitWithInt_(unitId);
}

+ (void)getUsers {
  ServiceService_getUsers();
}

+ (void)getUserWithInt:(jint)id_ {
  ServiceService_getUserWithInt_(id_);
}

+ (void)updateOrSaveUserWithModelUser:(ModelUser *)user
                         withNSString:(NSString *)password {
  ServiceService_updateOrSaveUserWithModelUser_withNSString_(user, password);
}

+ (void)deleteUserWithInt:(jint)userId {
  ServiceService_deleteUserWithInt_(userId);
}

+ (void)saveOrUpdateMessageWithModelMessage:(ModelMessage *)message {
  ServiceService_saveOrUpdateMessageWithModelMessage_(message);
}

+ (void)getMessagesWithInt:(jint)userId1
                   withInt:(jint)userId2 {
  ServiceService_getMessagesWithInt_withInt_(userId1, userId2);
}

+ (void)getActions {
  ServiceService_getActions();
}

+ (void)updateOrSaveActionsWithModelAction:(ModelAction *)action {
  ServiceService_updateOrSaveActionsWithModelAction_(action);
}

+ (void)deleteActionWithInt:(jint)actionId {
  ServiceService_deleteActionWithInt_(actionId);
}

+ (void)executeRequestWithImplHttpRequest:(id<ImplHttpRequest>)request {
  ServiceService_executeRequestWithImplHttpRequest_(request);
}

+ (void)initialize {
  if (self == [ServiceService class]) {
    ServiceService_logger_ = JavaUtilLoggingLogger_getLoggerWithNSString_([ServiceService_class_() getCanonicalName]);
    J2OBJC_SET_INITIALIZED(ServiceService)
  }
}

+ (const J2ObjcClassInfo *)__metadata {
  static const J2ObjcMethodInfo methods[] = {
    { "init", "Service", NULL, 0x2, NULL, NULL },
    { "authenticateUserWithNSString:withNSString:", "authenticateUser", "V", 0x9, NULL, NULL },
    { "isAuthTokenValid", NULL, "V", 0x9, NULL, NULL },
    { "logout", NULL, "V", 0x9, NULL, NULL },
    { "getUnits", NULL, "V", 0x9, NULL, NULL },
    { "updateOrSaveUnitWithModelUnit:", "updateOrSaveUnit", "V", 0x9, NULL, NULL },
    { "deleteUnitWithInt:", "deleteUnit", "V", 0x9, NULL, NULL },
    { "getUsers", NULL, "V", 0x9, NULL, NULL },
    { "getUserWithInt:", "getUser", "V", 0x9, NULL, NULL },
    { "updateOrSaveUserWithModelUser:withNSString:", "updateOrSaveUser", "V", 0x9, NULL, NULL },
    { "deleteUserWithInt:", "deleteUser", "V", 0x9, NULL, NULL },
    { "saveOrUpdateMessageWithModelMessage:", "saveOrUpdateMessage", "V", 0x9, NULL, NULL },
    { "getMessagesWithInt:withInt:", "getMessages", "V", 0x9, NULL, NULL },
    { "getActions", NULL, "V", 0x9, NULL, NULL },
    { "updateOrSaveActionsWithModelAction:", "updateOrSaveActions", "V", 0x9, NULL, NULL },
    { "deleteActionWithInt:", "deleteAction", "V", 0x9, NULL, NULL },
    { "executeRequestWithImplHttpRequest:", "executeRequest", "V", 0xa, NULL, NULL },
  };
  static const J2ObjcFieldInfo fields[] = {
    { "URL_", NULL, 0x1a, "Ljava.lang.String;", &ServiceService_URL_, NULL, .constantValue.asLong = 0 },
    { "logger_", NULL, 0x1a, "Ljava.util.logging.Logger;", &ServiceService_logger_, NULL, .constantValue.asLong = 0 },
  };
  static const J2ObjcClassInfo _ServiceService = { 2, "Service", "rtdc.core.service", NULL, 0x11, 17, methods, 2, fields, 0, NULL, 0, NULL, NULL, NULL };
  return &_ServiceService;
}

@end

void ServiceService_init(ServiceService *self) {
  (void) NSObject_init(self);
}

ServiceService *new_ServiceService_init() {
  ServiceService *self = [ServiceService alloc];
  ServiceService_init(self);
  return self;
}

void ServiceService_authenticateUserWithNSString_withNSString_(NSString *username, NSString *password) {
  ServiceService_initialize();
  id<ImplHttpRequest> req = [((id<ImplFactory>) nil_chk(JreLoadStatic(RtdcCoreBootstrapper, FACTORY_))) newHttpRequestWithNSString:JreStrcat("$$", ServiceService_URL_, @"authenticate") withImplHttpRequest_RequestMethodEnum:JreLoadStatic(ImplHttpRequest_RequestMethodEnum, POST)];
  [((id<ImplHttpRequest>) nil_chk(req)) addParameterWithNSString:@"username" withNSString:username];
  [req addParameterWithNSString:@"password" withNSString:password];
  ServiceService_executeRequestWithImplHttpRequest_(req);
}

void ServiceService_isAuthTokenValid() {
  ServiceService_initialize();
  ServiceService_executeRequestWithImplHttpRequest_([((id<ImplFactory>) nil_chk(JreLoadStatic(RtdcCoreBootstrapper, FACTORY_))) newHttpRequestWithNSString:JreStrcat("$$", ServiceService_URL_, @"authenticate/tokenValid") withImplHttpRequest_RequestMethodEnum:JreLoadStatic(ImplHttpRequest_RequestMethodEnum, POST)]);
}

void ServiceService_logout() {
  ServiceService_initialize();
  ServiceService_executeRequestWithImplHttpRequest_([((id<ImplFactory>) nil_chk(JreLoadStatic(RtdcCoreBootstrapper, FACTORY_))) newHttpRequestWithNSString:JreStrcat("$$", ServiceService_URL_, @"authenticate/logout") withImplHttpRequest_RequestMethodEnum:JreLoadStatic(ImplHttpRequest_RequestMethodEnum, POST)]);
  [((id<ImplVoipController>) nil_chk([JreLoadStatic(RtdcCoreBootstrapper, FACTORY_) getVoipController])) unregisterCurrentUser];
}

void ServiceService_getUnits() {
  ServiceService_initialize();
  ServiceService_executeRequestWithImplHttpRequest_([((id<ImplFactory>) nil_chk(JreLoadStatic(RtdcCoreBootstrapper, FACTORY_))) newHttpRequestWithNSString:JreStrcat("$$", ServiceService_URL_, @"units") withImplHttpRequest_RequestMethodEnum:JreLoadStatic(ImplHttpRequest_RequestMethodEnum, GET)]);
}

void ServiceService_updateOrSaveUnitWithModelUnit_(ModelUnit *unit) {
  ServiceService_initialize();
  id<ImplHttpRequest> req = [((id<ImplFactory>) nil_chk(JreLoadStatic(RtdcCoreBootstrapper, FACTORY_))) newHttpRequestWithNSString:JreStrcat("$$", ServiceService_URL_, @"units") withImplHttpRequest_RequestMethodEnum:JreLoadStatic(ImplHttpRequest_RequestMethodEnum, PUT)];
  [((id<ImplHttpRequest>) nil_chk(req)) addParameterWithNSString:@"unit" withNSString:[((ModelUnit *) nil_chk(unit)) description]];
  ServiceService_executeRequestWithImplHttpRequest_(req);
}

void ServiceService_deleteUnitWithInt_(jint unitId) {
  ServiceService_initialize();
  ServiceService_executeRequestWithImplHttpRequest_([((id<ImplFactory>) nil_chk(JreLoadStatic(RtdcCoreBootstrapper, FACTORY_))) newHttpRequestWithNSString:JreStrcat("$$I", ServiceService_URL_, @"units/", unitId) withImplHttpRequest_RequestMethodEnum:JreLoadStatic(ImplHttpRequest_RequestMethodEnum, DELETE)]);
}

void ServiceService_getUsers() {
  ServiceService_initialize();
  ServiceService_executeRequestWithImplHttpRequest_([((id<ImplFactory>) nil_chk(JreLoadStatic(RtdcCoreBootstrapper, FACTORY_))) newHttpRequestWithNSString:JreStrcat("$$", ServiceService_URL_, @"users") withImplHttpRequest_RequestMethodEnum:JreLoadStatic(ImplHttpRequest_RequestMethodEnum, GET)]);
}

void ServiceService_getUserWithInt_(jint id_) {
  ServiceService_initialize();
  ServiceService_executeRequestWithImplHttpRequest_([((id<ImplFactory>) nil_chk(JreLoadStatic(RtdcCoreBootstrapper, FACTORY_))) newHttpRequestWithNSString:JreStrcat("$$I", ServiceService_URL_, @"users/", id_) withImplHttpRequest_RequestMethodEnum:JreLoadStatic(ImplHttpRequest_RequestMethodEnum, POST)]);
}

void ServiceService_updateOrSaveUserWithModelUser_withNSString_(ModelUser *user, NSString *password) {
  ServiceService_initialize();
  id<ImplHttpRequest> req = [((id<ImplFactory>) nil_chk(JreLoadStatic(RtdcCoreBootstrapper, FACTORY_))) newHttpRequestWithNSString:JreStrcat("$$", ServiceService_URL_, @"users") withImplHttpRequest_RequestMethodEnum:JreLoadStatic(ImplHttpRequest_RequestMethodEnum, PUT)];
  [((id<ImplHttpRequest>) nil_chk(req)) addParameterWithNSString:@"user" withNSString:[((ModelUser *) nil_chk(user)) description]];
  [req addParameterWithNSString:@"password" withNSString:password];
  ServiceService_executeRequestWithImplHttpRequest_(req);
}

void ServiceService_deleteUserWithInt_(jint userId) {
  ServiceService_initialize();
  ServiceService_executeRequestWithImplHttpRequest_([((id<ImplFactory>) nil_chk(JreLoadStatic(RtdcCoreBootstrapper, FACTORY_))) newHttpRequestWithNSString:JreStrcat("$$I", ServiceService_URL_, @"users/", userId) withImplHttpRequest_RequestMethodEnum:JreLoadStatic(ImplHttpRequest_RequestMethodEnum, DELETE)]);
}

void ServiceService_saveOrUpdateMessageWithModelMessage_(ModelMessage *message) {
  ServiceService_initialize();
  id<ImplHttpRequest> req = [((id<ImplFactory>) nil_chk(JreLoadStatic(RtdcCoreBootstrapper, FACTORY_))) newHttpRequestWithNSString:JreStrcat("$$", ServiceService_URL_, @"messages") withImplHttpRequest_RequestMethodEnum:JreLoadStatic(ImplHttpRequest_RequestMethodEnum, PUT)];
  [((id<ImplHttpRequest>) nil_chk(req)) addParameterWithNSString:@"message" withNSString:[((ModelMessage *) nil_chk(message)) description]];
  ServiceService_executeRequestWithImplHttpRequest_(req);
}

void ServiceService_getMessagesWithInt_withInt_(jint userId1, jint userId2) {
  ServiceService_initialize();
  ServiceService_executeRequestWithImplHttpRequest_([((id<ImplFactory>) nil_chk(JreLoadStatic(RtdcCoreBootstrapper, FACTORY_))) newHttpRequestWithNSString:JreStrcat("$$ICI", ServiceService_URL_, @"messages/", userId1, '/', userId2) withImplHttpRequest_RequestMethodEnum:JreLoadStatic(ImplHttpRequest_RequestMethodEnum, POST)]);
}

void ServiceService_getActions() {
  ServiceService_initialize();
  ServiceService_executeRequestWithImplHttpRequest_([((id<ImplFactory>) nil_chk(JreLoadStatic(RtdcCoreBootstrapper, FACTORY_))) newHttpRequestWithNSString:JreStrcat("$$", ServiceService_URL_, @"actions") withImplHttpRequest_RequestMethodEnum:JreLoadStatic(ImplHttpRequest_RequestMethodEnum, GET)]);
}

void ServiceService_updateOrSaveActionsWithModelAction_(ModelAction *action) {
  ServiceService_initialize();
  id<ImplHttpRequest> req = [((id<ImplFactory>) nil_chk(JreLoadStatic(RtdcCoreBootstrapper, FACTORY_))) newHttpRequestWithNSString:JreStrcat("$$", ServiceService_URL_, @"actions") withImplHttpRequest_RequestMethodEnum:JreLoadStatic(ImplHttpRequest_RequestMethodEnum, PUT)];
  [((id<ImplHttpRequest>) nil_chk(req)) addParameterWithNSString:@"action" withNSString:[((ModelAction *) nil_chk(action)) description]];
  ServiceService_executeRequestWithImplHttpRequest_(req);
}

void ServiceService_deleteActionWithInt_(jint actionId) {
  ServiceService_initialize();
  ServiceService_executeRequestWithImplHttpRequest_([((id<ImplFactory>) nil_chk(JreLoadStatic(RtdcCoreBootstrapper, FACTORY_))) newHttpRequestWithNSString:JreStrcat("$$I", ServiceService_URL_, @"actions/", actionId) withImplHttpRequest_RequestMethodEnum:JreLoadStatic(ImplHttpRequest_RequestMethodEnum, DELETE)]);
}

void ServiceService_executeRequestWithImplHttpRequest_(id<ImplHttpRequest> request) {
  ServiceService_initialize();
  [((id<ImplHttpRequest>) nil_chk(request)) setContentTypeWithNSString:@"application/x-www-form-urlencoded"];
  if (JreLoadStatic(RtdcCoreBootstrapper, AUTHENTICATION_TOKEN_) != nil) [request setHeaderWithNSString:ServiceHttpHeadersName_AUTH_TOKEN_ withNSString:JreLoadStatic(RtdcCoreBootstrapper, AUTHENTICATION_TOKEN_)];
  [request executeWithServiceAsyncCallback:new_ServiceService_$1_init()];
}

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(ServiceService)

@implementation ServiceService_$1

- (void)onSuccessWithId:(id<ImplHttpResponse>)resp {
  if ([((id<ImplHttpResponse>) nil_chk(resp)) getStatusCode] != 200) [new_EventErrorEvent_initWithNSString_(JreStrcat("$IC$", @"Error code ", [resp getStatusCode], ' ', [resp getContent])) fire];
  else {
    @try {
      JsonJSONObject *object = new_JsonJSONObject_initWithNSString_([resp getContent]);
      EventEvent_fireWithJsonJSONObject_(object);
    }
    @catch (JsonJSONException *e) {
      [new_EventErrorEvent_initWithNSString_(JreStrcat("$$C$", @"Unrecognized output from server ", [resp getContent], ' ', [((JsonJSONException *) nil_chk(e)) getMessage])) fire];
    }
  }
}

- (void)onErrorWithNSString:(NSString *)message {
  [new_EventErrorEvent_initWithNSString_(JreStrcat("$$", @"Network error ", message)) fire];
}

J2OBJC_IGNORE_DESIGNATED_BEGIN
- (instancetype)init {
  ServiceService_$1_init(self);
  return self;
}
J2OBJC_IGNORE_DESIGNATED_END

+ (const J2ObjcClassInfo *)__metadata {
  static const J2ObjcMethodInfo methods[] = {
    { "onSuccessWithId:", "onSuccess", "V", 0x1, NULL, NULL },
    { "onErrorWithNSString:", "onError", "V", 0x1, NULL, NULL },
    { "init", "", NULL, 0x0, NULL, NULL },
  };
  static const J2ObjCEnclosingMethodInfo enclosing_method = { "ServiceService", "executeRequestWithImplHttpRequest:" };
  static const J2ObjcClassInfo _ServiceService_$1 = { 2, "", "rtdc.core.service", "Service", 0x8008, 3, methods, 0, NULL, 0, NULL, 0, NULL, &enclosing_method, "Ljava/lang/Object;Lrtdc/core/service/AsyncCallback<Lrtdc/core/impl/HttpResponse;>;" };
  return &_ServiceService_$1;
}

@end

void ServiceService_$1_init(ServiceService_$1 *self) {
  (void) NSObject_init(self);
}

ServiceService_$1 *new_ServiceService_$1_init() {
  ServiceService_$1 *self = [ServiceService_$1 alloc];
  ServiceService_$1_init(self);
  return self;
}

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(ServiceService_$1)
