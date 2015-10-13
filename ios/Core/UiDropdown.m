//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/nicolasmenard/IdeaProjects/RTDC/core/src/main/java/rtdc/core/impl/UiDropdown.java
//

#include "IOSObjectArray.h"
#include "J2ObjC_source.h"
#include "Stringifier.h"
#include "UiDropdown.h"

@interface ImplUiDropdown_$1 : NSObject < UtilStringifier >

- (NSString *)toStringWithId:(id)object;

- (instancetype)init;

@end

J2OBJC_EMPTY_STATIC_INIT(ImplUiDropdown_$1)

__attribute__((unused)) static void ImplUiDropdown_$1_init(ImplUiDropdown_$1 *self);

__attribute__((unused)) static ImplUiDropdown_$1 *new_ImplUiDropdown_$1_init() NS_RETURNS_RETAINED;

J2OBJC_TYPE_LITERAL_HEADER(ImplUiDropdown_$1)

J2OBJC_INITIALIZED_DEFN(ImplUiDropdown)

id<UtilStringifier> ImplUiDropdown_DEFAULT_STRINGIFIER_;

@implementation ImplUiDropdown

+ (id<UtilStringifier>)DEFAULT_STRINGIFIER {
  return ImplUiDropdown_DEFAULT_STRINGIFIER_;
}

+ (void)initialize {
  if (self == [ImplUiDropdown class]) {
    JreStrongAssignAndConsume(&ImplUiDropdown_DEFAULT_STRINGIFIER_, new_ImplUiDropdown_$1_init());
    J2OBJC_SET_INITIALIZED(ImplUiDropdown)
  }
}

+ (const J2ObjcClassInfo *)__metadata {
  static const J2ObjcMethodInfo methods[] = {
    { "setArrayWithNSObjectArray:", "setArray", "V", 0x401, NULL, NULL },
    { "getSelectedIndex", NULL, "I", 0x401, NULL, NULL },
    { "setStringifierWithUtilStringifier:", "setStringifier", "V", 0x401, NULL, NULL },
  };
  static const J2ObjcFieldInfo fields[] = {
    { "DEFAULT_STRINGIFIER_", NULL, 0x19, "Lrtdc.core.util.Stringifier;", &ImplUiDropdown_DEFAULT_STRINGIFIER_, NULL, .constantValue.asLong = 0 },
  };
  static const J2ObjcClassInfo _ImplUiDropdown = { 2, "UiDropdown", "rtdc.core.impl", NULL, 0x609, 3, methods, 1, fields, 0, NULL, 0, NULL, NULL, "<T:Ljava/lang/Object;>Ljava/lang/Object;Lrtdc/core/impl/UiElement<TT;>;" };
  return &_ImplUiDropdown;
}

@end

J2OBJC_INTERFACE_TYPE_LITERAL_SOURCE(ImplUiDropdown)

@implementation ImplUiDropdown_$1

- (NSString *)toStringWithId:(id)object {
  return [nil_chk(object) description];
}

J2OBJC_IGNORE_DESIGNATED_BEGIN
- (instancetype)init {
  ImplUiDropdown_$1_init(self);
  return self;
}
J2OBJC_IGNORE_DESIGNATED_END

+ (const J2ObjcClassInfo *)__metadata {
  static const J2ObjcMethodInfo methods[] = {
    { "toStringWithId:", "toString", "Ljava.lang.String;", 0x1, NULL, NULL },
    { "init", "", NULL, 0x0, NULL, NULL },
  };
  static const J2ObjcClassInfo _ImplUiDropdown_$1 = { 2, "", "rtdc.core.impl", "UiDropdown", 0x8008, 2, methods, 0, NULL, 0, NULL, 0, NULL, NULL, NULL };
  return &_ImplUiDropdown_$1;
}

@end

void ImplUiDropdown_$1_init(ImplUiDropdown_$1 *self) {
  NSObject_init(self);
}

ImplUiDropdown_$1 *new_ImplUiDropdown_$1_init() {
  ImplUiDropdown_$1 *self = [ImplUiDropdown_$1 alloc];
  ImplUiDropdown_$1_init(self);
  return self;
}

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(ImplUiDropdown_$1)
