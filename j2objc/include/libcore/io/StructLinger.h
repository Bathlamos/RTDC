//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/libcore/io/StructLinger.java
//

#ifndef _LibcoreIoStructLinger_H_
#define _LibcoreIoStructLinger_H_

#import "JreEmulation.h"

@interface LibcoreIoStructLinger : NSObject {
 @public
  jint l_onoff_;
  jint l_linger_;
}

- (instancetype)initWithInt:(jint)l_onoff
                    withInt:(jint)l_linger;

- (jboolean)isOn;

- (NSString *)description;

@end

__attribute__((always_inline)) inline void LibcoreIoStructLinger_init() {}

#endif // _LibcoreIoStructLinger_H_