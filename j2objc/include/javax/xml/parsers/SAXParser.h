//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/javax/xml/parsers/SAXParser.java
//

#ifndef _JavaxXmlParsersSAXParser_H_
#define _JavaxXmlParsersSAXParser_H_

@class JavaIoFile;
@class JavaIoInputStream;
@class JavaxXmlValidationSchema;
@class OrgXmlSaxHandlerBase;
@class OrgXmlSaxHelpersDefaultHandler;
@class OrgXmlSaxInputSource;
@protocol OrgXmlSaxParser;
@protocol OrgXmlSaxXMLReader;

#import "JreEmulation.h"

#define JavaxXmlParsersSAXParser_DEBUG NO

@interface JavaxXmlParsersSAXParser : NSObject {
}

- (instancetype)init;

- (void)reset;

- (void)parseWithJavaIoInputStream:(JavaIoInputStream *)is
          withOrgXmlSaxHandlerBase:(OrgXmlSaxHandlerBase *)hb;

- (void)parseWithJavaIoInputStream:(JavaIoInputStream *)is
          withOrgXmlSaxHandlerBase:(OrgXmlSaxHandlerBase *)hb
                      withNSString:(NSString *)systemId;

- (void)parseWithJavaIoInputStream:(JavaIoInputStream *)is
withOrgXmlSaxHelpersDefaultHandler:(OrgXmlSaxHelpersDefaultHandler *)dh;

- (void)parseWithJavaIoInputStream:(JavaIoInputStream *)is
withOrgXmlSaxHelpersDefaultHandler:(OrgXmlSaxHelpersDefaultHandler *)dh
                      withNSString:(NSString *)systemId;

- (void)parseWithNSString:(NSString *)uri
 withOrgXmlSaxHandlerBase:(OrgXmlSaxHandlerBase *)hb;

- (void)parseWithNSString:(NSString *)uri
withOrgXmlSaxHelpersDefaultHandler:(OrgXmlSaxHelpersDefaultHandler *)dh;

- (void)parseWithJavaIoFile:(JavaIoFile *)f
   withOrgXmlSaxHandlerBase:(OrgXmlSaxHandlerBase *)hb;

- (void)parseWithJavaIoFile:(JavaIoFile *)f
withOrgXmlSaxHelpersDefaultHandler:(OrgXmlSaxHelpersDefaultHandler *)dh;

- (void)parseWithOrgXmlSaxInputSource:(OrgXmlSaxInputSource *)is
             withOrgXmlSaxHandlerBase:(OrgXmlSaxHandlerBase *)hb;

- (void)parseWithOrgXmlSaxInputSource:(OrgXmlSaxInputSource *)is
   withOrgXmlSaxHelpersDefaultHandler:(OrgXmlSaxHelpersDefaultHandler *)dh;

- (id<OrgXmlSaxParser>)getParser;

- (id<OrgXmlSaxXMLReader>)getXMLReader;

- (jboolean)isNamespaceAware;

- (jboolean)isValidating;

- (void)setPropertyWithNSString:(NSString *)name
                         withId:(id)value;

- (id)getPropertyWithNSString:(NSString *)name;

- (JavaxXmlValidationSchema *)getSchema;

- (jboolean)isXIncludeAware;

@end

__attribute__((always_inline)) inline void JavaxXmlParsersSAXParser_init() {}

J2OBJC_STATIC_FIELD_GETTER(JavaxXmlParsersSAXParser, DEBUG, jboolean)

#endif // _JavaxXmlParsersSAXParser_H_