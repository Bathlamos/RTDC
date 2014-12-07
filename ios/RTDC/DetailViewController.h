//
//  DetailViewController.h
//  RTDC
//
//  Created by Philippe Legault on 2014-12-07.
//  Copyright (c) 2014 Legault. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface DetailViewController : UIViewController

@property (strong, nonatomic) id detailItem;
@property (weak, nonatomic) IBOutlet UILabel *detailDescriptionLabel;

@end

