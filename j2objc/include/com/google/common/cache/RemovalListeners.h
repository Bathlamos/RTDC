//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/kstanger/j2objc-0.9.5/j2objc/guava/sources/com/google/common/cache/RemovalListeners.java
//

#import "JreEmulation.h"

#if !ComGoogleCommonCacheRemovalListeners_RESTRICT
#define ComGoogleCommonCacheRemovalListeners_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCacheRemovalListeners_RESTRICT
#if !defined (_ComGoogleCommonCacheRemovalListeners_) && (ComGoogleCommonCacheRemovalListeners_INCLUDE_ALL || ComGoogleCommonCacheRemovalListeners_INCLUDE)
#define _ComGoogleCommonCacheRemovalListeners_

@protocol ComGoogleCommonCacheRemovalListener;
@protocol JavaUtilConcurrentExecutor;


@interface ComGoogleCommonCacheRemovalListeners : NSObject {
}

+ (id<ComGoogleCommonCacheRemovalListener>)asynchronousWithComGoogleCommonCacheRemovalListener:(id<ComGoogleCommonCacheRemovalListener>)listener
                                                                withJavaUtilConcurrentExecutor:(id<JavaUtilConcurrentExecutor>)executor;

@end

__attribute__((always_inline)) inline void ComGoogleCommonCacheRemovalListeners_init() {}
FOUNDATION_EXPORT id<ComGoogleCommonCacheRemovalListener> ComGoogleCommonCacheRemovalListeners_asynchronousWithComGoogleCommonCacheRemovalListener_withJavaUtilConcurrentExecutor_(id<ComGoogleCommonCacheRemovalListener> listener, id<JavaUtilConcurrentExecutor> executor);
#endif
#if !defined (_ComGoogleCommonCacheRemovalListeners_$1_) && (ComGoogleCommonCacheRemovalListeners_INCLUDE_ALL || ComGoogleCommonCacheRemovalListeners_$1_INCLUDE)
#define _ComGoogleCommonCacheRemovalListeners_$1_

@class ComGoogleCommonCacheRemovalNotification;
@protocol JavaUtilConcurrentExecutor;

#define ComGoogleCommonCacheRemovalListener_RESTRICT 1
#define ComGoogleCommonCacheRemovalListener_INCLUDE 1
#include "com/google/common/cache/RemovalListener.h"


@interface ComGoogleCommonCacheRemovalListeners_$1 : NSObject < ComGoogleCommonCacheRemovalListener > {
}

- (void)onRemovalWithComGoogleCommonCacheRemovalNotification:(ComGoogleCommonCacheRemovalNotification *)notification;

- (instancetype)initWithJavaUtilConcurrentExecutor:(id<JavaUtilConcurrentExecutor>)capture$0
           withComGoogleCommonCacheRemovalListener:(id<ComGoogleCommonCacheRemovalListener>)capture$1;

@end

__attribute__((always_inline)) inline void ComGoogleCommonCacheRemovalListeners_$1_init() {}
#endif
#if !defined (_ComGoogleCommonCacheRemovalListeners_$1_$1_) && (ComGoogleCommonCacheRemovalListeners_INCLUDE_ALL || ComGoogleCommonCacheRemovalListeners_$1_$1_INCLUDE)
#define _ComGoogleCommonCacheRemovalListeners_$1_$1_

@class ComGoogleCommonCacheRemovalListeners_$1;
@class ComGoogleCommonCacheRemovalNotification;

#define JavaLangRunnable_RESTRICT 1
#define JavaLangRunnable_INCLUDE 1
#include "java/lang/Runnable.h"


@interface ComGoogleCommonCacheRemovalListeners_$1_$1 : NSObject < JavaLangRunnable > {
}

- (void)run;

- (instancetype)initWithComGoogleCommonCacheRemovalListeners_$1:(ComGoogleCommonCacheRemovalListeners_$1 *)outer$
                    withComGoogleCommonCacheRemovalNotification:(ComGoogleCommonCacheRemovalNotification *)capture$0;

@end

__attribute__((always_inline)) inline void ComGoogleCommonCacheRemovalListeners_$1_$1_init() {}
#endif