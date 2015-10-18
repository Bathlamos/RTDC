//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/nicolasmenard/IdeaProjects/RTDC/core/src/main/java/rtdc/core/model/Unit.java
//

#ifndef _Unit_H_
#define _Unit_H_

#include "J2ObjC_header.h"
#include "ObjectProperty.h"
#include "RootObject.h"
#include "java/lang/Enum.h"

@class IOSObjectArray;
@class JSONJSONObject;
@class ModelUnit_PropertiesEnum;
@protocol ModelObjectProperty;

@interface ModelUnit : ModelRootObject

#pragma mark Public

- (instancetype)init;

- (instancetype)initWithJSONJSONObject:(JSONJSONObject *)object;

- (jint)getAdmitsByDeadline;

- (jint)getAvailableBeds;

- (jint)getDcByDeadline;

- (jint)getId;

- (NSString *)getName;

- (jint)getPotentialDc;

- (IOSObjectArray *)getProperties;

- (jint)getStatusAtDeadline;

- (jint)getTotalAdmits;

- (jint)getTotalBeds;

- (NSString *)getType;

- (id)getValueWithModelObjectProperty:(id<ModelObjectProperty>)property;

- (void)setAdmitsByDeadlineWithInt:(jint)admitsByDeadline;

- (void)setAvailableBedsWithInt:(jint)availableBeds;

- (void)setDcByDeadlineWithInt:(jint)dcByDeadline;

- (void)setIdWithInt:(jint)id_;

- (void)setNameWithNSString:(NSString *)name;

- (void)setPotentialDcWithInt:(jint)potentialDc;

- (void)setTotalAdmitsWithInt:(jint)totalAdmits;

- (void)setTotalBedsWithInt:(jint)totalBeds;

- (jboolean)validateWithModelUnit_PropertiesEnum:(ModelUnit_PropertiesEnum *)property;

@end

J2OBJC_EMPTY_STATIC_INIT(ModelUnit)

FOUNDATION_EXPORT void ModelUnit_init(ModelUnit *self);

FOUNDATION_EXPORT ModelUnit *new_ModelUnit_init() NS_RETURNS_RETAINED;

FOUNDATION_EXPORT void ModelUnit_initWithJSONJSONObject_(ModelUnit *self, JSONJSONObject *object);

FOUNDATION_EXPORT ModelUnit *new_ModelUnit_initWithJSONJSONObject_(JSONJSONObject *object) NS_RETURNS_RETAINED;

J2OBJC_TYPE_LITERAL_HEADER(ModelUnit)

@compatibility_alias RtdcCoreModelUnit ModelUnit;

typedef NS_ENUM(NSUInteger, ModelUnit_Properties) {
  ModelUnit_Properties_id = 0,
  ModelUnit_Properties_name = 1,
  ModelUnit_Properties_totalBeds = 2,
  ModelUnit_Properties_availableBeds = 3,
  ModelUnit_Properties_potentialDc = 4,
  ModelUnit_Properties_dcByDeadline = 5,
  ModelUnit_Properties_totalAdmits = 6,
  ModelUnit_Properties_admitsByDeadline = 7,
  ModelUnit_Properties_statusAtDeadline = 8,
};

@interface ModelUnit_PropertiesEnum : JavaLangEnum < NSCopying, ModelObjectProperty >

#pragma mark Package-Private

+ (IOSObjectArray *)values;
FOUNDATION_EXPORT IOSObjectArray *ModelUnit_PropertiesEnum_values();

+ (ModelUnit_PropertiesEnum *)valueOfWithNSString:(NSString *)name;
FOUNDATION_EXPORT ModelUnit_PropertiesEnum *ModelUnit_PropertiesEnum_valueOfWithNSString_(NSString *name);

- (id)copyWithZone:(NSZone *)zone;

@end

J2OBJC_STATIC_INIT(ModelUnit_PropertiesEnum)

FOUNDATION_EXPORT ModelUnit_PropertiesEnum *ModelUnit_PropertiesEnum_values_[];

#define ModelUnit_PropertiesEnum_id ModelUnit_PropertiesEnum_values_[ModelUnit_Properties_id]
J2OBJC_ENUM_CONSTANT_GETTER(ModelUnit_PropertiesEnum, id)

#define ModelUnit_PropertiesEnum_name ModelUnit_PropertiesEnum_values_[ModelUnit_Properties_name]
J2OBJC_ENUM_CONSTANT_GETTER(ModelUnit_PropertiesEnum, name)

#define ModelUnit_PropertiesEnum_totalBeds ModelUnit_PropertiesEnum_values_[ModelUnit_Properties_totalBeds]
J2OBJC_ENUM_CONSTANT_GETTER(ModelUnit_PropertiesEnum, totalBeds)

#define ModelUnit_PropertiesEnum_availableBeds ModelUnit_PropertiesEnum_values_[ModelUnit_Properties_availableBeds]
J2OBJC_ENUM_CONSTANT_GETTER(ModelUnit_PropertiesEnum, availableBeds)

#define ModelUnit_PropertiesEnum_potentialDc ModelUnit_PropertiesEnum_values_[ModelUnit_Properties_potentialDc]
J2OBJC_ENUM_CONSTANT_GETTER(ModelUnit_PropertiesEnum, potentialDc)

#define ModelUnit_PropertiesEnum_dcByDeadline ModelUnit_PropertiesEnum_values_[ModelUnit_Properties_dcByDeadline]
J2OBJC_ENUM_CONSTANT_GETTER(ModelUnit_PropertiesEnum, dcByDeadline)

#define ModelUnit_PropertiesEnum_totalAdmits ModelUnit_PropertiesEnum_values_[ModelUnit_Properties_totalAdmits]
J2OBJC_ENUM_CONSTANT_GETTER(ModelUnit_PropertiesEnum, totalAdmits)

#define ModelUnit_PropertiesEnum_admitsByDeadline ModelUnit_PropertiesEnum_values_[ModelUnit_Properties_admitsByDeadline]
J2OBJC_ENUM_CONSTANT_GETTER(ModelUnit_PropertiesEnum, admitsByDeadline)

#define ModelUnit_PropertiesEnum_statusAtDeadline ModelUnit_PropertiesEnum_values_[ModelUnit_Properties_statusAtDeadline]
J2OBJC_ENUM_CONSTANT_GETTER(ModelUnit_PropertiesEnum, statusAtDeadline)

J2OBJC_TYPE_LITERAL_HEADER(ModelUnit_PropertiesEnum)

#endif // _Unit_H_
