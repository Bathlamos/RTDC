//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/nicolasmenard/IdeaProjects/RTDC/core/src/main/java/rtdc/core/event/FetchUnitsEvent.java
//

#include "Event.h"
#include "EventType.h"
#include "FetchUnitsEvent.h"
#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "J2ObjC_source.h"
#include "JSONArray.h"
#include "JSONObject.h"
#include "ObjectProperty.h"
#include "RootObject.h"
#include "Unit.h"
#include "com/google/common/base/Function.h"
#include "com/google/common/collect/ImmutableSet.h"
#include "java/lang/Enum.h"
#include "java/lang/IllegalArgumentException.h"
#include "java/lang/Iterable.h"
#include "java/util/ArrayList.h"

@interface EventFetchUnitsEvent () {
 @public
  ComGoogleCommonCollectImmutableSet *units_;
}

@end

J2OBJC_FIELD_SETTER(EventFetchUnitsEvent, units_, ComGoogleCommonCollectImmutableSet *)

@interface EventFetchUnitsEvent_Handler : NSObject

@end

__attribute__((unused)) static void EventFetchUnitsEvent_PropertiesEnum_initWithNSString_withInt_(EventFetchUnitsEvent_PropertiesEnum *self, NSString *__name, jint __ordinal);

__attribute__((unused)) static EventFetchUnitsEvent_PropertiesEnum *new_EventFetchUnitsEvent_PropertiesEnum_initWithNSString_withInt_(NSString *__name, jint __ordinal) NS_RETURNS_RETAINED;

@interface EventFetchUnitsEvent_$1 : NSObject < ComGoogleCommonBaseFunction >

- (ModelUnit *)applyWithId:(JsonJSONObject *)input;

- (instancetype)init;

@end

J2OBJC_EMPTY_STATIC_INIT(EventFetchUnitsEvent_$1)

__attribute__((unused)) static void EventFetchUnitsEvent_$1_init(EventFetchUnitsEvent_$1 *self);

__attribute__((unused)) static EventFetchUnitsEvent_$1 *new_EventFetchUnitsEvent_$1_init() NS_RETURNS_RETAINED;

J2OBJC_TYPE_LITERAL_HEADER(EventFetchUnitsEvent_$1)

J2OBJC_INITIALIZED_DEFN(EventFetchUnitsEvent)

EventEventType *EventFetchUnitsEvent_TYPE_;

@implementation EventFetchUnitsEvent

+ (EventEventType *)TYPE {
  return EventFetchUnitsEvent_TYPE_;
}

- (instancetype)initWithJavaLangIterable:(id<JavaLangIterable>)units {
  EventFetchUnitsEvent_initWithJavaLangIterable_(self, units);
  return self;
}

- (instancetype)initWithJsonJSONObject:(JsonJSONObject *)object {
  EventFetchUnitsEvent_initWithJsonJSONObject_(self, object);
  return self;
}

- (ComGoogleCommonCollectImmutableSet *)getUnits {
  return units_;
}

- (void)fire {
  for (id<EventFetchUnitsEvent_Handler> __strong handler in nil_chk([self getHandlersWithEventEventType:EventFetchUnitsEvent_TYPE_])) [((id<EventFetchUnitsEvent_Handler>) nil_chk(handler)) onUnitsFetchedWithEventFetchUnitsEvent:self];
}

- (IOSObjectArray *)getProperties {
  return EventFetchUnitsEvent_PropertiesEnum_values();
}

- (NSString *)getType {
  return [((EventEventType *) nil_chk(EventFetchUnitsEvent_TYPE_)) getName];
}

- (id)getValueWithModelObjectProperty:(id<ModelObjectProperty>)property {
  switch ([(EventFetchUnitsEvent_PropertiesEnum *) check_class_cast(property, [EventFetchUnitsEvent_PropertiesEnum class]) ordinal]) {
    case EventFetchUnitsEvent_Properties_units:
    return units_;
  }
  return nil;
}

+ (void)initialize {
  if (self == [EventFetchUnitsEvent class]) {
    EventFetchUnitsEvent_TYPE_ = new_EventEventType_initWithNSString_(@"fetchUnitsEvent");
    J2OBJC_SET_INITIALIZED(EventFetchUnitsEvent)
  }
}

