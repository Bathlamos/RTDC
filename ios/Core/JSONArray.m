//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/nicolasmenard/IdeaProjects/RTDC/core/src/main/java/rtdc/core/json/JSONArray.java
//

#include "IOSClass.h"
#include "J2ObjC_source.h"
#include "JSONArray.h"
#include "JSONException.h"
#include "JSONObject.h"
#include "JSONTokener.h"
#include "java/lang/Boolean.h"
#include "java/lang/Exception.h"
#include "java/lang/Integer.h"
#include "java/lang/Long.h"
#include "java/lang/StringBuffer.h"
#include "java/util/Vector.h"

@interface JsonJSONArray () {
 @public
  JavaUtilVector *myArrayList_;
}

@end

J2OBJC_FIELD_SETTER(JsonJSONArray, myArrayList_, JavaUtilVector *)

@implementation JsonJSONArray

J2OBJC_IGNORE_DESIGNATED_BEGIN
- (instancetype)init {
  JsonJSONArray_init(self);
  return self;
}
J2OBJC_IGNORE_DESIGNATED_END

- (instancetype)initWithJsonJSONTokener:(JsonJSONTokener *)x {
  JsonJSONArray_initWithJsonJSONTokener_(self, x);
  return self;
}

- (instancetype)initWithNSString:(NSString *)string {
  JsonJSONArray_initWithNSString_(self, string);
  return self;
}

- (instancetype)initWithJavaUtilVector:(JavaUtilVector *)collection {
  JsonJSONArray_initWithJavaUtilVector_(self, collection);
  return self;
}

- (id)getWithInt:(jint)index {
  id o = [self optWithInt:index];
  if (o == nil) {
    @throw new_JsonJSONException_initWithNSString_(JreStrcat("$I$", @"JSONArray[", index, @"] not found."));
  }
  return o;
}

- (jboolean)getBooleanWithInt:(jint)index {
  id o = [self getWithInt:index];
  if ([nil_chk(o) isEqual:JreLoadStatic(JsonJSONObject, FALSE__)] || ([o isKindOfClass:[NSString class]] && [((NSString *) nil_chk([((NSString *) check_class_cast(o, [NSString class])) lowercaseString])) isEqual:@"false"])) {
    return false;
  }
  else if ([o isEqual:JreLoadStatic(JsonJSONObject, TRUE__)] || ([o isKindOfClass:[NSString class]] && [((NSString *) nil_chk([((NSString *) check_class_cast(o, [NSString class])) lowercaseString])) isEqual:@"true"])) {
    return true;
  }
  @throw new_JsonJSONException_initWithNSString_(JreStrcat("$I$", @"JSONArray[", index, @"] is not a Boolean."));
}

- (JsonJSONArray *)getJSONArrayWithInt:(jint)index {
  id o = [self getWithInt:index];
  if ([o isKindOfClass:[JsonJSONArray class]]) {
    return (JsonJSONArray *) check_class_cast(o, [JsonJSONArray class]);
  }
  @throw new_JsonJSONException_initWithNSString_(JreStrcat("$I$", @"JSONArray[", index, @"] is not a JSONArray."));
}

- (JsonJSONObject *)getJSONObjectWithInt:(jint)index {
  id o = [self getWithInt:index];
  if ([o isKindOfClass:[JsonJSONObject class]]) {
    return (JsonJSONObject *) check_class_cast(o, [JsonJSONObject class]);
  }
  @throw new_JsonJSONException_initWithNSString_(JreStrcat("$I$", @"JSONArray[", index, @"] is not a JSONObject."));
}

- (NSString *)getStringWithInt:(jint)index {
  return [nil_chk([self getWithInt:index]) description];
}

- (jboolean)isNullWithInt:(jint)index {
  return [nil_chk(JreLoadStatic(JsonJSONObject, NULL__)) isEqual:[self optWithInt:index]];
}

- (NSString *)joinWithNSString:(NSString *)separator {
  jint len = [self length];
  JavaLangStringBuffer *sb = new_JavaLangStringBuffer_init();
  for (jint i = 0; i < len; i += 1) {
    if (i > 0) {
      (void) [sb appendWithNSString:separator];
    }
    (void) [sb appendWithNSString:JsonJSONObject_valueToStringWithId_([((JavaUtilVector *) nil_chk(self->myArrayList_)) elementAtWithInt:i])];
  }
  return [sb description];
}

- (jint)length {
  return [((JavaUtilVector *) nil_chk(self->myArrayList_)) size];
}

- (id)optWithInt:(jint)index {
  return (index < 0 || index >= [self length]) ? nil : [((JavaUtilVector *) nil_chk(self->myArrayList_)) elementAtWithInt:index];
}

