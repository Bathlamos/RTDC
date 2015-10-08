//
//  iOSStorage.swift
//  RTDC
//
//  Created by Nicolas Ménard on 2015-10-08.
//  Copyright © 2015 Clermont, Ermel, Fortin-Boulay, Legault & Ménard. All rights reserved.
//

import Foundation

class iOSStorage: ImplStorage {
    
    override init(){
        
    }
    
    func get() -> ImplStorage {
        //That's a mistake
        return iOSStorage()
    }
    
    func addWithNSString(key: String!, data: String!) {
        
    }
    
    func retrieveWithNSString(key: String!) -> String! {
        return nil;
    }
    
    func removeWithNSString(key: String!) {
        
    }
    
}