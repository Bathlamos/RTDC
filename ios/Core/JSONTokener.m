//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/nicolasmenard/IdeaProjects/RTDC/core/src/main/java/rtdc/core/json/JSONTokener.java
//

#include "IOSClass.h"
#include "J2ObjC_source.h"
#include "JSONArray.h"
#include "JSONException.h"
#include "JSONObject.h"
#include "JSONTokener.h"
#include "java/lang/Exception.h"
#include "java/lang/Integer.h"
#include "java/lang/Long.h"
#include "java/lang/StringBuffer.h"

@interface JsonJSONTokener () {
 @public
  jint myIndex_;
  NSString *mySource_;
}

@end

J2OBJC_FIELD_SETTER(JsonJSONTokener, mySource_, NSString *)

@implementation JsonJSONTokener

- (instancetype)initWithNSString:(NSString *)s {
  JsonJSONTokener_initWithNSString_(self, s);
  return self;
}

- (void)back {
  if (self->myIndex_ > 0) {
    self->myIndex_ -= 1;
  }
}

+ (jint)dehexcharWithChar:(jchar)c {
  return JsonJSONTokener_dehexcharWithChar_(c);
}

- (jboolean)more {
  return self->myIndex_ < ((jint) [((NSString *) nil_chk(self->mySource_)) length]);
}

- (jchar)next {
  if ([self more]) {
    jchar c = [((NSString *) nil_chk(self->mySource_)) charAtWithInt:self->myIndex_];
    self->myIndex_ += 1;
    return c;
  }
  return 0;
}

- (jchar)nextWithChar:(jchar)c {
  jchar n = [self next];
  if (n != c) {
    @throw [self syntaxErrorWithNSString:JreStrcat("$C$C$", @"Expected '", c, @"' and instead saw '", n, @"'.")];
  }
  return n;
}

- (NSString *)nextWithInt:(jint)n {
  jint i = self->myIndex_;
  jint j = i + n;
  if (j >= ((jint) [((NSString *) nil_chk(self->mySource_)) length])) {
    @throw [self syntaxErrorWithNSString:@"Substring bounds error"];
  }
  self->myIndex_ += n;
  return [self->mySource_ substring:i endIndex:j];
}

- (jchar)nextClean {
  for (; ; ) {
    jchar c = [self next];
    if (c == '/') {
      switch ([self next]) {
        case '/':
        do {
          c = [self next];
        }
        while (c != 0x000a && c != 0x000d && c != 0);
        break;
        case '*':
        for (; ; ) {
          c = [self next];
          if (c == 0) {
            @throw [self syntaxErrorWithNSString:@"Unclosed comment."];
          }
          if (c == '*') {
            if ([self next] == '/') {
              break;
            }
            [self back];
          }
        }
        break;
        default:
        [self back];
        return '/';
      }
    }
    else if (c == '#') {
      do {
        c = [self next];
      }
      while (c != 0x000a && c != 0x000d && c != 0);
    }
    else if (c == 0 || c > ' ') {
      return c;
    }
  }
}

- (NSString *)nextStringWithChar:(jchar)quote {
  jchar c;
  JavaLangStringBuffer *sb = [new_JavaLangStringBuffer_init() autorelease];
  for (; ; ) {
    c = [self next];
    switch (c) {
      case 0:
      case 0x000a:
      case 0x000d:
      @throw [self syntaxErrorWithNSString:@"Unterminated string"];
      case '\\':
      c = [self next];
      switch (c) {
        case 'b':
        [sb appendWithChar:0x0008];
        break;
        case 't':
        [sb appendWithChar:0x0009];
        break;
        case 'n':
        [sb appendWithChar:0x000a];
        break;
        case 'f':
        [sb appendWithChar:0x000c];
        break;
        case 'r':
        [sb appendWithChar:0x000d];
        break;
        case 'u':
        [sb appendWithChar:(jchar) JavaLangInteger_parseIntWithNSString_withInt_([self nextWithInt:4], 16)];
        break;
        case 'x':
        [sb appendWithChar:(jchar) JavaLangInteger_parseIntWithNSString_withInt_([self nextWithInt:2], 16)];
        break;
        default:
        [sb appendWithChar:c];
      }
      break;
      default:
      if (c == quote) {
        return [sb description];
      }
      [sb appendWithChar:c];
    }
  }
}

