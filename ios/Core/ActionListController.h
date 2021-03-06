//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/nicolasmenard/IdeaProjects/RTDC/core/src/main/java/rtdc/core/controller/ActionListController.java
//

#ifndef _ActionListController_H_
#define _ActionListController_H_

#include "Controller.h"
#include "FetchActionsEvent.h"
#include "J2ObjC_header.h"

@class ModelAction;
@class ModelAction_PropertiesEnum;
@protocol ViewActionListView;

@interface ControllerActionListController : ControllerController < EventFetchActionsEvent_Handler >

#pragma mark Public

- (instancetype)initWithViewActionListView:(id<ViewActionListView>)view;

- (void)deleteActionWithModelAction:(ModelAction *)action;

- (void)editActionWithModelAction:(ModelAction *)action;

- (void)onActionsFetchedWithEventFetchActionsEvent:(EventFetchActionsEvent *)event;

- (void)onStop;

- (void)saveActionWithModelAction:(ModelAction *)action;

- (void)sortActionsWithModelAction_PropertiesEnum:(ModelAction_PropertiesEnum *)property;

#pragma mark Package-Private

- (NSString *)getTitle;

@end

J2OBJC_EMPTY_STATIC_INIT(ControllerActionListController)

FOUNDATION_EXPORT void ControllerActionListController_initWithViewActionListView_(ControllerActionListController *self, id<ViewActionListView> view);

FOUNDATION_EXPORT ControllerActionListController *new_ControllerActionListController_initWithViewActionListView_(id<ViewActionListView> view) NS_RETURNS_RETAINED;

J2OBJC_TYPE_LITERAL_HEADER(ControllerActionListController)

@compatibility_alias RtdcCoreControllerActionListController ControllerActionListController;

#endif // _ActionListController_H_
