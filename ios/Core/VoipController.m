//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/nicolasmenard/IdeaProjects/RTDC/core/src/main/java/rtdc/core/impl/VoipController.java
//

#include "J2ObjC_source.h"
#include "Message.h"
#include "User.h"
#include "VoipController.h"

@interface ImplVoipController : NSObject

@end

@implementation ImplVoipController

+ (const J2ObjcClassInfo *)__metadata {
  static const J2ObjcMethodInfo methods[] = {
    { "registerUserWithModelUser:", "registerUser", "V", 0x401, NULL, NULL },
    { "unregisterCurrentUser", NULL, "V", 0x401, NULL, NULL },
    { "callWithModelUser:withBoolean:", "call", "V", 0x401, NULL, NULL },
    { "acceptCall", NULL, "V", 0x401, NULL, NULL },
    { "declineCall", NULL, "V", 0x401, NULL, NULL },
    { "sendMessageWithModelMessage:", "sendMessage", "V", 0x401, NULL, NULL },
    { "setMicMutedWithBoolean:", "setMicMuted", "V", 0x401, NULL, NULL },
    { "setSpeakerWithBoolean:", "setSpeaker", "V", 0x401, NULL, NULL },
    { "setVideoWithBoolean:", "setVideo", "V", 0x401, NULL, NULL },
    { "setRemoteVideoWithBoolean:", "setRemoteVideo", "V", 0x401, NULL, NULL },
    { "isMicMuted", NULL, "Z", 0x401, NULL, NULL },
    { "isSpeakerEnabled", NULL, "Z", 0x401, NULL, NULL },
    { "isVideoEnabled", NULL, "Z", 0x401, NULL, NULL },
    { "isReceivingRemoteVideo", NULL, "Z", 0x401, NULL, NULL },
    { "hangup", NULL, "V", 0x401, NULL, NULL },
  };
  static const J2ObjcClassInfo _ImplVoipController = { 2, "VoipController", "rtdc.core.impl", NULL, 0x609, 15, methods, 0, NULL, 0, NULL, 0, NULL, NULL, NULL };
  return &_ImplVoipController;
}

@end

J2OBJC_INTERFACE_TYPE_LITERAL_SOURCE(ImplVoipController)