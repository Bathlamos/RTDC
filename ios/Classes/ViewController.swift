//
//  ViewController.swift
//  Sample
//
//  Created by Nicolas Ménard on 2014-12-15.
//  Copyright (c) 2014 Nicolas Ménard. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    
    var unit = RtdcCoreUnit(NSString: "Pizza")
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
        
        println(unit.getName())
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

