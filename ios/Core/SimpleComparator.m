//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/nicolasmenard/IdeaProjects/RTDC/core/src/main/java/rtdc/core/model/SimpleComparator.java
//

#include "IOSClass.h"
#include "J2ObjC_source.h"
#include "ObjectProperty.h"
#include "RootObject.h"
#include "SimpleComparator.h"
#include "java/lang/Comparable.h"
#include "java/lang/Double.h"
#include "java/lang/Math.h"
#include "java/math/BigInteger.h"
#include "java/util/Comparator.h"
#include "java/util/regex/Matcher.h"
#include "java/util/regex/Pattern.h"

static ModelSimpleComparator_NumberAwareStringComparator *ModelSimpleComparator_NUMBER_AWARE_STRING_COMPARATOR_;
J2OBJC_STATIC_FIELD_GETTER(ModelSimpleComparator, NUMBER_AWARE_STRING_COMPARATOR_, ModelSimpleComparator_NumberAwareStringComparator *)

@interface ModelSimpleComparator_Builder () {
 @public
  jboolean ascending_;
  id<ModelObjectProperty> property_;
}

- (instancetype)initWithModelObjectProperty:(id<ModelObjectProperty>)property;

@end

J2OBJC_FIELD_SETTER(ModelSimpleComparator_Builder, property_, id<ModelObjectProperty>)

__attribute__((unused)) static void ModelSimpleComparator_Builder_initWithModelObjectProperty_(ModelSimpleComparator_Builder *self, id<ModelObjectProperty> property);

__attribute__((unused)) static ModelSimpleComparator_Builder *new_ModelSimpleComparator_Builder_initWithModelObjectProperty_(id<ModelObjectProperty> property) NS_RETURNS_RETAINED;

@interface ModelSimpleComparator_Builder_$1 : NSObject < JavaUtilComparator > {
 @public
  ModelSimpleComparator_Builder *this$0_;
}

- (jint)compareWithId:(ModelRootObject *)a
               withId:(ModelRootObject *)b;

- (instancetype)initWithModelSimpleComparator_Builder:(ModelSimpleComparator_Builder *)outer$;

@end

J2OBJC_EMPTY_STATIC_INIT(ModelSimpleComparator_Builder_$1)

J2OBJC_FIELD_SETTER(ModelSimpleComparator_Builder_$1, this$0_, ModelSimpleComparator_Builder *)

__attribute__((unused)) static void ModelSimpleComparator_Builder_$1_initWithModelSimpleComparator_Builder_(ModelSimpleComparator_Builder_$1 *self, ModelSimpleComparator_Builder *outer$);

__attribute__((unused)) static ModelSimpleComparator_Builder_$1 *new_ModelSimpleComparator_Builder_$1_initWithModelSimpleComparator_Builder_(ModelSimpleComparator_Builder *outer$) NS_RETURNS_RETAINED;

J2OBJC_TYPE_LITERAL_HEADER(ModelSimpleComparator_Builder_$1)

static JavaUtilRegexPattern *ModelSimpleComparator_NumberAwareStringComparator_PATTERN_;
J2OBJC_STATIC_FIELD_GETTER(ModelSimpleComparator_NumberAwareStringComparator, PATTERN_, JavaUtilRegexPattern *)

J2OBJC_INITIALIZED_DEFN(ModelSimpleComparator)

@implementation ModelSimpleComparator

+ (ModelSimpleComparator_Builder *)forPropertyWithModelObjectProperty:(id<ModelObjectProperty>)property {
  return ModelSimpleComparator_forPropertyWithModelObjectProperty_(property);
}

J2OBJC_IGNORE_DESIGNATED_BEGIN
- (instancetype)init {
  ModelSimpleComparator_init(self);
  return self;
}
J2OBJC_IGNORE_DESIGNATED_END

+ (void)initialize {
  if (self == [ModelSimpleComparator class]) {
    ModelSimpleComparator_NUMBER_AWARE_STRING_COMPARATOR_ = new_ModelSimpleComparator_NumberAwareStringComparator_init();
    J2OBJC_SET_INITIALIZED(ModelSimpleComparator)
  }
}

