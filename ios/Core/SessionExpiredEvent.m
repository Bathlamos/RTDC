//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/nicolasmenard/IdeaProjects/RTDC/core/src/main/java/rtdc/core/event/SessionExpiredEvent.java
//

#include "Event.h"
#include "EventType.h"
#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "J2ObjC_source.h"
#include "ObjectProperty.h"
#include "SessionExpiredEvent.h"
#include "com/google/common/collect/ImmutableSet.h"
#include "java/lang/Enum.h"
#include "java/lang/IllegalArgumentException.h"

__attribute__((unused)) static void EventSessionExpiredEvent_PropertiesEnum_initWithNSString_withInt_(EventSessionExpiredEvent_PropertiesEnum *self, NSString *__name, jint __ordinal);

__attribute__((unused)) static EventSessionExpiredEvent_PropertiesEnum *new_EventSessionExpiredEvent_PropertiesEnum_initWithNSString_withInt_(NSString *__name, jint __ordinal) NS_RETURNS_RETAINED;

@interface EventSessionExpiredEvent_Handler : NSObject

@end

J2OBJC_INITIALIZED_DEFN(EventSessionExpiredEvent)

EventEventType *EventSessionExpiredEvent_TYPE_;

@implementation EventSessionExpiredEvent

+ (EventEventType *)TYPE {
  return EventSessionExpiredEvent_TYPE_;
}

- (void)fire {
  for (id<EventSessionExpiredEvent_Handler> __strong handler in nil_chk([self getHandlersWithEventEventType:EventSessionExpiredEvent_TYPE_])) [((id<EventSessionExpiredEvent_Handler>) nil_chk(handler)) onSessionExpired];
}

- (IOSObjectArray *)getProperties {
  return EventSessionExpiredEvent_PropertiesEnum_values();
}

- (NSString *)getType {
  return [((EventEventType *) nil_chk(EventSessionExpiredEvent_TYPE_)) getName];
}

- (id)getValueWithModelObjectProperty:(id<ModelObjectProperty>)property {
  return nil;
}

J2OBJC_IGNORE_DESIGNATED_BEGIN
- (instancetype)init {
  EventSessionExpiredEvent_init(self);
  return self;
}
J2OBJC_IGNORE_DESIGNATED_END

+ (void)initialize {
  if (self == [EventSessionExpiredEvent class]) {
    EventSessionExpiredEvent_TYPE_ = new_EventEventType_initWithNSString_(@"sessionExpiredEvent");
    J2OBJC_SET_INITIALIZED(EventSessionExpiredEvent)
  }
}

+ (const J2ObjcClassInfo *)__metadata {
  static const J2ObjcMethodInfo methods[] = {
    { "fire", NULL, "V", 0x0, NULL, NULL },
    { "getProperties", NULL, "[Lrtdc.core.model.ObjectProperty;", 0x1, NULL, NULL },
    { "getType", NULL, "Ljava.lang.String;", 0x1, NULL, NULL },
    { "getValueWithModelObjectProperty:", "getValue", "Ljava.lang.Object;", 0x1, NULL, NULL },
    { "init", NULL, NULL, 0x1, NULL, NULL },
  };
  static const J2ObjcFieldInfo fields[] = {
    { "TYPE_", NULL, 0x19, "Lrtdc.core.event.EventType;", &EventSessionExpiredEvent_TYPE_, "Lrtdc/core/event/EventType<Lrtdc/core/event/SessionExpiredEvent$Handler;>;", .constantValue.asLong = 0 },
  };
  static const char *superclass_type_args[] = {"Lrtdc.core.event.SessionExpiredEvent$Handler;"};
  static const char *inner_classes[] = {"Lrtdc.core.event.SessionExpiredEvent$Properties;", "Lrtdc.core.event.SessionExpiredEvent$Handler;"};
  static const J2ObjcClassInfo _EventSessionExpiredEvent = { 2, "SessionExpiredEvent", "rtdc.core.event", NULL, 0x1, 5, methods, 1, fields, 1, superclass_type_args, 2, inner_classes, NULL, "Lrtdc/core/event/Event<Lrtdc/core/event/SessionExpiredEvent$Handler;>;" };
  return &_EventSessionExpiredEvent;
}

