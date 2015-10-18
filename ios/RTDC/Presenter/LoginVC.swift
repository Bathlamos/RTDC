//
//  LoginVC.swift
//  RTDC
//
//  Created by Nicolas Ménard on 2015-10-16.
//  Copyright © 2015 Clermont, Ermel, Fortin-Boulay, Legault & Ménard. All rights reserved.
//

import UIKit

class LoginVC: UIViewController, ViewLoginView {
    
    @IBOutlet var usernameTextField: IOSTextField!
    @IBOutlet var passwordTextField: IOSTextField!
    
    private var controller: ControllerLoginController!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
        if controller == nil{
            controller = ControllerLoginController(viewLoginView: self)
        }
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func login(sender: AnyObject) {
        controller.login()
    }
    
    func getUsernameUiElement() -> ImplUiElement! {
        return usernameTextField
    }
    
    func getPasswordUiElement() -> ImplUiElement! {
        return passwordTextField
    }
    
    func displayErrorWithNSString(title: String!, withNSString error: String!) {
        let alertController = UIAlertController(title: title, message: error, preferredStyle: UIAlertControllerStyle.Alert)
        alertController.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.Default, handler: nil))
        
        NSOperationQueue.mainQueue().addOperationWithBlock {
            self.presentViewController(alertController, animated: true, completion: nil)
        }
    }
    
    func clearError() {
        // Do nothing
    }
    
    func setTitleWithNSString(title: String!) {
        // Do nothing
    }
}
