//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/nicolasmenard/IdeaProjects/RTDC/core/src/main/java/rtdc/core/event/FetchUsersEvent.java
//

#include "Event.h"
#include "EventType.h"
#include "FetchUsersEvent.h"
#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "J2ObjC_source.h"
#include "JSONArray.h"
#include "JSONObject.h"
#include "ObjectProperty.h"
#include "RootObject.h"
#include "User.h"
#include "com/google/common/base/Function.h"
#include "com/google/common/collect/ImmutableSet.h"
#include "java/lang/Enum.h"
#include "java/lang/IllegalArgumentException.h"
#include "java/lang/Iterable.h"
#include "java/util/ArrayList.h"

@interface EventFetchUsersEvent () {
 @public
  ComGoogleCommonCollectImmutableSet *users_;
}

@end

J2OBJC_FIELD_SETTER(EventFetchUsersEvent, users_, ComGoogleCommonCollectImmutableSet *)

@interface EventFetchUsersEvent_Handler : NSObject

@end

__attribute__((unused)) static void EventFetchUsersEvent_PropertiesEnum_initWithNSString_withInt_(EventFetchUsersEvent_PropertiesEnum *self, NSString *__name, jint __ordinal);

__attribute__((unused)) static EventFetchUsersEvent_PropertiesEnum *new_EventFetchUsersEvent_PropertiesEnum_initWithNSString_withInt_(NSString *__name, jint __ordinal) NS_RETURNS_RETAINED;

@interface EventFetchUsersEvent_$1 : NSObject < ComGoogleCommonBaseFunction >

- (ModelUser *)applyWithId:(JsonJSONObject *)input;

- (instancetype)init;

@end

J2OBJC_EMPTY_STATIC_INIT(EventFetchUsersEvent_$1)

__attribute__((unused)) static void EventFetchUsersEvent_$1_init(EventFetchUsersEvent_$1 *self);

__attribute__((unused)) static EventFetchUsersEvent_$1 *new_EventFetchUsersEvent_$1_init() NS_RETURNS_RETAINED;

J2OBJC_TYPE_LITERAL_HEADER(EventFetchUsersEvent_$1)

J2OBJC_INITIALIZED_DEFN(EventFetchUsersEvent)

EventEventType *EventFetchUsersEvent_TYPE_;

@implementation EventFetchUsersEvent

+ (EventEventType *)TYPE {
  return EventFetchUsersEvent_TYPE_;
}

- (instancetype)initWithJavaLangIterable:(id<JavaLangIterable>)units {
  EventFetchUsersEvent_initWithJavaLangIterable_(self, units);
  return self;
}

- (instancetype)initWithJsonJSONObject:(JsonJSONObject *)object {
  EventFetchUsersEvent_initWithJsonJSONObject_(self, object);
  return self;
}

- (ComGoogleCommonCollectImmutableSet *)getUsers {
  return users_;
}

- (void)fire {
  for (id<EventFetchUsersEvent_Handler> __strong handler in nil_chk([self getHandlersWithEventEventType:EventFetchUsersEvent_TYPE_])) [((id<EventFetchUsersEvent_Handler>) nil_chk(handler)) onUsersFetchedWithEventFetchUsersEvent:self];
}

- (IOSObjectArray *)getProperties {
  return EventFetchUsersEvent_PropertiesEnum_values();
}

- (NSString *)getType {
  return [((EventEventType *) nil_chk(EventFetchUsersEvent_TYPE_)) getName];
}

- (id)getValueWithModelObjectProperty:(id<ModelObjectProperty>)property {
  switch ([(EventFetchUsersEvent_PropertiesEnum *) check_class_cast(property, [EventFetchUsersEvent_PropertiesEnum class]) ordinal]) {
    case EventFetchUsersEvent_Properties_users:
    return users_;
  }
  return nil;
}

+ (void)initialize {
  if (self == [EventFetchUsersEvent class]) {
    EventFetchUsersEvent_TYPE_ = new_EventEventType_initWithNSString_(@"fetchUsersEvent");
    J2OBJC_SET_INITIALIZED(EventFetchUsersEvent)
  }
}