- (jboolean)optBooleanWithInt:(jint)index {
  return [self optBooleanWithInt:index withBoolean:false];
}

- (jboolean)optBooleanWithInt:(jint)index
                  withBoolean:(jboolean)defaultValue {
  @try {
    return [self getBooleanWithInt:index];
  }
  @catch (JavaLangException *e) {
    return defaultValue;
  }
}

- (JsonJSONArray *)optJSONArrayWithInt:(jint)index {
  id o = [self optWithInt:index];
  return [o isKindOfClass:[JsonJSONArray class]] ? (JsonJSONArray *) check_class_cast(o, [JsonJSONArray class]) : nil;
}

- (JsonJSONObject *)optJSONObjectWithInt:(jint)index {
  id o = [self optWithInt:index];
  return [o isKindOfClass:[JsonJSONObject class]] ? (JsonJSONObject *) check_class_cast(o, [JsonJSONObject class]) : nil;
}

- (NSString *)optStringWithInt:(jint)index {
  return [self optStringWithInt:index withNSString:@""];
}

- (NSString *)optStringWithInt:(jint)index
                  withNSString:(NSString *)defaultValue {
  id o = [self optWithInt:index];
  return o != nil ? [o description] : defaultValue;
}

- (JsonJSONArray *)putWithBoolean:(jboolean)value {
  (void) [self putWithId:value ? JreLoadStatic(JsonJSONObject, TRUE__) : JreLoadStatic(JsonJSONObject, FALSE__)];
  return self;
}

- (JsonJSONArray *)putWithJavaUtilVector:(JavaUtilVector *)value {
  (void) [self putWithId:new_JsonJSONArray_initWithJavaUtilVector_(value)];
  return self;
}

- (JsonJSONArray *)putWithInt:(jint)value {
  (void) [self putWithId:new_JavaLangInteger_initWithInt_(value)];
  return self;
}

- (JsonJSONArray *)putWithLong:(jlong)value {
  (void) [self putWithId:new_JavaLangLong_initWithLong_(value)];
  return self;
}

- (JsonJSONArray *)putWithId:(id)value {
  [((JavaUtilVector *) nil_chk(self->myArrayList_)) addElementWithId:value];
  return self;
}

- (JsonJSONArray *)putWithInt:(jint)index
                  withBoolean:(jboolean)value {
  (void) [self putWithInt:index withId:value ? JreLoadStatic(JsonJSONObject, TRUE__) : JreLoadStatic(JsonJSONObject, FALSE__)];
  return self;
}

- (JsonJSONArray *)putWithInt:(jint)index
           withJavaUtilVector:(JavaUtilVector *)value {
  (void) [self putWithInt:index withId:new_JsonJSONArray_initWithJavaUtilVector_(value)];
  return self;
}

- (JsonJSONArray *)putWithInt:(jint)index
                      withInt:(jint)value {
  (void) [self putWithInt:index withId:new_JavaLangInteger_initWithInt_(value)];
  return self;
}

- (JsonJSONArray *)putWithInt:(jint)index
                     withLong:(jlong)value {
  (void) [self putWithInt:index withId:new_JavaLangLong_initWithLong_(value)];
  return self;
}

- (JsonJSONArray *)putWithInt:(jint)index
                       withId:(id)value {
  JsonJSONObject_testValidityWithId_(value);
  if (index < 0) {
    @throw new_JsonJSONException_initWithNSString_(JreStrcat("$I$", @"JSONArray[", index, @"] not found."));
  }
  if (index < [self length]) {
    [((JavaUtilVector *) nil_chk(self->myArrayList_)) setElementAtWithId:value withInt:index];
  }
  else {
    while (index != [self length]) {
      (void) [self putWithId:JreLoadStatic(JsonJSONObject, NULL__)];
    }
    (void) [self putWithId:value];
  }
  return self;
}

- (JsonJSONObject *)toJSONObjectWithJsonJSONArray:(JsonJSONArray *)names {
  if (names == nil || [names length] == 0 || [self length] == 0) {
    return nil;
  }
  JsonJSONObject *jo = new_JsonJSONObject_init();
  for (jint i = 0; i < [((JsonJSONArray *) nil_chk(names)) length]; i += 1) {
    (void) [jo putWithNSString:[names getStringWithInt:i] withId:[self optWithInt:i]];
  }
  return jo;
}

- (NSString *)description {
  @try {
    return JreStrcat("C$C", '[', [self joinWithNSString:@","], ']');
  }
  @catch (JavaLangException *e) {
    return nil;
  }
}

- (NSString *)toStringWithInt:(jint)indentFactor {
  return [self toStringWithInt:indentFactor withInt:0];
}

