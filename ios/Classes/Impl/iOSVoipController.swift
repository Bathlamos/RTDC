//
//  iOSVoipController.swift
//  RTDC
//
//  Created by Nicolas Ménard on 2015-10-08.
//  Copyright © 2015 Clermont, Ermel, Fortin-Boulay, Legault & Ménard. All rights reserved.
//

import Foundation

class iOSVoipController: NSObject, ImplVoipController {
    
    
    func get() -> iOSVoipController {
        return iOSVoipController()
    }
    
    func registerUserWithModelUser(user: ModelUser){
    }
    
    func unregisterCurrentUser(){
    }
    
    func callWithModelUser(user: ModelUser, withBoolean videoEnabled: Bool){
        
    }
    
    func acceptCall(){
    }
    
    func declineCall(){
    }
    
    func sendMessageWithModelMessage(message: ModelMessage){
    }
    
    func setMicMutedWithBoolean(mute: Bool){
    }
    
    func setSpeakerWithBoolean(enabled: Bool){
    }
    
    func setVideoWithBoolean(enabled: Bool){
    }
    
    func setRemoteVideoWithBoolean(enabled: Bool){
    }
    
    func isMicMuted() -> Bool{
        return false
    }
    
    func isSpeakerEnabled() -> Bool {
        return false
    }
    
    func isVideoEnabled() -> Bool{
        return false
    }
    
    func isReceivingRemoteVideo() -> Bool {
        return false
    }
    
    func hangup(){
    }
    
}