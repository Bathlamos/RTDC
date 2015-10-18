//
//  IOSDispatcher.swift
//  RTDC
//
//  Created by Nicolas Ménard on 2015-03-02.
//  Copyright © 2015 Clermont, Ermel, Fortin-Boulay, Legault & Ménard. All rights reserved.
//

import Foundation

class IOSDispatcher: NSObject, ImplDispatcher {

    func goToLoginWithControllerController(caller: ControllerController) {
        
    }
    
    func goToAllUnitsWithControllerController(caller: ControllerController) {
        presentViewController(caller, storyboardName: "Main", viewControllerId: "CapacityOverview")
    }
    
    func goToActionPlanWithControllerController(caller: ControllerController) {

    }
    
    func goToEditUserWithControllerController(caller: ControllerController) {
        
    }
    
    func goToEditUnitWithControllerController(caller: ControllerController) {
        
    }
    
    func goToEditActionWithControllerController(caller: ControllerController) {
        
    }
    
    func goToEditCapacityWithControllerController(caller: ControllerController) {
        
    }
    
    func goToCapacityOverviewWithControllerController(caller: ControllerController) {
        presentViewController(caller, storyboardName: "Main", viewControllerId: "CapacityOverview")
    }
    
    private func presentViewController(sender: ControllerController?, storyboardName: String!, viewControllerId: String!){
        if sender != nil{
            let view = sender!.getView() as! UIViewController
            let sb: UIStoryboard = UIStoryboard(name: storyboardName, bundle: nil)
            let vc = sb.instantiateViewControllerWithIdentifier(viewControllerId)
            
            NSOperationQueue.mainQueue().addOperationWithBlock {
                view.presentViewController(vc, animated: true, completion: nil)
            }

        }
    }
}