+ (const J2ObjcClassInfo *)__metadata {
  static const J2ObjcMethodInfo methods[] = {
    { "forPropertyWithModelObjectProperty:", "forProperty", "Lrtdc.core.model.SimpleComparator$Builder;", 0x9, NULL, "<T:Lrtdc/core/model/RootObject;>(Lrtdc/core/model/ObjectProperty<TT;>;)Lrtdc/core/model/SimpleComparator$Builder<TT;>;" },
    { "init", NULL, NULL, 0x1, NULL, NULL },
  };
  static const J2ObjcFieldInfo fields[] = {
    { "NUMBER_AWARE_STRING_COMPARATOR_", NULL, 0x1a, "Lrtdc.core.model.SimpleComparator$NumberAwareStringComparator;", &ModelSimpleComparator_NUMBER_AWARE_STRING_COMPARATOR_, NULL, .constantValue.asLong = 0 },
  };
  static const char *inner_classes[] = {"Lrtdc.core.model.SimpleComparator$Builder;", "Lrtdc.core.model.SimpleComparator$NumberAwareStringComparator;"};
  static const J2ObjcClassInfo _ModelSimpleComparator = { 2, "SimpleComparator", "rtdc.core.model", NULL, 0x1, 2, methods, 1, fields, 0, NULL, 2, inner_classes, NULL, NULL };
  return &_ModelSimpleComparator;
}

@end

ModelSimpleComparator_Builder *ModelSimpleComparator_forPropertyWithModelObjectProperty_(id<ModelObjectProperty> property) {
  ModelSimpleComparator_initialize();
  return new_ModelSimpleComparator_Builder_initWithModelObjectProperty_(property);
}

void ModelSimpleComparator_init(ModelSimpleComparator *self) {
  (void) NSObject_init(self);
}

ModelSimpleComparator *new_ModelSimpleComparator_init() {
  ModelSimpleComparator *self = [ModelSimpleComparator alloc];
  ModelSimpleComparator_init(self);
  return self;
}

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(ModelSimpleComparator)

@implementation ModelSimpleComparator_Builder

- (instancetype)initWithModelObjectProperty:(id<ModelObjectProperty>)property {
  ModelSimpleComparator_Builder_initWithModelObjectProperty_(self, property);
  return self;
}

- (ModelSimpleComparator_Builder *)setAscendingWithBoolean:(jboolean)ascending {
  self->ascending_ = ascending;
  return self;
}

- (id<JavaUtilComparator>)build {
  return new_ModelSimpleComparator_Builder_$1_initWithModelSimpleComparator_Builder_(self);
}

+ (const J2ObjcClassInfo *)__metadata {
  static const J2ObjcMethodInfo methods[] = {
    { "initWithModelObjectProperty:", "Builder", NULL, 0x2, NULL, NULL },
    { "setAscendingWithBoolean:", "setAscending", "Lrtdc.core.model.SimpleComparator$Builder;", 0x1, NULL, NULL },
    { "build", NULL, "Ljava.util.Comparator;", 0x1, NULL, NULL },
  };
  static const J2ObjcFieldInfo fields[] = {
    { "ascending_", NULL, 0x2, "Z", NULL, NULL, .constantValue.asLong = 0 },
    { "property_", NULL, 0x12, "Lrtdc.core.model.ObjectProperty;", NULL, "Lrtdc/core/model/ObjectProperty<TT;>;", .constantValue.asLong = 0 },
  };
  static const J2ObjcClassInfo _ModelSimpleComparator_Builder = { 2, "Builder", "rtdc.core.model", "SimpleComparator", 0x9, 3, methods, 2, fields, 0, NULL, 0, NULL, NULL, "<T:Lrtdc/core/model/RootObject;>Ljava/lang/Object;" };
  return &_ModelSimpleComparator_Builder;
}

@end

void ModelSimpleComparator_Builder_initWithModelObjectProperty_(ModelSimpleComparator_Builder *self, id<ModelObjectProperty> property) {
  (void) NSObject_init(self);
  self->property_ = property;
}