+ (const J2ObjcClassInfo *)__metadata {
  static const J2ObjcMethodInfo methods[] = {
    { "initWithJavaLangIterable:", "FetchUnitsEvent", NULL, 0x1, NULL, NULL },
    { "initWithJsonJSONObject:", "FetchUnitsEvent", NULL, 0x1, NULL, NULL },
    { "getUnits", NULL, "Lcom.google.common.collect.ImmutableSet;", 0x1, NULL, NULL },
    { "fire", NULL, "V", 0x1, NULL, NULL },
    { "getProperties", NULL, "[Lrtdc.core.model.ObjectProperty;", 0x1, NULL, NULL },
    { "getType", NULL, "Ljava.lang.String;", 0x1, NULL, NULL },
    { "getValueWithModelObjectProperty:", "getValue", "Ljava.lang.Object;", 0x1, NULL, NULL },
  };
  static const J2ObjcFieldInfo fields[] = {
    { "TYPE_", NULL, 0x19, "Lrtdc.core.event.EventType;", &EventFetchUnitsEvent_TYPE_, "Lrtdc/core/event/EventType<Lrtdc/core/event/FetchUnitsEvent$Handler;>;", .constantValue.asLong = 0 },
    { "units_", NULL, 0x12, "Lcom.google.common.collect.ImmutableSet;", NULL, "Lcom/google/common/collect/ImmutableSet<Lrtdc/core/model/Unit;>;", .constantValue.asLong = 0 },
  };
  static const char *superclass_type_args[] = {"Lrtdc.core.event.FetchUnitsEvent$Handler;"};
  static const char *inner_classes[] = {"Lrtdc.core.event.FetchUnitsEvent$Handler;", "Lrtdc.core.event.FetchUnitsEvent$Properties;"};
  static const J2ObjcClassInfo _EventFetchUnitsEvent = { 2, "FetchUnitsEvent", "rtdc.core.event", NULL, 0x1, 7, methods, 2, fields, 1, superclass_type_args, 2, inner_classes, NULL, "Lrtdc/core/event/Event<Lrtdc/core/event/FetchUnitsEvent$Handler;>;" };
  return &_EventFetchUnitsEvent;
}

@end

void EventFetchUnitsEvent_initWithJavaLangIterable_(EventFetchUnitsEvent *self, id<JavaLangIterable> units) {
  (void) EventEvent_init(self);
  self->units_ = ComGoogleCommonCollectImmutableSet_copyOfWithJavaLangIterable_(units);
}

EventFetchUnitsEvent *new_EventFetchUnitsEvent_initWithJavaLangIterable_(id<JavaLangIterable> units) {
  EventFetchUnitsEvent *self = [EventFetchUnitsEvent alloc];
  EventFetchUnitsEvent_initWithJavaLangIterable_(self, units);
  return self;
}

void EventFetchUnitsEvent_initWithJsonJSONObject_(EventFetchUnitsEvent *self, JsonJSONObject *object) {
  (void) EventEvent_init(self);
  self->units_ = ComGoogleCommonCollectImmutableSet_copyOfWithJavaUtilCollection_([self parseJsonArrayWithJsonJSONArray:[((JsonJSONObject *) nil_chk(object)) getJSONArrayWithNSString:[((EventFetchUnitsEvent_PropertiesEnum *) nil_chk(JreLoadStatic(EventFetchUnitsEvent_PropertiesEnum, units))) name]] withComGoogleCommonBaseFunction:new_EventFetchUnitsEvent_$1_init()]);
}

EventFetchUnitsEvent *new_EventFetchUnitsEvent_initWithJsonJSONObject_(JsonJSONObject *object) {
  EventFetchUnitsEvent *self = [EventFetchUnitsEvent alloc];
  EventFetchUnitsEvent_initWithJsonJSONObject_(self, object);
  return self;
}

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(EventFetchUnitsEvent)

@implementation EventFetchUnitsEvent_Handler

+ (const J2ObjcClassInfo *)__metadata {
  static const J2ObjcMethodInfo methods[] = {
    { "onUnitsFetchedWithEventFetchUnitsEvent:", "onUnitsFetched", "V", 0x401, NULL, NULL },
  };
  static const J2ObjcClassInfo _EventFetchUnitsEvent_Handler = { 2, "Handler", "rtdc.core.event", "FetchUnitsEvent", 0x609, 1, methods, 0, NULL, 0, NULL, 0, NULL, NULL, NULL };
  return &_EventFetchUnitsEvent_Handler;
}

@end

J2OBJC_INTERFACE_TYPE_LITERAL_SOURCE(EventFetchUnitsEvent_Handler)

J2OBJC_INITIALIZED_DEFN(EventFetchUnitsEvent_PropertiesEnum)

EventFetchUnitsEvent_PropertiesEnum *EventFetchUnitsEvent_PropertiesEnum_values_[1];

@implementation EventFetchUnitsEvent_PropertiesEnum

+ (EventFetchUnitsEvent_PropertiesEnum *)units {
  return EventFetchUnitsEvent_PropertiesEnum_units;
}

- (instancetype)initWithNSString:(NSString *)__name
                         withInt:(jint)__ordinal {
  EventFetchUnitsEvent_PropertiesEnum_initWithNSString_withInt_(self, __name, __ordinal);
  return self;
}

IOSObjectArray *EventFetchUnitsEvent_PropertiesEnum_values() {
  EventFetchUnitsEvent_PropertiesEnum_initialize();
  return [IOSObjectArray arrayWithObjects:EventFetchUnitsEvent_PropertiesEnum_values_ count:1 type:EventFetchUnitsEvent_PropertiesEnum_class_()];
}

