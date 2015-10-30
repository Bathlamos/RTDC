//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/nicolasmenard/IdeaProjects/RTDC/core/src/main/java/rtdc/core/event/EventAggregator.java
//

#ifndef _EventAggregator_H_
#define _EventAggregator_H_

#include "J2ObjC_header.h"

@class ComGoogleCommonCollectImmutableSet;
@class JavaUtilHashSet;

@interface EventEventAggregator : NSObject {
 @public
  JavaUtilHashSet *handlers_;
}

#pragma mark Public

- (instancetype)init;

- (void)addHandlerWithId:(id)object;

- (ComGoogleCommonCollectImmutableSet *)getHandlers;

- (void)removeHandlerWithId:(id)object;

@end

J2OBJC_EMPTY_STATIC_INIT(EventEventAggregator)

J2OBJC_FIELD_SETTER(EventEventAggregator, handlers_, JavaUtilHashSet *)

FOUNDATION_EXPORT void EventEventAggregator_init(EventEventAggregator *self);

FOUNDATION_EXPORT EventEventAggregator *new_EventEventAggregator_init() NS_RETURNS_RETAINED;

J2OBJC_TYPE_LITERAL_HEADER(EventEventAggregator)

@compatibility_alias RtdcCoreEventEventAggregator EventEventAggregator;

#endif // _EventAggregator_H_