ModelSimpleComparator_Builder *new_ModelSimpleComparator_Builder_initWithModelObjectProperty_(id<ModelObjectProperty> property) {
  ModelSimpleComparator_Builder *self = [ModelSimpleComparator_Builder alloc];
  ModelSimpleComparator_Builder_initWithModelObjectProperty_(self, property);
  return self;
}

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(ModelSimpleComparator_Builder)

@implementation ModelSimpleComparator_Builder_$1

- (jint)compareWithId:(ModelRootObject *)a
               withId:(ModelRootObject *)b {
  jint result = 0;
  if (a == b) result = 0;
  if (a == nil) result = -1;
  else if (b == nil) result = 1;
  else {
    id valueA = [a getValueWithModelObjectProperty:this$0_->property_];
    id valueB = [b getValueWithModelObjectProperty:this$0_->property_];
    if (valueA == valueB) result = 0;
    else if (valueA == nil) result = -1;
    else if (valueB == nil) result = 1;
    else if ([valueA isKindOfClass:[NSString class]] && [valueB isKindOfClass:[NSString class]]) result = [((ModelSimpleComparator_NumberAwareStringComparator *) nil_chk(JreLoadStatic(ModelSimpleComparator, NUMBER_AWARE_STRING_COMPARATOR_))) compareWithId:(NSString *) check_class_cast(valueA, [NSString class]) withId:(NSString *) check_class_cast(valueB, [NSString class])];
    else if ([JavaLangComparable_class_() isInstance:valueA]) result = [((id<JavaLangComparable>) check_protocol_cast(valueA, JavaLangComparable_class_())) compareToWithId:valueB];
    else if ([[valueA getClass] isPrimitive] && [[valueB getClass] isPrimitive]) result = JreFpToInt(JavaLangMath_signumWithDouble_([(JavaLangDouble *) check_class_cast(valueA, [JavaLangDouble class]) doubleValue] - [(JavaLangDouble *) check_class_cast(valueB, [JavaLangDouble class]) doubleValue]));
  }
  if (!this$0_->ascending_) result *= -1;
  return result;
}

- (instancetype)initWithModelSimpleComparator_Builder:(ModelSimpleComparator_Builder *)outer$ {
  ModelSimpleComparator_Builder_$1_initWithModelSimpleComparator_Builder_(self, outer$);
  return self;
}

+ (const J2ObjcClassInfo *)__metadata {
  static const J2ObjcMethodInfo methods[] = {
    { "compareWithId:withId:", "compare", "I", 0x1, NULL, "(TT;TT;)I" },
    { "initWithModelSimpleComparator_Builder:", "", NULL, 0x0, NULL, NULL },
  };
  static const J2ObjcFieldInfo fields[] = {
    { "this$0_", NULL, 0x1012, "Lrtdc.core.model.SimpleComparator$Builder;", NULL, NULL, .constantValue.asLong = 0 },
  };
  static const J2ObjCEnclosingMethodInfo enclosing_method = { "ModelSimpleComparator_Builder", "build" };
  static const J2ObjcClassInfo _ModelSimpleComparator_Builder_$1 = { 2, "", "rtdc.core.model", "SimpleComparator$Builder", 0x8008, 2, methods, 1, fields, 0, NULL, 0, NULL, &enclosing_method, "Ljava/lang/Object;Ljava/util/Comparator<TT;>;" };
  return &_ModelSimpleComparator_Builder_$1;
}

@end

void ModelSimpleComparator_Builder_$1_initWithModelSimpleComparator_Builder_(ModelSimpleComparator_Builder_$1 *self, ModelSimpleComparator_Builder *outer$) {
  self->this$0_ = outer$;
  (void) NSObject_init(self);
}

ModelSimpleComparator_Builder_$1 *new_ModelSimpleComparator_Builder_$1_initWithModelSimpleComparator_Builder_(ModelSimpleComparator_Builder *outer$) {
  ModelSimpleComparator_Builder_$1 *self = [ModelSimpleComparator_Builder_$1 alloc];
  ModelSimpleComparator_Builder_$1_initWithModelSimpleComparator_Builder_(self, outer$);
  return self;
}

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(ModelSimpleComparator_Builder_$1)

J2OBJC_INITIALIZED_DEFN(ModelSimpleComparator_NumberAwareStringComparator)