+ (IOSObjectArray *)values {
  return EventFetchUnitsEvent_PropertiesEnum_values();
}

+ (EventFetchUnitsEvent_PropertiesEnum *)valueOfWithNSString:(NSString *)name {
  return EventFetchUnitsEvent_PropertiesEnum_valueOfWithNSString_(name);
}

EventFetchUnitsEvent_PropertiesEnum *EventFetchUnitsEvent_PropertiesEnum_valueOfWithNSString_(NSString *name) {
  EventFetchUnitsEvent_PropertiesEnum_initialize();
  for (int i = 0; i < 1; i++) {
    EventFetchUnitsEvent_PropertiesEnum *e = EventFetchUnitsEvent_PropertiesEnum_values_[i];
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
  if (self == [EventFetchUnitsEvent_PropertiesEnum class]) {
    EventFetchUnitsEvent_PropertiesEnum_units = new_EventFetchUnitsEvent_PropertiesEnum_initWithNSString_withInt_(@"units", 0);
    J2OBJC_SET_INITIALIZED(EventFetchUnitsEvent_PropertiesEnum)
  }
}

+ (const J2ObjcClassInfo *)__metadata {
  static const J2ObjcFieldInfo fields[] = {
    { "units", "units", 0x4019, "Lrtdc.core.event.FetchUnitsEvent$Properties;", &EventFetchUnitsEvent_PropertiesEnum_units, NULL, .constantValue.asLong = 0 },
  };
  static const char *superclass_type_args[] = {"Lrtdc.core.event.FetchUnitsEvent$Properties;"};
  static const J2ObjcClassInfo _EventFetchUnitsEvent_PropertiesEnum = { 2, "Properties", "rtdc.core.event", "FetchUnitsEvent", 0x4019, 0, NULL, 1, fields, 1, superclass_type_args, 0, NULL, NULL, "Ljava/lang/Enum<Lrtdc/core/event/FetchUnitsEvent$Properties;>;Lrtdc/core/model/ObjectProperty<Lrtdc/core/event/FetchUnitsEvent;>;" };
  return &_EventFetchUnitsEvent_PropertiesEnum;
}

@end

void EventFetchUnitsEvent_PropertiesEnum_initWithNSString_withInt_(EventFetchUnitsEvent_PropertiesEnum *self, NSString *__name, jint __ordinal) {
  (void) JavaLangEnum_initWithNSString_withInt_(self, __name, __ordinal);
}

EventFetchUnitsEvent_PropertiesEnum *new_EventFetchUnitsEvent_PropertiesEnum_initWithNSString_withInt_(NSString *__name, jint __ordinal) {
  EventFetchUnitsEvent_PropertiesEnum *self = [EventFetchUnitsEvent_PropertiesEnum alloc];
  EventFetchUnitsEvent_PropertiesEnum_initWithNSString_withInt_(self, __name, __ordinal);
  return self;
}

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(EventFetchUnitsEvent_PropertiesEnum)

@implementation EventFetchUnitsEvent_$1

- (ModelUnit *)applyWithId:(JsonJSONObject *)input {
  return new_ModelUnit_initWithJsonJSONObject_(input);
}

J2OBJC_IGNORE_DESIGNATED_BEGIN
- (instancetype)init {
  EventFetchUnitsEvent_$1_init(self);
  return self;
}
J2OBJC_IGNORE_DESIGNATED_END

+ (const J2ObjcClassInfo *)__metadata {
  static const J2ObjcMethodInfo methods[] = {
    { "applyWithId:", "apply", "Lrtdc.core.model.Unit;", 0x1, NULL, NULL },
    { "init", "", NULL, 0x0, NULL, NULL },
  };
  static const J2ObjCEnclosingMethodInfo enclosing_method = { "EventFetchUnitsEvent", "initWithJsonJSONObject:" };
  static const J2ObjcClassInfo _EventFetchUnitsEvent_$1 = { 2, "", "rtdc.core.event", "FetchUnitsEvent", 0x8008, 2, methods, 0, NULL, 0, NULL, 0, NULL, &enclosing_method, "Ljava/lang/Object;Lcom/google/common/base/Function<Lrtdc/core/json/JSONObject;Lrtdc/core/model/Unit;>;" };
  return &_EventFetchUnitsEvent_$1;
}

@end

void EventFetchUnitsEvent_$1_init(EventFetchUnitsEvent_$1 *self) {
  (void) NSObject_init(self);
}

EventFetchUnitsEvent_$1 *new_EventFetchUnitsEvent_$1_init() {
  EventFetchUnitsEvent_$1 *self = [EventFetchUnitsEvent_$1 alloc];
  EventFetchUnitsEvent_$1_init(self);
  return self;
}

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(EventFetchUnitsEvent_$1)