- (NSString *)toStringWithInt:(jint)indentFactor
                      withInt:(jint)indent {
  jint len = [self length];
  if (len == 0) {
    return @"[]";
  }
  jint i;
  JavaLangStringBuffer *sb = new_JavaLangStringBuffer_initWithNSString_(@"[");
  if (len == 1) {
    (void) [sb appendWithNSString:JsonJSONObject_valueToStringWithId_withInt_withInt_([((JavaUtilVector *) nil_chk(self->myArrayList_)) elementAtWithInt:0], indentFactor, indent)];
  }
  else {
    jint newindent = indent + indentFactor;
    (void) [sb appendWithChar:0x000a];
    for (i = 0; i < len; i += 1) {
      if (i > 0) {
        (void) [sb appendWithNSString:@",\n"];
      }
      for (jint j = 0; j < newindent; j += 1) {
        (void) [sb appendWithChar:' '];
      }
      (void) [sb appendWithNSString:JsonJSONObject_valueToStringWithId_withInt_withInt_([((JavaUtilVector *) nil_chk(self->myArrayList_)) elementAtWithInt:i], indentFactor, newindent)];
    }
    (void) [sb appendWithChar:0x000a];
    for (i = 0; i < indent; i += 1) {
      (void) [sb appendWithChar:' '];
    }
  }
  (void) [sb appendWithChar:']'];
  return [sb description];
}

+ (const J2ObjcClassInfo *)__metadata {
  static const J2ObjcMethodInfo methods[] = {
    { "init", "JSONArray", NULL, 0x1, NULL, NULL },
    { "initWithJsonJSONTokener:", "JSONArray", NULL, 0x1, "Lrtdc.core.json.JSONException;", NULL },
    { "initWithNSString:", "JSONArray", NULL, 0x1, "Lrtdc.core.json.JSONException;", NULL },
    { "initWithJavaUtilVector:", "JSONArray", NULL, 0x1, NULL, NULL },
    { "getWithInt:", "get", "Ljava.lang.Object;", 0x1, "Lrtdc.core.json.JSONException;", NULL },
    { "getBooleanWithInt:", "getBoolean", "Z", 0x1, "Lrtdc.core.json.JSONException;", NULL },
    { "getJSONArrayWithInt:", "getJSONArray", "Lrtdc.core.json.JSONArray;", 0x1, "Lrtdc.core.json.JSONException;", NULL },
    { "getJSONObjectWithInt:", "getJSONObject", "Lrtdc.core.json.JSONObject;", 0x1, "Lrtdc.core.json.JSONException;", NULL },
    { "getStringWithInt:", "getString", "Ljava.lang.String;", 0x1, "Lrtdc.core.json.JSONException;", NULL },
    { "isNullWithInt:", "isNull", "Z", 0x1, NULL, NULL },
    { "joinWithNSString:", "join", "Ljava.lang.String;", 0x1, "Lrtdc.core.json.JSONException;", NULL },
    { "length", NULL, "I", 0x1, NULL, NULL },
    { "optWithInt:", "opt", "Ljava.lang.Object;", 0x1, NULL, NULL },
    { "optBooleanWithInt:", "optBoolean", "Z", 0x1, NULL, NULL },
    { "optBooleanWithInt:withBoolean:", "optBoolean", "Z", 0x1, NULL, NULL },
    { "optJSONArrayWithInt:", "optJSONArray", "Lrtdc.core.json.JSONArray;", 0x1, NULL, NULL },
    { "optJSONObjectWithInt:", "optJSONObject", "Lrtdc.core.json.JSONObject;", 0x1, NULL, NULL },
    { "optStringWithInt:", "optString", "Ljava.lang.String;", 0x1, NULL, NULL },
    { "optStringWithInt:withNSString:", "optString", "Ljava.lang.String;", 0x1, NULL, NULL },
    { "putWithBoolean:", "put", "Lrtdc.core.json.JSONArray;", 0x1, NULL, NULL },
    { "putWithJavaUtilVector:", "put", "Lrtdc.core.json.JSONArray;", 0x1, NULL, NULL },
    { "putWithInt:", "put", "Lrtdc.core.json.JSONArray;", 0x1, NULL, NULL },
    { "putWithLong:", "put", "Lrtdc.core.json.JSONArray;", 0x1, NULL, NULL },
    { "putWithId:", "put", "Lrtdc.core.json.JSONArray;", 0x1, NULL, NULL },
    { "putWithInt:withBoolean:", "put", "Lrtdc.core.json.JSONArray;", 0x1, "Lrtdc.core.json.JSONException;", NULL },
    { "putWithInt:withJavaUtilVector:", "put", "Lrtdc.core.json.JSONArray;", 0x1, "Lrtdc.core.json.JSONException;", NULL },
    { "putWithInt:withInt:", "put", "Lrtdc.core.json.JSONArray;", 0x1, "Lrtdc.core.json.JSONException;", NULL },
    { "putWithInt:withLong:", "put", "Lrtdc.core.json.JSONArray;", 0x1, "Lrtdc.core.json.JSONException;", NULL },
    { "putWithInt:withId:", "put", "Lrtdc.core.json.JSONArray;", 0x1, "Lrtdc.core.json.JSONException;", NULL },
    { "toJSONObjectWithJsonJSONArray:", "toJSONObject", "Lrtdc.core.json.JSONObject;", 0x1, "Lrtdc.core.json.JSONException;", NULL },
    { "description", "toString", "Ljava.lang.String;", 0x1, NULL, NULL },
    { "toStringWithInt:", "toString", "Ljava.lang.String;", 0x1, "Lrtdc.core.json.JSONException;", NULL },
    { "toStringWithInt:withInt:", "toString", "Ljava.lang.String;", 0x0, "Lrtdc.core.json.JSONException;", NULL },
  };
  static const J2ObjcFieldInfo fields[] = {
    { "myArrayList_", NULL, 0x2, "Ljava.util.Vector;", NULL, NULL, .constantValue.asLong = 0 },
  };
  static const J2ObjcClassInfo _JsonJSONArray = { 2, "JSONArray", "rtdc.core.json", NULL, 0x1, 33, methods, 1, fields, 0, NULL, 0, NULL, NULL, NULL };
  return &_JsonJSONArray;
}