@implementation ModelSimpleComparator_NumberAwareStringComparator

- (jint)compareWithId:(NSString *)a
               withId:(NSString *)b {
  JavaUtilRegexMatcher *m1 = [((JavaUtilRegexPattern *) nil_chk(ModelSimpleComparator_NumberAwareStringComparator_PATTERN_)) matcherWithJavaLangCharSequence:a];
  JavaUtilRegexMatcher *m2 = [ModelSimpleComparator_NumberAwareStringComparator_PATTERN_ matcherWithJavaLangCharSequence:b];
  while ([((JavaUtilRegexMatcher *) nil_chk(m1)) find] && [((JavaUtilRegexMatcher *) nil_chk(m2)) find]) {
    jint nonDigitCompare = [((NSString *) nil_chk([m1 groupWithInt:1])) compareToWithId:[((JavaUtilRegexMatcher *) nil_chk(m2)) groupWithInt:1]];
    if (0 != nonDigitCompare) {
      return nonDigitCompare;
    }
    if ([((NSString *) nil_chk([m1 groupWithInt:2])) isEmpty]) {
      return [((NSString *) nil_chk([m2 groupWithInt:2])) isEmpty] ? 0 : -1;
    }
    else if ([((NSString *) nil_chk([m2 groupWithInt:2])) isEmpty]) {
      return 1;
    }
    JavaMathBigInteger *n1 = new_JavaMathBigInteger_initWithNSString_([m1 groupWithInt:2]);
    JavaMathBigInteger *n2 = new_JavaMathBigInteger_initWithNSString_([m2 groupWithInt:2]);
    jint numberCompare = [n1 compareToWithId:n2];
    if (0 != numberCompare) {
      return numberCompare;
    }
  }
  return [m1 hitEnd] && [((JavaUtilRegexMatcher *) nil_chk(m2)) hitEnd] ? 0 : [m1 hitEnd] ? -1 : 1;
}

J2OBJC_IGNORE_DESIGNATED_BEGIN
- (instancetype)init {
  ModelSimpleComparator_NumberAwareStringComparator_init(self);
  return self;
}
J2OBJC_IGNORE_DESIGNATED_END

+ (void)initialize {
  if (self == [ModelSimpleComparator_NumberAwareStringComparator class]) {
    ModelSimpleComparator_NumberAwareStringComparator_PATTERN_ = JavaUtilRegexPattern_compileWithNSString_(@"(\\D*)(\\d*)");
    J2OBJC_SET_INITIALIZED(ModelSimpleComparator_NumberAwareStringComparator)
  }
}

+ (const J2ObjcClassInfo *)__metadata {
  static const J2ObjcMethodInfo methods[] = {
    { "compareWithId:withId:", "compare", "I", 0x1, NULL, NULL },
    { "init", NULL, NULL, 0x1, NULL, NULL },
  };
  static const J2ObjcFieldInfo fields[] = {
    { "PATTERN_", NULL, 0x1a, "Ljava.util.regex.Pattern;", &ModelSimpleComparator_NumberAwareStringComparator_PATTERN_, NULL, .constantValue.asLong = 0 },
  };
  static const J2ObjcClassInfo _ModelSimpleComparator_NumberAwareStringComparator = { 2, "NumberAwareStringComparator", "rtdc.core.model", "SimpleComparator", 0x9, 2, methods, 1, fields, 0, NULL, 0, NULL, NULL, "Ljava/lang/Object;Ljava/util/Comparator<Ljava/lang/String;>;" };
  return &_ModelSimpleComparator_NumberAwareStringComparator;
}

@end

void ModelSimpleComparator_NumberAwareStringComparator_init(ModelSimpleComparator_NumberAwareStringComparator *self) {
  (void) NSObject_init(self);
}

ModelSimpleComparator_NumberAwareStringComparator *new_ModelSimpleComparator_NumberAwareStringComparator_init() {
  ModelSimpleComparator_NumberAwareStringComparator *self = [ModelSimpleComparator_NumberAwareStringComparator alloc];
  ModelSimpleComparator_NumberAwareStringComparator_init(self);
  return self;
}

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(ModelSimpleComparator_NumberAwareStringComparator)
