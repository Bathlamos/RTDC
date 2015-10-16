//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/nicolasmenard/IdeaProjects/RTDC/core/src/main/java/rtdc/core/event/FetchActionsEvent.java
//

#include "Action.h"
#include "Event.h"
#include "EventType.h"
#include "FetchActionsEvent.h"
#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "J2ObjC_source.h"
#include "JSONArray.h"
#include "JSONObject.h"
#include "ObjectProperty.h"
#include "RootObject.h"
#include "com/google/common/base/Function.h"
#include "com/google/common/collect/ImmutableSet.h"
#include "java/lang/Enum.h"
#include "java/lang/IllegalArgumentException.h"
#include "java/lang/Iterable.h"
#include "java/util/ArrayList.h"

@interface EventFetchActionsEvent () {
 @public
  ComGoogleCommonCollectImmutableSet *actions_;
}

@end

J2OBJC_FIELD_SETTER(EventFetchActionsEvent, actions_, ComGoogleCommonCollectImmutableSet *)

@interface EventFetchActionsEvent_Handler : NSObject

@end

__attribute__((unused)) static void EventFetchActionsEvent_PropertiesEnum_initWithNSString_withInt_(EventFetchActionsEvent_PropertiesEnum *self, NSString *__name, jint __ordinal);

__attribute__((unused)) static EventFetchActionsEvent_PropertiesEnum *new_EventFetchActionsEvent_PropertiesEnum_initWithNSString_withInt_(NSString *__name, jint __ordinal) NS_RETURNS_RETAINED;

@interface EventFetchActionsEvent_$1 : NSObject < ComGoogleCommonBaseFunction >

- (ModelAction *)applyWithId:(JsonJSONObject *)input;

- (instancetype)init;

@end

J2OBJC_EMPTY_STATIC_INIT(EventFetchActionsEvent_$1)

__attribute__((unused)) static void EventFetchActionsEvent_$1_init(EventFetchActionsEvent_$1 *self);

__attribute__((unused)) static EventFetchActionsEvent_$1 *new_EventFetchActionsEvent_$1_init() NS_RETURNS_RETAINED;

J2OBJC_TYPE_LITERAL_HEADER(EventFetchActionsEvent_$1)

J2OBJC_INITIALIZED_DEFN(EventFetchActionsEvent)

EventEventType *EventFetchActionsEvent_TYPE_;

@implementation EventFetchActionsEvent

+ (EventEventType *)TYPE {
  return EventFetchActionsEvent_TYPE_;
}

- (instancetype)initWithJavaLangIterable:(id<JavaLangIterable>)actions {
  EventFetchActionsEvent_initWithJavaLangIterable_(self, actions);
  return self;
}

- (instancetype)initWithJsonJSONObject:(JsonJSONObject *)object {
  EventFetchActionsEvent_initWithJsonJSONObject_(self, object);
  return self;
}

- (ComGoogleCommonCollectImmutableSet *)getActions {
  return actions_;
}

- (void)fire {
  for (id<EventFetchActionsEvent_Handler> __strong handler in nil_chk([self getHandlersWithEventEventType:EventFetchActionsEvent_TYPE_])) [((id<EventFetchActionsEvent_Handler>) nil_chk(handler)) onActionsFetchedWithEventFetchActionsEvent:self];
}

- (IOSObjectArray *)getProperties {
  return EventFetchActionsEvent_PropertiesEnum_values();
}

- (NSString *)getType {
  return [((EventEventType *) nil_chk(EventFetchActionsEvent_TYPE_)) getName];
}

- (id)getValueWithModelObjectProperty:(id<ModelObjectProperty>)property {
  switch ([(EventFetchActionsEvent_PropertiesEnum *) check_class_cast(property, [EventFetchActionsEvent_PropertiesEnum class]) ordinal]) {
    case EventFetchActionsEvent_Properties_actions:
    return actions_;
  }
  return nil;
}

+ (void)initialize {
  if (self == [EventFetchActionsEvent class]) {
    EventFetchActionsEvent_TYPE_ = new_EventEventType_initWithNSString_(@"fetchActionsEvent");
    J2OBJC_SET_INITIALIZED(EventFetchActionsEvent)
  }
}

