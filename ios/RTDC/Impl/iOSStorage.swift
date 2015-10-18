//
//  IOSStorage.swift
//  RTDC
//
//  Created by Nicolas Ménard on 2015-10-08.
//  Copyright © 2015 Clermont, Ermel, Fortin-Boulay, Legault & Ménard. All rights reserved.
//

import Foundation

class IOSStorage: ImplStorage, ImplStorageProtocol {
    
    private let settings = NSUserDefaults.standardUserDefaults()
    private var INSTANCE: IOSStorage? = nil
    
    func get() -> IOSStorage {
        if INSTANCE == nil{
            INSTANCE = IOSStorage()
        }
        
        return INSTANCE!
    }
    
    func addWithNSString(key: String!, withNSString data: String!) {
        settings.setObject(data, forKey: key)
    }
    
    func retrieveWithNSString(key: String!) -> String {
        return settings.stringForKey(key)!
    }
    
    func removeWithNSString(key: String!) {
        settings.removeObjectForKey(key)
    }
    
}