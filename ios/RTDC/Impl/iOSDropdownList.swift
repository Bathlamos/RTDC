//
//  iOSDropdownList.swift
//  RTDC
//
//  Created by Nicolas Ménard on 2015-10-12.
//  Copyright © 2015 Clermont, Ermel, Fortin-Boulay, Legault & Ménard. All rights reserved.
//

import Foundation
import UIKit

class iOSDropdownList: ImplUiDropdown, ImplUiDropdownProtocol, UITableViewDataSource, UITableViewDelegate {
    
    private var elements: IOSObjectArray = IOSObjectArray()

    // UITableViewDataSource
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return Int(elements.length())
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {

        let identifier = "identifier"
        let cell = tableView.dequeueReusableCellWithIdentifier(identifier, forIndexPath: indexPath)
        
        cell.textLabel?.text = String(elements.objectAtIndex(UInt(indexPath.row)))
        
        return cell
    }
    
    // UITableViewDelegate
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        
    }
    
    // ImplUiDropdown
    func getValue() -> AnyObject! {
        return ""
    }
    
    func setValueWithId(value: AnyObject!) {
        
    }
    
    func getErrorMessage() -> String! {
        return ""
    }
    
    func setErrorMessageWithNSString(errorMessage: String!) {
        // Does nothing
    }
    
    func setFocusWithBoolean(hasFocus: jboolean) {

    }
    
    func getSelectedIndex() -> jint {
        return -1
    }
    
    func setStringifierWithUtilStringifier(stringifier: UtilStringifier!) {

    }
    
    func setArrayWithNSObjectArray(elements: IOSObjectArray!) {
        self.elements = elements
    }
    
}