+ (const J2ObjcClassInfo *)__metadata {
  static const J2ObjcMethodInfo methods[] = {
    { "initWithJavaLangIterable:", "FetchActionsEvent", NULL, 0x1, NULL, NULL },
    { "initWithJsonJSONObject:", "FetchActionsEvent", NULL, 0x1, NULL, NULL },
    { "getActions", NULL, "Lcom.google.common.collect.ImmutableSet;", 0x1, NULL, NULL },
    { "fire", NULL, "V", 0x1, NULL, NULL },
    { "getProperties", NULL, "[Lrtdc.core.model.ObjectProperty;", 0x1, NULL, NULL },
    { "getType", NULL, "Ljava.lang.String;", 0x1, NULL, NULL },
    { "getValueWithModelObjectProperty:", "getValue", "Ljava.lang.Object;", 0x1, NULL, NULL },
  };
  static const J2ObjcFieldInfo fields[] = {
    { "TYPE_", NULL, 0x19, "Lrtdc.core.event.EventType;", &EventFetchActionsEvent_TYPE_, "Lrtdc/core/event/EventType<Lrtdc/core/event/FetchActionsEvent$Handler;>;", .constantValue.asLong = 0 },
    { "actions_", NULL, 0x12, "Lcom.google.common.collect.ImmutableSet;", NULL, "Lcom/google/common/collect/ImmutableSet<Lrtdc/core/model/Action;>;", .constantValue.asLong = 0 },
  };
  static const char *superclass_type_args[] = {"Lrtdc.core.event.FetchActionsEvent$Handler;"};
  static const char *inner_classes[] = {"Lrtdc.core.event.FetchActionsEvent$Handler;", "Lrtdc.core.event.FetchActionsEvent$Properties;"};
  static const J2ObjcClassInfo _EventFetchActionsEvent = { 2, "FetchActionsEvent", "rtdc.core.event", NULL, 0x1, 7, methods, 2, fields, 1, superclass_type_args, 2, inner_classes, NULL, "Lrtdc/core/event/Event<Lrtdc/core/event/FetchActionsEvent$Handler;>;" };
  return &_EventFetchActionsEvent;
}

@end

void EventFetchActionsEvent_initWithJavaLangIterable_(EventFetchActionsEvent *self, id<JavaLangIterable> actions) {
  (void) EventEvent_init(self);
  self->actions_ = ComGoogleCommonCollectImmutableSet_copyOfWithJavaLangIterable_(actions);
}

EventFetchActionsEvent *new_EventFetchActionsEvent_initWithJavaLangIterable_(id<JavaLangIterable> actions) {
  EventFetchActionsEvent *self = [EventFetchActionsEvent alloc];
  EventFetchActionsEvent_initWithJavaLangIterable_(self, actions);
  return self;
}

void EventFetchActionsEvent_initWithJsonJSONObject_(EventFetchActionsEvent *self, JsonJSONObject *object) {
  (void) EventEvent_init(self);
  self->actions_ = ComGoogleCommonCollectImmutableSet_copyOfWithJavaUtilCollection_([self parseJsonArrayWithJsonJSONArray:[((JsonJSONObject *) nil_chk(object)) getJSONArrayWithNSString:[((EventFetchActionsEvent_PropertiesEnum *) nil_chk(JreLoadStatic(EventFetchActionsEvent_PropertiesEnum, actions))) name]] withComGoogleCommonBaseFunction:new_EventFetchActionsEvent_$1_init()]);
}

EventFetchActionsEvent *new_EventFetchActionsEvent_initWithJsonJSONObject_(JsonJSONObject *object) {
  EventFetchActionsEvent *self = [EventFetchActionsEvent alloc];
  EventFetchActionsEvent_initWithJsonJSONObject_(self, object);
  return self;
}

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(EventFetchActionsEvent)

@implementation EventFetchActionsEvent_Handler

+ (const J2ObjcClassInfo *)__metadata {
  static const J2ObjcMethodInfo methods[] = {
    { "onActionsFetchedWithEventFetchActionsEvent:", "onActionsFetched", "V", 0x401, NULL, NULL },
  };
  static const J2ObjcClassInfo _EventFetchActionsEvent_Handler = { 2, "Handler", "rtdc.core.event", "FetchActionsEvent", 0x609, 1, methods, 0, NULL, 0, NULL, 0, NULL, NULL, NULL };
  return &_EventFetchActionsEvent_Handler;
}

@end

J2OBJC_INTERFACE_TYPE_LITERAL_SOURCE(EventFetchActionsEvent_Handler)

J2OBJC_INITIALIZED_DEFN(EventFetchActionsEvent_PropertiesEnum)

EventFetchActionsEvent_PropertiesEnum *EventFetchActionsEvent_PropertiesEnum_values_[1];

@implementation EventFetchActionsEvent_PropertiesEnum

+ (EventFetchActionsEvent_PropertiesEnum *)actions {
  return EventFetchActionsEvent_PropertiesEnum_actions;
}

- (instancetype)initWithNSString:(NSString *)__name
                         withInt:(jint)__ordinal {
  EventFetchActionsEvent_PropertiesEnum_initWithNSString_withInt_(self, __name, __ordinal);
  return self;
}

IOSObjectArray *EventFetchActionsEvent_PropertiesEnum_values() {
  EventFetchActionsEvent_PropertiesEnum_initialize();
  return [IOSObjectArray arrayWithObjects:EventFetchActionsEvent_PropertiesEnum_values_ count:1 type:EventFetchActionsEvent_PropertiesEnum_class_()];
}

