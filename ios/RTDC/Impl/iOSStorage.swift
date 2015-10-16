//
//  iOSStorage.swift
//  RTDC
//
//  Created by Nicolas Ménard on 2015-10-08.
//  Copyright © 2015 Clermont, Ermel, Fortin-Boulay, Legault & Ménard. All rights reserved.
//

import Foundation

class iOSStorage: ImplStorage, ImplStorageProtocol {
    
    private let settings = NSUserDefaults.standardUserDefaults()
    private var INSTANCE: iOSStorage? = nil
    
    func get() -> iOSStorage {
        if INSTANCE == nil{
            INSTANCE = iOSStorage()
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