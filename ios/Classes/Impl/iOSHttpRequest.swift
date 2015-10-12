//
//  iOSHttpRequest.swift
//  RTDC
//
//  Created by Nicolas Ménard on 2015-03-02.
//  Copyright © 2015 Clermont, Ermel, Fortin-Boulay, Legault & Ménard. All rights reserved.
//

import Foundation

class iOSHttpRequest: NSObject, ImplHttpRequest {
    
    var request: NSMutableURLRequest
    var urlComponents: NSURLComponents
    
    init(url: String, requestMethod: ImplHttpRequest_RequestMethodEnum){
        urlComponents = NSURLComponents(string: url)!
        request = NSMutableURLRequest()
        switch(requestMethod){
            case ImplHttpRequest_RequestMethodEnum.GET():
                request.HTTPMethod = "GET"
            case ImplHttpRequest_RequestMethodEnum.PUT():
                request.HTTPMethod = "PUT"
            case ImplHttpRequest_RequestMethodEnum.DELETE():
                request.HTTPMethod = "DELETE"
            case ImplHttpRequest_RequestMethodEnum.POST():
                request.HTTPMethod = "POST"
            default: ()
        }

    }
        
    
    func addParameterWithNSString(parameter: String!, withNSString data: String!){
        if urlComponents.query != nil {
            urlComponents.query! += "&\(parameter)=\(data)"
        } else {
            urlComponents.query = "\(parameter)=\(data)"
        }
    }
    
    
    func setHeaderWithNSString(name: String!, withNSString value: String!) {
        request.setValue(value, forHTTPHeaderField: name)
    }
    
    
    func executeWithServiceAsyncCallback(callback: ServiceAsyncCallback!){
        
        request.URL = urlComponents.URL // Setting the request's NSURL
        let session = NSURLSession.sharedSession() // Getting the shared session.
        
        let task = session.dataTaskWithRequest(request, completionHandler: { (data, response, error) -> Void in
            
            if error != nil {
                callback.onErrorWithNSString(error!.localizedDescription)
            } else {
                var statusCode: jint = 200 // Default status code
                
                // Checks if the response is an HTTP Response. If yes, we get the correct status code.
                if let httpResponse = response as? NSHTTPURLResponse {
                    statusCode = Int32(httpResponse.statusCode)
                }
                
                callback.onSuccessWithId(iOSHttpResponse(statusCode: statusCode,
                    content: String(data: data!, encoding: NSUTF8StringEncoding)))
            }
        })
        
        // Run the task. NOTE: NSURLSession is asynchronous by design.
        task.resume()
        
    }
    
    func setContentTypeWithNSString(contentType: String!) {
        request.setValue(contentType, forHTTPHeaderField: "Content-Type")
    }
    
}