+ (const J2ObjcClassInfo *)__metadata {
  static const J2ObjcMethodInfo methods[] = {
    { "initWithJavaLangIterable:", "FetchUsersEvent", NULL, 0x1, NULL, NULL },
    { "initWithJsonJSONObject:", "FetchUsersEvent", NULL, 0x1, NULL, NULL },
    { "getUsers", NULL, "Lcom.google.common.collect.ImmutableSet;", 0x1, NULL, NULL },
    { "fire", NULL, "V", 0x1, NULL, NULL },
    { "getProperties", NULL, "[Lrtdc.core.model.ObjectProperty;", 0x1, NULL, NULL },
    { "getType", NULL, "Ljava.lang.String;", 0x1, NULL, NULL },
    { "getValueWithModelObjectProperty:", "getValue", "Ljava.lang.Object;", 0x1, NULL, NULL },
  };
  static const J2ObjcFieldInfo fields[] = {
    { "TYPE_", NULL, 0x19, "Lrtdc.core.event.EventType;", &EventFetchUsersEvent_TYPE_, "Lrtdc/core/event/EventType<Lrtdc/core/event/FetchUsersEvent$Handler;>;", .constantValue.asLong = 0 },
    { "users_", NULL, 0x12, "Lcom.google.common.collect.ImmutableSet;", NULL, "Lcom/google/common/collect/ImmutableSet<Lrtdc/core/model/User;>;", .constantValue.asLong = 0 },
  };
  static const char *superclass_type_args[] = {"Lrtdc.core.event.FetchUsersEvent$Handler;"};
  static const char *inner_classes[] = {"Lrtdc.core.event.FetchUsersEvent$Handler;", "Lrtdc.core.event.FetchUsersEvent$Properties;"};
  static const J2ObjcClassInfo _EventFetchUsersEvent = { 2, "FetchUsersEvent", "rtdc.core.event", NULL, 0x1, 7, methods, 2, fields, 1, superclass_type_args, 2, inner_classes, NULL, "Lrtdc/core/event/Event<Lrtdc/core/event/FetchUsersEvent$Handler;>;" };
  return &_EventFetchUsersEvent;
}

@end

void EventFetchUsersEvent_initWithJavaLangIterable_(EventFetchUsersEvent *self, id<JavaLangIterable> units) {
  (void) EventEvent_init(self);
  self->users_ = ComGoogleCommonCollectImmutableSet_copyOfWithJavaLangIterable_(units);
}

EventFetchUsersEvent *new_EventFetchUsersEvent_initWithJavaLangIterable_(id<JavaLangIterable> units) {
  EventFetchUsersEvent *self = [EventFetchUsersEvent alloc];
  EventFetchUsersEvent_initWithJavaLangIterable_(self, units);
  return self;
}

void EventFetchUsersEvent_initWithJsonJSONObject_(EventFetchUsersEvent *self, JsonJSONObject *object) {
  (void) EventEvent_init(self);
  self->users_ = ComGoogleCommonCollectImmutableSet_copyOfWithJavaUtilCollection_([self parseJsonArrayWithJsonJSONArray:[((JsonJSONObject *) nil_chk(object)) getJSONArrayWithNSString:[((EventFetchUsersEvent_PropertiesEnum *) nil_chk(JreLoadStatic(EventFetchUsersEvent_PropertiesEnum, users))) name]] withComGoogleCommonBaseFunction:new_EventFetchUsersEvent_$1_init()]);
}

EventFetchUsersEvent *new_EventFetchUsersEvent_initWithJsonJSONObject_(JsonJSONObject *object) {
  EventFetchUsersEvent *self = [EventFetchUsersEvent alloc];
  EventFetchUsersEvent_initWithJsonJSONObject_(self, object);
  return self;
}

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(EventFetchUsersEvent)

@implementation EventFetchUsersEvent_Handler

+ (const J2ObjcClassInfo *)__metadata {
  static const J2ObjcMethodInfo methods[] = {
    { "onUsersFetchedWithEventFetchUsersEvent:", "onUsersFetched", "V", 0x401, NULL, NULL },
  };
  static const J2ObjcClassInfo _EventFetchUsersEvent_Handler = { 2, "Handler", "rtdc.core.event", "FetchUsersEvent", 0x609, 1, methods, 0, NULL, 0, NULL, 0, NULL, NULL, NULL };
  return &_EventFetchUsersEvent_Handler;
}

@end

J2OBJC_INTERFACE_TYPE_LITERAL_SOURCE(EventFetchUsersEvent_Handler)

J2OBJC_INITIALIZED_DEFN(EventFetchUsersEvent_PropertiesEnum)

EventFetchUsersEvent_PropertiesEnum *EventFetchUsersEvent_PropertiesEnum_values_[1];

@implementation EventFetchUsersEvent_PropertiesEnum

+ (EventFetchUsersEvent_PropertiesEnum *)users {
  return EventFetchUsersEvent_PropertiesEnum_users;
}

- (instancetype)initWithNSString:(NSString *)__name
                         withInt:(jint)__ordinal {
  EventFetchUsersEvent_PropertiesEnum_initWithNSString_withInt_(self, __name, __ordinal);
  return self;
}

IOSObjectArray *EventFetchUsersEvent_PropertiesEnum_values() {
  EventFetchUsersEvent_PropertiesEnum_initialize();
  return [IOSObjectArray arrayWithObjects:EventFetchUsersEvent_PropertiesEnum_values_ count:1 type:EventFetchUsersEvent_PropertiesEnum_class_()];
}

