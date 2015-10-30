//
//  IOSFactory.swift
//  RTDC
//
//  Created by Nicolas Ménard on 2015-03-02.
//  Copyright © 2015 Clermont, Ermel, Fortin-Boulay, Legault & Ménard. All rights reserved.
//

import Foundation

class IOSFactory: NSObject, ImplFactory {
    
    func newHttpRequestWithNSString(url: String!, withImplHttpRequest_RequestMethodEnum requestMethod: ImplHttpRequest_RequestMethodEnum!) -> ImplHttpRequest{
        return IOSHttpRequest(url: url, requestMethod: requestMethod)
    }
    
    func newDispatcher() -> ImplDispatcher{
        return IOSDispatcher()
    }
    
    func getStorage() -> ImplStorageProtocol {
        return IOSStorage().get()
    }
    
    func getVoipController() -> ImplVoipController{
        return IOSVoipController().get()
    }
    
}