@end

void JsonJSONArray_init(JsonJSONArray *self) {
  (void) NSObject_init(self);
  self->myArrayList_ = new_JavaUtilVector_init();
}

JsonJSONArray *new_JsonJSONArray_init() {
  JsonJSONArray *self = [JsonJSONArray alloc];
  JsonJSONArray_init(self);
  return self;
}

void JsonJSONArray_initWithJsonJSONTokener_(JsonJSONArray *self, JsonJSONTokener *x) {
  (void) JsonJSONArray_init(self);
  if ([((JsonJSONTokener *) nil_chk(x)) nextClean] != '[') {
    @throw [x syntaxErrorWithNSString:@"A JSONArray text must start with '['"];
  }
  if ([x nextClean] == ']') {
    return;
  }
  [x back];
  for (; ; ) {
    if ([x nextClean] == ',') {
      [x back];
      [((JavaUtilVector *) nil_chk(self->myArrayList_)) addElementWithId:nil];
    }
    else {
      [x back];
      [((JavaUtilVector *) nil_chk(self->myArrayList_)) addElementWithId:[x nextValue]];
    }
    switch ([x nextClean]) {
      case ';':
      case ',':
      if ([x nextClean] == ']') {
        return;
      }
      [x back];
      break;
      case ']':
      return;
      default:
      @throw [x syntaxErrorWithNSString:@"Expected a ',' or ']'"];
    }
  }
}

JsonJSONArray *new_JsonJSONArray_initWithJsonJSONTokener_(JsonJSONTokener *x) {
  JsonJSONArray *self = [JsonJSONArray alloc];
  JsonJSONArray_initWithJsonJSONTokener_(self, x);
  return self;
}

void JsonJSONArray_initWithNSString_(JsonJSONArray *self, NSString *string) {
  (void) JsonJSONArray_initWithJsonJSONTokener_(self, new_JsonJSONTokener_initWithNSString_(string));
}

JsonJSONArray *new_JsonJSONArray_initWithNSString_(NSString *string) {
  JsonJSONArray *self = [JsonJSONArray alloc];
  JsonJSONArray_initWithNSString_(self, string);
  return self;
}

void JsonJSONArray_initWithJavaUtilVector_(JsonJSONArray *self, JavaUtilVector *collection) {
  (void) NSObject_init(self);
  if (collection == nil) {
    self->myArrayList_ = new_JavaUtilVector_init();
  }
  else {
    jint size = [collection size];
    self->myArrayList_ = new_JavaUtilVector_initWithInt_(size);
    for (jint i = 0; i < size; i++) {
      [self->myArrayList_ addElementWithId:[collection elementAtWithInt:i]];
    }
  }
}

JsonJSONArray *new_JsonJSONArray_initWithJavaUtilVector_(JavaUtilVector *collection) {
  JsonJSONArray *self = [JsonJSONArray alloc];
  JsonJSONArray_initWithJavaUtilVector_(self, collection);
  return self;
}

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(JsonJSONArray)
