//
//  iOSFactory.swift
//  RTDC
//
//  Created by Nicolas Ménard on 2015-03-02.
//  Copyright © 2015 Clermont, Ermel, Fortin-Boulay, Legault & Ménard. All rights reserved.
//

import Foundation

class iOSFactory: NSObject, ImplFactory {
    
    func newHttpRequestWithNSString(url: String!, withImplHttpRequest_RequestMethodEnum requestMethod: ImplHttpRequest_RequestMethodEnum!) -> ImplHttpRequest{
        return iOSHttpRequest(url: url, requestMethod: requestMethod)
    }
    
    func newDispatcher() -> ImplDispatcher{
        return iOSDispatcher()
    }
    
    func getStorage() -> ImplStorageProtocol {
        return iOSStorage().get()
    }
    
    func getVoipController() -> ImplVoipController{
        return iOSVoipController().get()
    }
    
}