- (NSString *)nextToWithChar:(jchar)d {
  JavaLangStringBuffer *sb = [new_JavaLangStringBuffer_init() autorelease];
  for (; ; ) {
    jchar c = [self next];
    if (c == d || c == 0 || c == 0x000a || c == 0x000d) {
      if (c != 0) {
        [self back];
      }
      return [((NSString *) nil_chk([sb description])) trim];
    }
    [sb appendWithChar:c];
  }
}

- (NSString *)nextToWithNSString:(NSString *)delimiters {
  jchar c;
  JavaLangStringBuffer *sb = [new_JavaLangStringBuffer_init() autorelease];
  for (; ; ) {
    c = [self next];
    if ([((NSString *) nil_chk(delimiters)) indexOf:c] >= 0 || c == 0 || c == 0x000a || c == 0x000d) {
      if (c != 0) {
        [self back];
      }
      return [((NSString *) nil_chk([sb description])) trim];
    }
    [sb appendWithChar:c];
  }
}

- (id)nextValue {
  jchar c = [self nextClean];
  NSString *s;
  switch (c) {
    case '"':
    case '\'':
    return [self nextStringWithChar:c];
    case '{':
    [self back];
    return [new_JsonJSONObject_initWithJsonJSONTokener_(self) autorelease];
    case '[':
    [self back];
    return [new_JsonJSONArray_initWithJsonJSONTokener_(self) autorelease];
  }
  JavaLangStringBuffer *sb = [new_JavaLangStringBuffer_init() autorelease];
  jchar b = c;
  while (c >= ' ' && [@",:]}/\\\"[{;=#" indexOf:c] < 0) {
    [sb appendWithChar:c];
    c = [self next];
  }
  [self back];
  s = [((NSString *) nil_chk([sb description])) trim];
  if ([((NSString *) nil_chk(s)) isEqual:@""]) {
    @throw [self syntaxErrorWithNSString:@"Missing value."];
  }
  if ([((NSString *) nil_chk([s lowercaseString])) isEqual:@"true"]) {
    return JreLoadStatic(JsonJSONObject, TRUE__);
  }
  if ([((NSString *) nil_chk([s lowercaseString])) isEqual:@"false"]) {
    return JreLoadStatic(JsonJSONObject, FALSE__);
  }
  if ([((NSString *) nil_chk([s lowercaseString])) isEqual:@"null"]) {
    return JreLoadStatic(JsonJSONObject, NULL__);
  }
  if ((b >= '0' && b <= '9') || b == '.' || b == '-' || b == '+') {
    if (b == '0') {
      if (((jint) [s length]) > 2 && ([s charAtWithInt:1] == 'x' || [s charAtWithInt:1] == 'X')) {
        @try {
          return [new_JavaLangInteger_initWithInt_(JavaLangInteger_parseIntWithNSString_withInt_([s substring:2], 16)) autorelease];
        }
        @catch (JavaLangException *e) {
        }
      }
      else {
        @try {
          return [new_JavaLangInteger_initWithInt_(JavaLangInteger_parseIntWithNSString_withInt_(s, 8)) autorelease];
        }
        @catch (JavaLangException *e) {
        }
      }
    }
    @try {
      return JavaLangInteger_valueOfWithNSString_(s);
    }
    @catch (JavaLangException *e) {
      @try {
        return [new_JavaLangLong_initWithLong_(JavaLangLong_parseLongWithNSString_(s)) autorelease];
      }
      @catch (JavaLangException *f) {
        return s;
      }
    }
  }
  return s;
}

- (jchar)skipToWithChar:(jchar)to {
  jchar c;
  jint index = self->myIndex_;
  do {
    c = [self next];
    if (c == 0) {
      self->myIndex_ = index;
      return c;
    }
  }
  while (c != to);
  [self back];
  return c;
}

