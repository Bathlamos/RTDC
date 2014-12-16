//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/nio/PipeImpl.java
//

#ifndef _JavaNioPipeImpl_H_
#define _JavaNioPipeImpl_H_

@class IOSObjectArray;
@class JavaIoFileDescriptor;
@class JavaNioByteBuffer;
@class JavaNioChannelsSocketChannel;
@class JavaNioChannelsSpiSelectorProvider;
@class JavaNioPipeImpl_PipeSinkChannel;
@class JavaNioPipeImpl_PipeSourceChannel;

#import "JreEmulation.h"
#include "java/nio/FileDescriptorChannel.h"
#include "java/nio/channels/Pipe.h"

@interface JavaNioPipeImpl : JavaNioChannelsPipe {
}

- (instancetype)initWithJavaNioChannelsSpiSelectorProvider:(JavaNioChannelsSpiSelectorProvider *)selectorProvider;

- (JavaNioChannelsPipe_SinkChannel *)sink;

- (JavaNioChannelsPipe_SourceChannel *)source;

@end

__attribute__((always_inline)) inline void JavaNioPipeImpl_init() {}

@interface JavaNioPipeImpl_PipeSourceChannel : JavaNioChannelsPipe_SourceChannel < JavaNioFileDescriptorChannel > {
}

- (void)implCloseSelectableChannel;

- (void)implConfigureBlockingWithBoolean:(jboolean)blocking;

- (jint)readWithJavaNioByteBuffer:(JavaNioByteBuffer *)buffer;

- (jlong)readWithJavaNioByteBufferArray:(IOSObjectArray *)buffers;

- (jlong)readWithJavaNioByteBufferArray:(IOSObjectArray *)buffers
                                withInt:(jint)offset
                                withInt:(jint)length;

- (JavaIoFileDescriptor *)getFD;

@end

__attribute__((always_inline)) inline void JavaNioPipeImpl_PipeSourceChannel_init() {}

@interface JavaNioPipeImpl_PipeSinkChannel : JavaNioChannelsPipe_SinkChannel < JavaNioFileDescriptorChannel > {
}

- (void)implCloseSelectableChannel;

- (void)implConfigureBlockingWithBoolean:(jboolean)blocking;

- (jint)writeWithJavaNioByteBuffer:(JavaNioByteBuffer *)buffer;

- (jlong)writeWithJavaNioByteBufferArray:(IOSObjectArray *)buffers;

- (jlong)writeWithJavaNioByteBufferArray:(IOSObjectArray *)buffers
                                 withInt:(jint)offset
                                 withInt:(jint)length;

- (JavaIoFileDescriptor *)getFD;

@end

__attribute__((always_inline)) inline void JavaNioPipeImpl_PipeSinkChannel_init() {}

#endif // _JavaNioPipeImpl_H_