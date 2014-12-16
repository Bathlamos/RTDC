//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/kstanger/j2objc-0.9.5/j2objc/guava/sources/com/google/common/collect/ImmutableTable.java
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectImmutableTable_RESTRICT
#define ComGoogleCommonCollectImmutableTable_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectImmutableTable_RESTRICT
#if !defined (_ComGoogleCommonCollectImmutableTable_) && (ComGoogleCommonCollectImmutableTable_INCLUDE_ALL || ComGoogleCommonCollectImmutableTable_INCLUDE)
#define _ComGoogleCommonCollectImmutableTable_

@class ComGoogleCommonCollectImmutableMap;
@class ComGoogleCommonCollectImmutableSet;
@class ComGoogleCommonCollectImmutableTable_Builder;
@protocol ComGoogleCommonCollectTable_Cell;

#define ComGoogleCommonCollectTable_RESTRICT 1
#define ComGoogleCommonCollectTable_INCLUDE 1
#include "com/google/common/collect/Table.h"


@interface ComGoogleCommonCollectImmutableTable : NSObject < ComGoogleCommonCollectTable > {
}

+ (ComGoogleCommonCollectImmutableTable *)of;

+ (ComGoogleCommonCollectImmutableTable *)ofWithId:(id)rowKey
                                            withId:(id)columnKey
                                            withId:(id)value;

+ (ComGoogleCommonCollectImmutableTable *)copyOfWithComGoogleCommonCollectTable:(id<ComGoogleCommonCollectTable>)table OBJC_METHOD_FAMILY_NONE;

+ (ComGoogleCommonCollectImmutableTable_Builder *)builder;

+ (id<ComGoogleCommonCollectTable_Cell>)cellOfWithId:(id)rowKey
                                              withId:(id)columnKey
                                              withId:(id)value;

- (instancetype)init;

- (ComGoogleCommonCollectImmutableSet *)cellSet;

- (ComGoogleCommonCollectImmutableMap *)columnWithId:(id)columnKey;

- (ComGoogleCommonCollectImmutableSet *)columnKeySet;

- (ComGoogleCommonCollectImmutableMap *)columnMap;

- (ComGoogleCommonCollectImmutableMap *)rowWithId:(id)rowKey;

- (ComGoogleCommonCollectImmutableSet *)rowKeySet;

- (ComGoogleCommonCollectImmutableMap *)rowMap;

- (void)clear;

- (id)putWithId:(id)rowKey
         withId:(id)columnKey
         withId:(id)value;

- (void)putAllWithComGoogleCommonCollectTable:(id<ComGoogleCommonCollectTable>)table;

- (id)removeWithId:(id)rowKey
            withId:(id)columnKey;

- (jboolean)isEqual:(id)obj;

- (NSUInteger)hash;

- (NSString *)description;

@end

__attribute__((always_inline)) inline void ComGoogleCommonCollectImmutableTable_init() {}
FOUNDATION_EXPORT ComGoogleCommonCollectImmutableTable *ComGoogleCommonCollectImmutableTable_of();
FOUNDATION_EXPORT ComGoogleCommonCollectImmutableTable *ComGoogleCommonCollectImmutableTable_ofWithId_withId_withId_(id rowKey, id columnKey, id value);
FOUNDATION_EXPORT ComGoogleCommonCollectImmutableTable *ComGoogleCommonCollectImmutableTable_copyOfWithComGoogleCommonCollectTable_(id<ComGoogleCommonCollectTable> table);
FOUNDATION_EXPORT ComGoogleCommonCollectImmutableTable_Builder *ComGoogleCommonCollectImmutableTable_builder();
FOUNDATION_EXPORT id<ComGoogleCommonCollectTable_Cell> ComGoogleCommonCollectImmutableTable_cellOfWithId_withId_withId_(id rowKey, id columnKey, id value);
#endif
#if !defined (_ComGoogleCommonCollectImmutableTable_Builder_) && (ComGoogleCommonCollectImmutableTable_INCLUDE_ALL || ComGoogleCommonCollectImmutableTable_Builder_INCLUDE)
#define _ComGoogleCommonCollectImmutableTable_Builder_

@class ComGoogleCommonCollectImmutableTable;
@protocol ComGoogleCommonCollectTable;
@protocol ComGoogleCommonCollectTable_Cell;
@protocol JavaUtilComparator;
@protocol JavaUtilList;


@interface ComGoogleCommonCollectImmutableTable_Builder : NSObject {
}

- (instancetype)init;

- (ComGoogleCommonCollectImmutableTable_Builder *)orderRowsByWithJavaUtilComparator:(id<JavaUtilComparator>)rowComparator;

- (ComGoogleCommonCollectImmutableTable_Builder *)orderColumnsByWithJavaUtilComparator:(id<JavaUtilComparator>)columnComparator;

- (ComGoogleCommonCollectImmutableTable_Builder *)putWithId:(id)rowKey
                                                     withId:(id)columnKey
                                                     withId:(id)value;

- (ComGoogleCommonCollectImmutableTable_Builder *)putWithComGoogleCommonCollectTable_Cell:(id<ComGoogleCommonCollectTable_Cell>)cell;

- (ComGoogleCommonCollectImmutableTable_Builder *)putAllWithComGoogleCommonCollectTable:(id<ComGoogleCommonCollectTable>)table;

- (ComGoogleCommonCollectImmutableTable *)build;

@end

__attribute__((always_inline)) inline void ComGoogleCommonCollectImmutableTable_Builder_init() {}
#endif