@end

void EventSessionExpiredEvent_init(EventSessionExpiredEvent *self) {
  (void) EventEvent_init(self);
}

EventSessionExpiredEvent *new_EventSessionExpiredEvent_init() {
  EventSessionExpiredEvent *self = [EventSessionExpiredEvent alloc];
  EventSessionExpiredEvent_init(self);
  return self;
}

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(EventSessionExpiredEvent)

EventSessionExpiredEvent_PropertiesEnum *EventSessionExpiredEvent_PropertiesEnum_values_[0];

@implementation EventSessionExpiredEvent_PropertiesEnum

- (instancetype)initWithNSString:(NSString *)__name
                         withInt:(jint)__ordinal {
  EventSessionExpiredEvent_PropertiesEnum_initWithNSString_withInt_(self, __name, __ordinal);
  return self;
}

IOSObjectArray *EventSessionExpiredEvent_PropertiesEnum_values() {
  EventSessionExpiredEvent_PropertiesEnum_initialize();
  return [IOSObjectArray arrayWithObjects:EventSessionExpiredEvent_PropertiesEnum_values_ count:0 type:EventSessionExpiredEvent_PropertiesEnum_class_()];
}

+ (IOSObjectArray *)values {
  return EventSessionExpiredEvent_PropertiesEnum_values();
}

+ (EventSessionExpiredEvent_PropertiesEnum *)valueOfWithNSString:(NSString *)name {
  return EventSessionExpiredEvent_PropertiesEnum_valueOfWithNSString_(name);
}

EventSessionExpiredEvent_PropertiesEnum *EventSessionExpiredEvent_PropertiesEnum_valueOfWithNSString_(NSString *name) {
  EventSessionExpiredEvent_PropertiesEnum_initialize();
  for (int i = 0; i < 0; i++) {
    EventSessionExpiredEvent_PropertiesEnum *e = EventSessionExpiredEvent_PropertiesEnum_values_[i];
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

+ (const J2ObjcClassInfo *)__metadata {
  static const char *superclass_type_args[] = {"Lrtdc.core.event.SessionExpiredEvent$Properties;"};
  static const J2ObjcClassInfo _EventSessionExpiredEvent_PropertiesEnum = { 2, "Properties", "rtdc.core.event", "SessionExpiredEvent", 0x4019, 0, NULL, 0, NULL, 1, superclass_type_args, 0, NULL, NULL, "Ljava/lang/Enum<Lrtdc/core/event/SessionExpiredEvent$Properties;>;Lrtdc/core/model/ObjectProperty<Lrtdc/core/event/SessionExpiredEvent;>;" };
  return &_EventSessionExpiredEvent_PropertiesEnum;
}

@end

void EventSessionExpiredEvent_PropertiesEnum_initWithNSString_withInt_(EventSessionExpiredEvent_PropertiesEnum *self, NSString *__name, jint __ordinal) {
  (void) JavaLangEnum_initWithNSString_withInt_(self, __name, __ordinal);
}

EventSessionExpiredEvent_PropertiesEnum *new_EventSessionExpiredEvent_PropertiesEnum_initWithNSString_withInt_(NSString *__name, jint __ordinal) {
  EventSessionExpiredEvent_PropertiesEnum *self = [EventSessionExpiredEvent_PropertiesEnum alloc];
  EventSessionExpiredEvent_PropertiesEnum_initWithNSString_withInt_(self, __name, __ordinal);
  return self;
}

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(EventSessionExpiredEvent_PropertiesEnum)

@implementation EventSessionExpiredEvent_Handler

+ (const J2ObjcClassInfo *)__metadata {
  static const J2ObjcMethodInfo methods[] = {
    { "onSessionExpired", NULL, "V", 0x401, NULL, NULL },
  };
  static const J2ObjcClassInfo _EventSessionExpiredEvent_Handler = { 2, "Handler", "rtdc.core.event", "SessionExpiredEvent", 0x609, 1, methods, 0, NULL, 0, NULL, 0, NULL, NULL, NULL };
  return &_EventSessionExpiredEvent_Handler;
}

@end

J2OBJC_INTERFACE_TYPE_LITERAL_SOURCE(EventSessionExpiredEvent_Handler)