+ (IOSObjectArray *)values {
  return EventFetchUsersEvent_PropertiesEnum_values();
}

+ (EventFetchUsersEvent_PropertiesEnum *)valueOfWithNSString:(NSString *)name {
  return EventFetchUsersEvent_PropertiesEnum_valueOfWithNSString_(name);
}

EventFetchUsersEvent_PropertiesEnum *EventFetchUsersEvent_PropertiesEnum_valueOfWithNSString_(NSString *name) {
  EventFetchUsersEvent_PropertiesEnum_initialize();
  for (int i = 0; i < 1; i++) {
    EventFetchUsersEvent_PropertiesEnum *e = EventFetchUsersEvent_PropertiesEnum_values_[i];
    if ([name isEqual:[e name]]) {
      return e;
    }
  }
  @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:name];
  return nil;
}

- (id)copyWithZone:(NSZone *)zone {
  return self;
}

+ (void)initialize {
  if (self == [EventFetchUsersEvent_PropertiesEnum class]) {
    EventFetchUsersEvent_PropertiesEnum_users = new_EventFetchUsersEvent_PropertiesEnum_initWithNSString_withInt_(@"users", 0);
    J2OBJC_SET_INITIALIZED(EventFetchUsersEvent_PropertiesEnum)
  }
}

+ (const J2ObjcClassInfo *)__metadata {
  static const J2ObjcFieldInfo fields[] = {
    { "users", "users", 0x4019, "Lrtdc.core.event.FetchUsersEvent$Properties;", &EventFetchUsersEvent_PropertiesEnum_users, NULL, .constantValue.asLong = 0 },
  };
  static const char *superclass_type_args[] = {"Lrtdc.core.event.FetchUsersEvent$Properties;"};
  static const J2ObjcClassInfo _EventFetchUsersEvent_PropertiesEnum = { 2, "Properties", "rtdc.core.event", "FetchUsersEvent", 0x4019, 0, NULL, 1, fields, 1, superclass_type_args, 0, NULL, NULL, "Ljava/lang/Enum<Lrtdc/core/event/FetchUsersEvent$Properties;>;Lrtdc/core/model/ObjectProperty<Lrtdc/core/event/FetchUsersEvent;>;" };
  return &_EventFetchUsersEvent_PropertiesEnum;
}

@end

void EventFetchUsersEvent_PropertiesEnum_initWithNSString_withInt_(EventFetchUsersEvent_PropertiesEnum *self, NSString *__name, jint __ordinal) {
  (void) JavaLangEnum_initWithNSString_withInt_(self, __name, __ordinal);
}

EventFetchUsersEvent_PropertiesEnum *new_EventFetchUsersEvent_PropertiesEnum_initWithNSString_withInt_(NSString *__name, jint __ordinal) {
  EventFetchUsersEvent_PropertiesEnum *self = [EventFetchUsersEvent_PropertiesEnum alloc];
  EventFetchUsersEvent_PropertiesEnum_initWithNSString_withInt_(self, __name, __ordinal);
  return self;
}

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(EventFetchUsersEvent_PropertiesEnum)

@implementation EventFetchUsersEvent_$1

- (ModelUser *)applyWithId:(JsonJSONObject *)input {
  return new_ModelUser_initWithJsonJSONObject_(input);
}

J2OBJC_IGNORE_DESIGNATED_BEGIN
- (instancetype)init {
  EventFetchUsersEvent_$1_init(self);
  return self;
}
J2OBJC_IGNORE_DESIGNATED_END

+ (const J2ObjcClassInfo *)__metadata {
  static const J2ObjcMethodInfo methods[] = {
    { "applyWithId:", "apply", "Lrtdc.core.model.User;", 0x1, NULL, NULL },
    { "init", "", NULL, 0x0, NULL, NULL },
  };
  static const J2ObjCEnclosingMethodInfo enclosing_method = { "EventFetchUsersEvent", "initWithJsonJSONObject:" };
  static const J2ObjcClassInfo _EventFetchUsersEvent_$1 = { 2, "", "rtdc.core.event", "FetchUsersEvent", 0x8008, 2, methods, 0, NULL, 0, NULL, 0, NULL, &enclosing_method, "Ljava/lang/Object;Lcom/google/common/base/Function<Lrtdc/core/json/JSONObject;Lrtdc/core/model/User;>;" };
  return &_EventFetchUsersEvent_$1;
}

@end

void EventFetchUsersEvent_$1_init(EventFetchUsersEvent_$1 *self) {
  (void) NSObject_init(self);
}

EventFetchUsersEvent_$1 *new_EventFetchUsersEvent_$1_init() {
  EventFetchUsersEvent_$1 *self = [EventFetchUsersEvent_$1 alloc];
  EventFetchUsersEvent_$1_init(self);
  return self;
}

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(EventFetchUsersEvent_$1)
