<img src="https://raw.githubusercontent.com/Bathlamos/RTDC/master/wiki/img/RTDCLogo_Transparent.png" alt="RTDC logo" height="85" />

# RTDC Pilot Project <a href="https://travis-ci.org/Bathlamos/RTDC"><img src="https://travis-ci.org/Bathlamos/RTDC.svg?branch=master"></a>

Long wait times to receive care in hospitals is one of the major issue plaguing the healthcare system. A new approach to manage the patient flow in terms of hours and not days is called Real-Time Demand Capacity Management (RTDC). Briefly, the RTDC method has 4 steps:
  1. Assess the capacity (number of available beds + discharges by 2:00 PM)
  * Estimate the demand by 2:00 PM
  * Build and implement the plan to address the mismatch between the capacity and the demand
  * Evaluate the plan's execution at the end of the day

*Note: RTDC normally evaluates the capacity at 2pm, after most of the patients have been discharged for the day and before most of the new patients are admitted.*

## The Project

The RTDC Pilot Project consists of building an application to support the RTDC methodology. The goal is have a pilot study take place at either the Queensway Carleton Hospital, Montfort Hospital, or both.

The system was developed as part of a 4th year Software Engineering Capstone project at the University of Ottawa. The project was supervised by Dr. Daniel Amyot and delivered to Mr. Alain Mouttham, founder of the start-up HealthNow.

## The System

The system is a typical client-server application. The application targets tablets running **Android Jelly Bean (4.2.2 | API 17)** which interacts with a RESTful server. The system also provides unified communications (text messages, audio calls, video calls) via [Asterisk](http://www.asterisk.org/), an open source framework for building communications applications.

The architecture of the system was designed to be transpiled to build an iOS platform, as well as a web platform. Thus, a package (called 'Core') which contains the shared logic can be easily transpiled using using source-to-source compiler technologies such as [J2ObjC](http://j2objc.org/) for iOS, and [GWT](http://www.gwtproject.org/) for the web.

## Documentation

To get started with the project, we suggest that you read the following documents:

* [Asterisk Installation Guide](https://github.com/Bathlamos/RTDC/raw/master/wiki/doc/Asterisk%20Installation%20Guide.pdf)
* [Server Installation Guide](https://github.com/Bathlamos/RTDC/raw/master/wiki/doc/Server%20Installation%20Guide.pdf)
* [Development Guide](https://github.com/Bathlamos/RTDC/raw/master/wiki/doc/Development%20Guide.pdf)
* [User Guide](https://github.com/Bathlamos/RTDC/raw/master/wiki/doc/User%20Guide.pdf)
* [Design Report](https://github.com/Bathlamos/RTDC/raw/master/wiki/doc/Design%20Report.pdf)

*Note: To run the server with IntelliJ IDEA Community Edition, follow the Server Installation Guide*

## Screenshots

Below are screenshots of the main parts of the applications. For more screenshots, check out this [folder](https://github.com/Bathlamos/RTDC/tree/master/wiki/img/Screenshots).

<a href="https://raw.githubusercontent.com/Bathlamos/RTDC/master/wiki/img/Screenshots/Login.png"><img title="Login" src="https://raw.githubusercontent.com/Bathlamos/RTDC/master/wiki/img/Screenshots/Login.png" alt="Login Screenshot" height="250" ></a>
<a href="https://github.com/Bathlamos/RTDC/raw/master/wiki/img/Screenshots/CapacityOverview.png"><img title="Capacity Overview" src="https://github.com/Bathlamos/RTDC/raw/master/wiki/img/Screenshots/CapacityOverview.png" alt="Capacity Overview Screenshot" height="250" ></a>
<a href="https://raw.githubusercontent.com/Bathlamos/RTDC/master/wiki/img/Screenshots/ActionPlan.png"><img title="Action Plan" src="https://raw.githubusercontent.com/Bathlamos/RTDC/master/wiki/img/Screenshots/ActionPlan.png" alt="Action Plan Screenshot" height="250" ></a>
<a href="https://raw.githubusercontent.com/Bathlamos/RTDC/master/wiki/img/Screenshots/Messages.png"><img title="Messages" src="https://raw.githubusercontent.com/Bathlamos/RTDC/master/wiki/img/Screenshots/Messages.png" alt="Messages Screenshot" height="250" ></a>
<a href="https://raw.githubusercontent.com/Bathlamos/RTDC/master/wiki/img/Screenshots/Call.png"><img title="Audio Call" src="https://raw.githubusercontent.com/Bathlamos/RTDC/master/wiki/img/Screenshots/Call.png" alt="Audio Call Screenshot" height="250" ></a>
<a href="https://raw.githubusercontent.com/Bathlamos/RTDC/master/wiki/img/Screenshots/VideoCall.png"><img title="Video Call" src="https://raw.githubusercontent.com/Bathlamos/RTDC/master/wiki/img/Screenshots/VideoCall.png" alt="Video Call Screenshot" height="250" ></a>

## Further Reading

For more information on what inspired the project, be sure to read the following documents:

* <a href="https://github.com/Bathlamos/RTDC/tree/master/wiki/ref/Capstone student project.pptx">Original Capstone Student Project Overview</a>
* <a href="https://github.com/Bathlamos/RTDC/tree/master/wiki/ref/ED and Hospital-wide Flow Best Practices.pdf">ED and Hospital-wide Flow Best Practices</a>
* <a href="https://github.com/Bathlamos/RTDC/tree/master/wiki/ref/Location-Aware Business Process Management for Real-time Monitoring of a Cardiac Care Process.pdf">Location-Aware Business Process Management for Real-time Monitoring Monitoring of a Cardiac Care Process</a>
* <a href="https://github.com/Bathlamos/RTDC/tree/master/wiki/ref/Real-time Simulations to Support Operational Decision Making in Healthcare.pdf">Real-time Simulations to Support Operational Decision Making in Healthcare</a>
* <a href="http://www.sciencedirect.com/science/article/pii/S1877050914010047">Towards an RTLS-based Hand Hygiene Notification System</a>
* <a href="http://www.sciencedirect.com/science/article/pii/S1877050914010072">Location-Based Patient-Device Association and Disassociation</a>