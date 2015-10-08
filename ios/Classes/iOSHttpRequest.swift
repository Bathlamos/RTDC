//
//  iOSHttpRequest.swift
//  RTDC
//
//  Created by Nicolas Ménard on 2015-03-02.
//  Copyright (c) 2015 Clermont, Ermel, Fortin-Boulay, Legault & Ménard. All rights reserved.
//

import Foundation

class iOSHttpRequest: NSObject, ImplHttpRequest {
    
    var request: NSMutableURLRequest
    var urlComponents: NSURLComponents
    
    init(url: String, requestMethod: String){
        urlComponents = NSURLComponents(string: url)!
        
        request = NSMutableURLRequest()
        request.HTTPMethod = requestMethod
    }
        
    
    func addParameterWithNSString(parameter: String!, withNSString data: String!){
        if urlComponents.query? != nil {
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
                callback.onErrorWithNSString(error.localizedDescription)
            } else {
                var statusCode: jint = 200 // Default status code
                
                // Checks if the response is an HTTP Response. If yes, we get the correct status code.
                if let httpResponse = response as? NSHTTPURLResponse {
                    statusCode = Int32(httpResponse.statusCode)
                }
                
                callback.onSuccessWithId(iOSHttpResponse(statusCode: statusCode,
                    // Converts the content data from NSData to NString
                    content: NSString(data:data, encoding:NSUTF8StringEncoding)))
            }
        })
        
        // Run the task. NOTE: NSURLSession is asynchronous by design.
        task.resume()
        
    }
    
}