//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/nicolasmenard/IdeaProjects/RTDC/core/src/main/java/rtdc/core/model/ApplicationPermission.java
//

#include "ApplicationPermission.h"
#include "J2ObjC_source.h"

NSString *ModelApplicationPermission_USER_ = @"USER";
NSString *ModelApplicationPermission_ADMIN_ = @"ADMIN";

@implementation ModelApplicationPermission

J2OBJC_IGNORE_DESIGNATED_BEGIN
- (instancetype)init {
  ModelApplicationPermission_init(self);
  return self;
}
J2OBJC_IGNORE_DESIGNATED_END

+ (const J2ObjcClassInfo *)__metadata {
  static const J2ObjcMethodInfo methods[] = {
    { "init", NULL, NULL, 0x1, NULL, NULL },
  };
  static const J2ObjcFieldInfo fields[] = {
    { "USER_", NULL, 0x19, "Ljava.lang.String;", &ModelApplicationPermission_USER_, NULL, .constantValue.asLong = 0 },
    { "ADMIN_", NULL, 0x19, "Ljava.lang.String;", &ModelApplicationPermission_ADMIN_, NULL, .constantValue.asLong = 0 },
  };
  static const J2ObjcClassInfo _ModelApplicationPermission = { 2, "ApplicationPermission", "rtdc.core.model", NULL, 0x1, 1, methods, 2, fields, 0, NULL, 0, NULL, NULL, NULL };
  return &_ModelApplicationPermission;
}

@end

void ModelApplicationPermission_init(ModelApplicationPermission *self) {
  NSObject_init(self);
}

ModelApplicationPermission *new_ModelApplicationPermission_init() {
  ModelApplicationPermission *self = [ModelApplicationPermission alloc];
  ModelApplicationPermission_init(self);
  return self;
}

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(ModelApplicationPermission)
