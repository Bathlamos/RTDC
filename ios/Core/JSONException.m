//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/nicolasmenard/IdeaProjects/RTDC/core/src/main/java/rtdc/core/json/JSONException.java
//

#include "J2ObjC_source.h"
#include "JSONException.h"
#include "java/lang/RuntimeException.h"
#include "java/lang/Throwable.h"

@interface JSONJSONException () {
 @public
  JavaLangThrowable *cause_;
}

@end

J2OBJC_FIELD_SETTER(JSONJSONException, cause_, JavaLangThrowable *)

@implementation JSONJSONException

- (instancetype)initWithNSString:(NSString *)message {
  JSONJSONException_initWithNSString_(self, message);
  return self;
}

- (instancetype)initWithJavaLangThrowable:(JavaLangThrowable *)t {
  JSONJSONException_initWithJavaLangThrowable_(self, t);
  return self;
}

- (JavaLangThrowable *)getCause {
  return self->cause_;
}

- (void)dealloc {
  RELEASE_(cause_);
  [super dealloc];
}

+ (const J2ObjcClassInfo *)__metadata {
  static const J2ObjcMethodInfo methods[] = {
    { "initWithNSString:", "JSONException", NULL, 0x1, NULL, NULL },
    { "initWithJavaLangThrowable:", "JSONException", NULL, 0x1, NULL, NULL },
    { "getCause", NULL, "Ljava.lang.Throwable;", 0x1, NULL, NULL },
  };
  static const J2ObjcFieldInfo fields[] = {
    { "cause_", NULL, 0x2, "Ljava.lang.Throwable;", NULL, NULL, .constantValue.asLong = 0 },
  };
  static const J2ObjcClassInfo _JSONJSONException = { 2, "JSONException", "rtdc.core.json", NULL, 0x1, 3, methods, 1, fields, 0, NULL, 0, NULL, NULL, NULL };
  return &_JSONJSONException;
}

@end

void JSONJSONException_initWithNSString_(JSONJSONException *self, NSString *message) {
  JavaLangRuntimeException_initWithNSString_(self, message);
}

JSONJSONException *new_JSONJSONException_initWithNSString_(NSString *message) {
  JSONJSONException *self = [JSONJSONException alloc];
  JSONJSONException_initWithNSString_(self, message);
  return self;
}

void JSONJSONException_initWithJavaLangThrowable_(JSONJSONException *self, JavaLangThrowable *t) {
  JavaLangRuntimeException_initWithNSString_(self, [((JavaLangThrowable *) nil_chk(t)) getMessage]);
  JreStrongAssign(&self->cause_, t);
}

JSONJSONException *new_JSONJSONException_initWithJavaLangThrowable_(JavaLangThrowable *t) {
  JSONJSONException *self = [JSONJSONException alloc];
  JSONJSONException_initWithJavaLangThrowable_(self, t);
  return self;
}

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(JSONJSONException)