- (void)skipPastWithNSString:(NSString *)to {
  self->myIndex_ = [((NSString *) nil_chk(self->mySource_)) indexOfString:to fromIndex:self->myIndex_];
  if (self->myIndex_ < 0) {
    self->myIndex_ = ((jint) [self->mySource_ length]);
  }
  else {
    self->myIndex_ += ((jint) [((NSString *) nil_chk(to)) length]);
  }
}

- (JsonJSONException *)syntaxErrorWithNSString:(NSString *)message {
  return [new_JsonJSONException_initWithNSString_(JreStrcat("$$", message, [self description])) autorelease];
}

- (NSString *)description {
  return JreStrcat("$I$$", @" at character ", self->myIndex_, @" of ", self->mySource_);
}

- (void)dealloc {
  RELEASE_(mySource_);
  [super dealloc];
}

+ (const J2ObjcClassInfo *)__metadata {
  static const J2ObjcMethodInfo methods[] = {
    { "initWithNSString:", "JSONTokener", NULL, 0x1, NULL, NULL },
    { "back", NULL, "V", 0x1, NULL, NULL },
    { "dehexcharWithChar:", "dehexchar", "I", 0x9, NULL, NULL },
    { "more", NULL, "Z", 0x1, NULL, NULL },
    { "next", NULL, "C", 0x1, NULL, NULL },
    { "nextWithChar:", "next", "C", 0x1, "Lrtdc.core.json.JSONException;", NULL },
    { "nextWithInt:", "next", "Ljava.lang.String;", 0x1, "Lrtdc.core.json.JSONException;", NULL },
    { "nextClean", NULL, "C", 0x1, "Lrtdc.core.json.JSONException;", NULL },
    { "nextStringWithChar:", "nextString", "Ljava.lang.String;", 0x1, "Lrtdc.core.json.JSONException;", NULL },
    { "nextToWithChar:", "nextTo", "Ljava.lang.String;", 0x1, NULL, NULL },
    { "nextToWithNSString:", "nextTo", "Ljava.lang.String;", 0x1, NULL, NULL },
    { "nextValue", NULL, "Ljava.lang.Object;", 0x1, "Lrtdc.core.json.JSONException;", NULL },
    { "skipToWithChar:", "skipTo", "C", 0x1, NULL, NULL },
    { "skipPastWithNSString:", "skipPast", "V", 0x1, NULL, NULL },
    { "syntaxErrorWithNSString:", "syntaxError", "Lrtdc.core.json.JSONException;", 0x1, NULL, NULL },
    { "description", "toString", "Ljava.lang.String;", 0x1, NULL, NULL },
  };
  static const J2ObjcFieldInfo fields[] = {
    { "myIndex_", NULL, 0x2, "I", NULL, NULL, .constantValue.asLong = 0 },
    { "mySource_", NULL, 0x2, "Ljava.lang.String;", NULL, NULL, .constantValue.asLong = 0 },
  };
  static const J2ObjcClassInfo _JsonJSONTokener = { 2, "JSONTokener", "rtdc.core.json", NULL, 0x1, 16, methods, 2, fields, 0, NULL, 0, NULL, NULL, NULL };
  return &_JsonJSONTokener;
}

@end

void JsonJSONTokener_initWithNSString_(JsonJSONTokener *self, NSString *s) {
  NSObject_init(self);
  self->myIndex_ = 0;
  JreStrongAssign(&self->mySource_, s);
}

JsonJSONTokener *new_JsonJSONTokener_initWithNSString_(NSString *s) {
  JsonJSONTokener *self = [JsonJSONTokener alloc];
  JsonJSONTokener_initWithNSString_(self, s);
  return self;
}

jint JsonJSONTokener_dehexcharWithChar_(jchar c) {
  JsonJSONTokener_initialize();
  if (c >= '0' && c <= '9') {
    return c - '0';
  }
  if (c >= 'A' && c <= 'F') {
    return c - ('A' - 10);
  }
  if (c >= 'a' && c <= 'f') {
    return c - ('a' - 10);
  }
  return -1;
}

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(JsonJSONTokener)
