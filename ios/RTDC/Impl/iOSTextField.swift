//
//  IOSTextField.swift
//  RTDC
//
//  Created by Nicolas Ménard on 2015-10-12.
//  Copyright © 2015 Clermont, Ermel, Fortin-Boulay, Legault & Ménard. All rights reserved.
//

import Foundation
import UIKit

class IOSTextField: UITextField, ImplUiElement {

    func getValue() -> AnyObject! {
        return text!
    }
    
    func setValueWithId(value: AnyObject!) {
        text = value as? String
    }
    
    func getErrorMessage() -> String! {
        return ""
    }
    
    func setErrorMessageWithNSString(errorMessage: String!) {
        // Do nothing
    }
    
    func setFocusWithBoolean(hasFocus: jboolean) {
        if !hasFocus {
            becomeFirstResponder()
        }
    }

}