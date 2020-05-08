#import "FilterPlugin.h"
#if __has_include(<filter/filter-Swift.h>)
#import <filter/filter-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "filter-Swift.h"
#endif

@implementation FilterPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftFilterPlugin registerWithRegistrar:registrar];
}
@end