+ (IOSObjectArray *)values {
  return EventFetchActionsEvent_PropertiesEnum_values();
}

+ (EventFetchActionsEvent_PropertiesEnum *)valueOfWithNSString:(NSString *)name {
  return EventFetchActionsEvent_PropertiesEnum_valueOfWithNSString_(name);
}

EventFetchActionsEvent_PropertiesEnum *EventFetchActionsEvent_PropertiesEnum_valueOfWithNSString_(NSString *name) {
  EventFetchActionsEvent_PropertiesEnum_initialize();
  for (int i = 0; i < 1; i++) {
    EventFetchActionsEvent_PropertiesEnum *e = EventFetchActionsEvent_PropertiesEnum_values_[i];
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
  if (self == [EventFetchActionsEvent_PropertiesEnum class]) {
    EventFetchActionsEvent_PropertiesEnum_actions = new_EventFetchActionsEvent_PropertiesEnum_initWithNSString_withInt_(@"actions", 0);
    J2OBJC_SET_INITIALIZED(EventFetchActionsEvent_PropertiesEnum)
  }
}

+ (const J2ObjcClassInfo *)__metadata {
  static const J2ObjcFieldInfo fields[] = {
    { "actions", "actions", 0x4019, "Lrtdc.core.event.FetchActionsEvent$Properties;", &EventFetchActionsEvent_PropertiesEnum_actions, NULL, .constantValue.asLong = 0 },
  };
  static const char *superclass_type_args[] = {"Lrtdc.core.event.FetchActionsEvent$Properties;"};
  static const J2ObjcClassInfo _EventFetchActionsEvent_PropertiesEnum = { 2, "Properties", "rtdc.core.event", "FetchActionsEvent", 0x4019, 0, NULL, 1, fields, 1, superclass_type_args, 0, NULL, NULL, "Ljava/lang/Enum<Lrtdc/core/event/FetchActionsEvent$Properties;>;Lrtdc/core/model/ObjectProperty<Lrtdc/core/event/FetchActionsEvent;>;" };
  return &_EventFetchActionsEvent_PropertiesEnum;
}

@end

void EventFetchActionsEvent_PropertiesEnum_initWithNSString_withInt_(EventFetchActionsEvent_PropertiesEnum *self, NSString *__name, jint __ordinal) {
  (void) JavaLangEnum_initWithNSString_withInt_(self, __name, __ordinal);
}

EventFetchActionsEvent_PropertiesEnum *new_EventFetchActionsEvent_PropertiesEnum_initWithNSString_withInt_(NSString *__name, jint __ordinal) {
  EventFetchActionsEvent_PropertiesEnum *self = [EventFetchActionsEvent_PropertiesEnum alloc];
  EventFetchActionsEvent_PropertiesEnum_initWithNSString_withInt_(self, __name, __ordinal);
  return self;
}

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(EventFetchActionsEvent_PropertiesEnum)

@implementation EventFetchActionsEvent_$1

- (ModelAction *)applyWithId:(JsonJSONObject *)input {
  return new_ModelAction_initWithJsonJSONObject_(input);
}

J2OBJC_IGNORE_DESIGNATED_BEGIN
- (instancetype)init {
  EventFetchActionsEvent_$1_init(self);
  return self;
}
J2OBJC_IGNORE_DESIGNATED_END

+ (const J2ObjcClassInfo *)__metadata {
  static const J2ObjcMethodInfo methods[] = {
    { "applyWithId:", "apply", "Lrtdc.core.model.Action;", 0x1, NULL, NULL },
    { "init", "", NULL, 0x0, NULL, NULL },
  };
  static const J2ObjCEnclosingMethodInfo enclosing_method = { "EventFetchActionsEvent", "initWithJsonJSONObject:" };
  static const J2ObjcClassInfo _EventFetchActionsEvent_$1 = { 2, "", "rtdc.core.event", "FetchActionsEvent", 0x8008, 2, methods, 0, NULL, 0, NULL, 0, NULL, &enclosing_method, "Ljava/lang/Object;Lcom/google/common/base/Function<Lrtdc/core/json/JSONObject;Lrtdc/core/model/Action;>;" };
  return &_EventFetchActionsEvent_$1;
}

@end

void EventFetchActionsEvent_$1_init(EventFetchActionsEvent_$1 *self) {
  (void) NSObject_init(self);
}

EventFetchActionsEvent_$1 *new_EventFetchActionsEvent_$1_init() {
  EventFetchActionsEvent_$1 *self = [EventFetchActionsEvent_$1 alloc];
  EventFetchActionsEvent_$1_init(self);
  return self;
}

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(EventFetchActionsEvent_$1)
