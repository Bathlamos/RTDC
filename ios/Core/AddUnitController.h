//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/nicolasmenard/IdeaProjects/RTDC/core/src/main/java/rtdc/core/controller/AddUnitController.java
//

#ifndef _AddUnitController_H_
#define _AddUnitController_H_

#include "Controller.h"
#include "J2ObjC_header.h"

@protocol ViewAddUnitView;

@interface ControllerAddUnitController : ControllerController

#pragma mark Public

- (instancetype)initWithViewAddUnitView:(id<ViewAddUnitView>)view;

- (void)addUnit;

- (void)deleteUnit;

#pragma mark Package-Private

- (NSString *)getTitle;

@end

J2OBJC_EMPTY_STATIC_INIT(ControllerAddUnitController)

FOUNDATION_EXPORT void ControllerAddUnitController_initWithViewAddUnitView_(ControllerAddUnitController *self, id<ViewAddUnitView> view);

FOUNDATION_EXPORT ControllerAddUnitController *new_ControllerAddUnitController_initWithViewAddUnitView_(id<ViewAddUnitView> view) NS_RETURNS_RETAINED;

J2OBJC_TYPE_LITERAL_HEADER(ControllerAddUnitController)

@compatibility_alias RtdcCoreControllerAddUnitController ControllerAddUnitController;

#endif // _AddUnitController_H_
