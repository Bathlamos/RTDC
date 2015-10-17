//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/nicolasmenard/IdeaProjects/RTDC/core/src/main/java/rtdc/core/controller/Controller.java
//

#include "Controller.h"
#include "ErrorEvent.h"
#include "Event.h"
#include "EventType.h"
#include "IOSClass.h"
#include "J2ObjC_source.h"
#include "View.h"
#include "java/util/logging/Level.h"
#include "java/util/logging/Logger.h"

@implementation ControllerController

- (instancetype)initWithViewView:(id<ViewView>)view {
  ControllerController_initWithViewView_(self, view);
  return self;
}

- (void)onErrorWithEventErrorEvent:(EventErrorEvent *)event {
  [((JavaUtilLoggingLogger *) nil_chk(JavaUtilLoggingLogger_getLoggerWithNSString_([ControllerController_class_() getName]))) logWithJavaUtilLoggingLevel:JreLoadStatic(JavaUtilLoggingLevel, WARNING_) withNSString:[((EventErrorEvent *) nil_chk(event)) description]];
  [((id<ViewView>) nil_chk(view_)) displayErrorWithNSString:@"Error" withNSString:[event getDescription]];
}

- (NSString *)getTitle {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (id)getView {
  return view_;
}

- (void)onStop {
  EventEvent_unsubscribeWithEventEventType_withEventEventHandler_(JreLoadStatic(EventErrorEvent, TYPE_), self);
}

+ (const J2ObjcClassInfo *)__metadata {
  static const J2ObjcMethodInfo methods[] = {
    { "initWithViewView:", "Controller", NULL, 0x1, NULL, "(TT;)V" },
    { "onErrorWithEventErrorEvent:", "onError", "V", 0x1, NULL, NULL },
    { "getTitle", NULL, "Ljava.lang.String;", 0x400, NULL, NULL },
    { "getView", NULL, "TT;", 0x1, NULL, "()TT;" },
    { "onStop", NULL, "V", 0x1, NULL, NULL },
  };
  static const J2ObjcFieldInfo fields[] = {
    { "view_", NULL, 0x14, "TT;", NULL, "TT;", .constantValue.asLong = 0 },
  };
  static const J2ObjcClassInfo _ControllerController = { 2, "Controller", "rtdc.core.controller", NULL, 0x401, 5, methods, 1, fields, 0, NULL, 0, NULL, NULL, "<T::Lrtdc/core/view/View;>Ljava/lang/Object;Lrtdc/core/event/ErrorEvent$Handler;" };
  return &_ControllerController;
}

@end

void ControllerController_initWithViewView_(ControllerController *self, id<ViewView> view) {
  (void) NSObject_init(self);
  EventEvent_subscribeWithEventEventType_withEventEventHandler_(JreLoadStatic(EventErrorEvent, TYPE_), self);
  self->view_ = view;
  [((id<ViewView>) nil_chk(view)) setTitleWithNSString:[self getTitle]];
}

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(ControllerController)
