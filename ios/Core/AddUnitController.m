//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/nicolasmenard/IdeaProjects/RTDC/core/src/main/java/rtdc/core/controller/AddUnitController.java
//

#include "AddUnitController.h"
#include "AddUnitView.h"
#include "Cache.h"
#include "Controller.h"
#include "J2ObjC_source.h"
#include "Service.h"
#include "Unit.h"
#include "java/lang/Integer.h"
#include "java/lang/NumberFormatException.h"

@interface ControllerAddUnitController () {
 @public
  ModelUnit *currentUnit_;
}

@end

J2OBJC_FIELD_SETTER(ControllerAddUnitController, currentUnit_, ModelUnit *)

@implementation ControllerAddUnitController

- (instancetype)initWithViewAddUnitView:(id<ViewAddUnitView>)view {
  ControllerAddUnitController_initWithViewAddUnitView_(self, view);
  return self;
}

- (NSString *)getTitle {
  return @"Add unit";
}

- (void)addUnit {
  ModelUnit *newUnit = new_ModelUnit_init();
  if (currentUnit_ != nil) [newUnit setIdWithInt:[currentUnit_ getId]];
  [newUnit setNameWithNSString:[((id<ViewAddUnitView>) nil_chk(view_)) getNameAsString]];
  @try {
    [newUnit setTotalBedsWithInt:JavaLangInteger_parseIntWithNSString_([((id<ViewAddUnitView>) view_) getTotalBedsAsString])];
  }
  @catch (JavaLangNumberFormatException *e) {
  }
  ServiceService_updateOrSaveUnitWithModelUnit_(newUnit);
  [((id<ViewAddUnitView>) view_) closeDialog];
}

- (void)deleteUnit {
  if (currentUnit_ != nil) ServiceService_deleteUnitWithInt_([currentUnit_ getId]);
  [((id<ViewAddUnitView>) nil_chk(view_)) closeDialog];
}

+ (const J2ObjcClassInfo *)__metadata {
  static const J2ObjcMethodInfo methods[] = {
    { "initWithViewAddUnitView:", "AddUnitController", NULL, 0x1, NULL, NULL },
    { "getTitle", NULL, "Ljava.lang.String;", 0x0, NULL, NULL },
    { "addUnit", NULL, "V", 0x1, NULL, NULL },
    { "deleteUnit", NULL, "V", 0x1, NULL, NULL },
  };
  static const J2ObjcFieldInfo fields[] = {
    { "currentUnit_", NULL, 0x2, "Lrtdc.core.model.Unit;", NULL, NULL, .constantValue.asLong = 0 },
  };
  static const char *superclass_type_args[] = {"Lrtdc.core.view.AddUnitView;"};
  static const J2ObjcClassInfo _ControllerAddUnitController = { 2, "AddUnitController", "rtdc.core.controller", NULL, 0x1, 4, methods, 1, fields, 1, superclass_type_args, 0, NULL, NULL, "Lrtdc/core/controller/Controller<Lrtdc/core/view/AddUnitView;>;" };
  return &_ControllerAddUnitController;
}

@end

void ControllerAddUnitController_initWithViewAddUnitView_(ControllerAddUnitController *self, id<ViewAddUnitView> view) {
  (void) ControllerController_initWithViewView_(self, view);
  self->currentUnit_ = (ModelUnit *) check_class_cast([((UtilCache *) nil_chk(UtilCache_getInstance())) retrieveWithNSString:@"unit"], [ModelUnit class]);
  if (self->currentUnit_ != nil) {
    [((id<ViewAddUnitView>) nil_chk(view)) setTitleWithNSString:@"Edit Unit"];
    [view setNameAsStringWithNSString:[self->currentUnit_ getName]];
    [view setTotalBedsAsStringWithNSString:JavaLangInteger_toStringWithInt_([self->currentUnit_ getTotalBeds])];
  }
  else {
    [((id<ViewAddUnitView>) nil_chk(view)) hideDeleteButton];
  }
}

ControllerAddUnitController *new_ControllerAddUnitController_initWithViewAddUnitView_(id<ViewAddUnitView> view) {
  ControllerAddUnitController *self = [ControllerAddUnitController alloc];
  ControllerAddUnitController_initWithViewAddUnitView_(self, view);
  return self;
}

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(ControllerAddUnitController)
