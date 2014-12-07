//
//  MasterViewController.h
//  RTDC
//
//  Created by Philippe Legault on 2014-12-07.
//  Copyright (c) 2014 Legault. All rights reserved.
//

#import <UIKit/UIKit.h>

@class DetailViewController;

@interface MasterViewController : UITableViewController

@property (strong, nonatomic) DetailViewController *detailViewController;


@end

