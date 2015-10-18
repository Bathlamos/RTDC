//
//  LoginVC.swift
//  Sample
//
//  Created by Nicolas Ménard on 2014-12-15.
//  Copyright (c) 2014 Nicolas Ménard. All rights reserved.
//

import UIKit


class LoginVC: UIViewController, ViewLoginView {
    
    @IBOutlet var textUsername: UITextField?
    @IBOutlet var textPassword: UITextField?
    
    var controller: ControllerLoginController!

    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        self.controller = ControllerLoginController(viewLoginView: self)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func displayPermanentErrorWithNSString(title: String!, withNSString error: String!) {
        println(title + ":" + error)
    }
    
    func displayErrorWithNSString(title: String!, withNSString error: String!) {
        println(title + ":" + error)
    }
    
    func getUsername() -> String! {
        return textUsername!.text
    }
    
    func setUsernameWithNSString(username: String!) {
        textUsername!.text = username
    }
    
    func getPassword() -> String! {
        return textPassword!.text
    }

    func setPasswordWithNSString(password: String!) {
        textPassword!.text = password
    }
    
    func saveAuthenticationTokenWithNSString(token: String!) {
        
    }

    @IBAction func signIn(sender: AnyObject) {
        controller.login()
    }
}

