//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/nicolasmenard/IdeaProjects/RTDC/core/src/main/java/rtdc/core/view/AddActionView.java
//

#ifndef _AddActionView_H_
#define _AddActionView_H_

#include "Dialog.h"
#include "J2ObjC_header.h"

@protocol ImplUiDropdown;
@protocol ImplUiElement;

@protocol ViewAddActionView < ViewDialog, NSObject, JavaObject >

- (id<ImplUiDropdown>)getUnitUiElement;

- (id<ImplUiDropdown>)getStatusUiElement;

- (id<ImplUiElement>)getRoleUiElement;

- (id<ImplUiDropdown>)getTaskUiElement;

- (id<ImplUiElement>)getTargetUiElement;

- (id<ImplUiElement>)getDeadlineUiElement;

- (id<ImplUiElement>)getDescriptionUiElement;

@end

J2OBJC_EMPTY_STATIC_INIT(ViewAddActionView)

J2OBJC_TYPE_LITERAL_HEADER(ViewAddActionView)

#define RtdcCoreViewAddActionView ViewAddActionView

#endif // _AddActionView_H_
