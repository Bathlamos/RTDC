//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/sql/RowId.java
//

#ifndef _JavaSqlRowId_H_
#define _JavaSqlRowId_H_

@class IOSByteArray;

#import "JreEmulation.h"

@protocol JavaSqlRowId < NSObject, JavaObject >

- (jboolean)isEqual:(id)obj;

- (IOSByteArray *)getBytes;

- (NSString *)description;

- (NSUInteger)hash;

@end

__attribute__((always_inline)) inline void JavaSqlRowId_init() {}

#endif // _JavaSqlRowId_H_