//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/nicolasmenard/IdeaProjects/RTDC/core/src/main/java/rtdc/core/controller/CapacityOverviewController.java
//

#include "Bootstrapper.h"
#include "Cache.h"
#include "CapacityOverviewController.h"
#include "CapacityOverviewView.h"
#include "Controller.h"
#include "Dispatcher.h"
#include "Event.h"
#include "EventType.h"
#include "Factory.h"
#include "FetchUnitsEvent.h"
#include "IOSClass.h"
#include "J2ObjC_source.h"
#include "Service.h"
#include "SimpleComparator.h"
#include "Unit.h"
#include "com/google/common/collect/ImmutableSet.h"
#include "java/util/ArrayList.h"
#include "java/util/Collections.h"
#include "java/util/Comparator.h"
#include "java/util/logging/Level.h"
#include "java/util/logging/Logger.h"

@interface ControllerCapacityOverviewController () {
 @public
  JavaUtilArrayList *units_;
}

@end

J2OBJC_FIELD_SETTER(ControllerCapacityOverviewController, units_, JavaUtilArrayList *)

static JavaUtilLoggingLogger *ControllerCapacityOverviewController_logger_;
J2OBJC_STATIC_FIELD_GETTER(ControllerCapacityOverviewController, logger_, JavaUtilLoggingLogger *)

J2OBJC_INITIALIZED_DEFN(ControllerCapacityOverviewController)

@implementation ControllerCapacityOverviewController

- (instancetype)initWithViewCapacityOverviewView:(id<ViewCapacityOverviewView>)view {
  ControllerCapacityOverviewController_initWithViewCapacityOverviewView_(self, view);
  return self;
}

- (NSString *)getTitle {
  return @"Capacity Overview";
}

- (void)editCapacityWithModelUnit:(ModelUnit *)unit {
  [((UtilCache *) nil_chk(UtilCache_getInstance())) putWithNSString:@"unit" withId:unit];
  [((id<ImplDispatcher>) nil_chk([((id<ImplFactory>) nil_chk(JreLoadStatic(RtdcCoreBootstrapper, FACTORY_))) newDispatcher])) goToEditCapacityWithControllerController:self];
}

- (void)sortUnitsWithModelUnit_PropertiesEnum:(ModelUnit_PropertiesEnum *)property
                                  withBoolean:(jboolean)ascending {
  [((JavaUtilLoggingLogger *) nil_chk(ControllerCapacityOverviewController_logger_)) logWithJavaUtilLoggingLevel:JreLoadStatic(JavaUtilLoggingLevel, INFO_) withNSString:JreStrcat("$$", @"Sorting over ", [((ModelUnit_PropertiesEnum *) nil_chk(property)) name])];
  JavaUtilArrayList *sortedUnits = new_JavaUtilArrayList_initWithJavaUtilCollection_(units_);
  JavaUtilCollections_sortWithJavaUtilList_withJavaUtilComparator_(sortedUnits, [((ModelSimpleComparator_Builder *) nil_chk([((ModelSimpleComparator_Builder *) nil_chk(ModelSimpleComparator_forPropertyWithModelObjectProperty_(property))) setAscendingWithBoolean:ascending])) build]);
  [((id<ViewCapacityOverviewView>) nil_chk(view_)) setUnitsWithJavaUtilList:sortedUnits];
}

- (void)onUnitsFetchedWithEventFetchUnitsEvent:(EventFetchUnitsEvent *)event {
  units_ = new_JavaUtilArrayList_initWithJavaUtilCollection_([((EventFetchUnitsEvent *) nil_chk(event)) getUnits]);
  [self sortUnitsWithModelUnit_PropertiesEnum:JreLoadStatic(ModelUnit_PropertiesEnum, name) withBoolean:true];
}

- (void)onStop {
  [super onStop];
  EventEvent_unsubscribeWithEventEventType_withEventEventHandler_(JreLoadStatic(EventFetchUnitsEvent, TYPE_), self);
}

+ (void)initialize {
  if (self == [ControllerCapacityOverviewController class]) {
    ControllerCapacityOverviewController_logger_ = JavaUtilLoggingLogger_getLoggerWithNSString_([ControllerCapacityOverviewController_class_() getCanonicalName]);
    J2OBJC_SET_INITIALIZED(ControllerCapacityOverviewController)
  }
}

+ (const J2ObjcClassInfo *)__metadata {
  static const J2ObjcMethodInfo methods[] = {
    { "initWithViewCapacityOverviewView:", "CapacityOverviewController", NULL, 0x1, NULL, NULL },
    { "getTitle", NULL, "Ljava.lang.String;", 0x0, NULL, NULL },
    { "editCapacityWithModelUnit:", "editCapacity", "V", 0x1, NULL, NULL },
    { "sortUnitsWithModelUnit_PropertiesEnum:withBoolean:", "sortUnits", "V", 0x1, NULL, NULL },
    { "onUnitsFetchedWithEventFetchUnitsEvent:", "onUnitsFetched", "V", 0x1, NULL, NULL },
    { "onStop", NULL, "V", 0x1, NULL, NULL },
  };
  static const J2ObjcFieldInfo fields[] = {
    { "logger_", NULL, 0x1a, "Ljava.util.logging.Logger;", &ControllerCapacityOverviewController_logger_, NULL, .constantValue.asLong = 0 },
    { "units_", NULL, 0x2, "Ljava.util.ArrayList;", NULL, "Ljava/util/ArrayList<Lrtdc/core/model/Unit;>;", .constantValue.asLong = 0 },
  };
  static const char *superclass_type_args[] = {"Lrtdc.core.view.CapacityOverviewView;"};
  static const J2ObjcClassInfo _ControllerCapacityOverviewController = { 2, "CapacityOverviewController", "rtdc.core.controller", NULL, 0x1, 6, methods, 2, fields, 1, superclass_type_args, 0, NULL, NULL, "Lrtdc/core/controller/Controller<Lrtdc/core/view/CapacityOverviewView;>;Lrtdc/core/event/FetchUnitsEvent$Handler;" };
  return &_ControllerCapacityOverviewController;
}

@end

void ControllerCapacityOverviewController_initWithViewCapacityOverviewView_(ControllerCapacityOverviewController *self, id<ViewCapacityOverviewView> view) {
  (void) ControllerController_initWithViewView_(self, view);
  EventEvent_subscribeWithEventEventType_withEventEventHandler_(JreLoadStatic(EventFetchUnitsEvent, TYPE_), self);
  ServiceService_getUnits();
}

ControllerCapacityOverviewController *new_ControllerCapacityOverviewController_initWithViewCapacityOverviewView_(id<ViewCapacityOverviewView> view) {
  ControllerCapacityOverviewController *self = [ControllerCapacityOverviewController alloc];
  ControllerCapacityOverviewController_initWithViewCapacityOverviewView_(self, view);
  return self;
}

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(ControllerCapacityOverviewController)
