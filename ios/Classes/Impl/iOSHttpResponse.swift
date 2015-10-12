//
//  iOSHttpResponse.swift
//  RTDC
//
//  Created by Nicolas Ménard on 2015-03-03.
//  Copyright © 2015 Clermont, Ermel, Fortin-Boulay, Legault & Ménard. All rights reserved.
//

import Foundation

class iOSHttpResponse: NSObject, ImplHttpResponse {

    private var statusCode: jint
    private var content: String!
    
    init(statusCode: jint, content: String!){
        self.statusCode = statusCode
        self.content = content
    }
    
    func getStatusCode() -> jint {
        return statusCode
    }
    
    func getContent() -> String! {
        return content
    }
    
}