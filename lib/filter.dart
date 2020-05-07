import 'dart:async';
import 'dart:typed_data';

import 'package:flutter/services.dart';

class Filter {
  static const MethodChannel _channel =
      const MethodChannel('filter');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
  static Future getThumbs(Uint8List data) async {
    var result = await _channel.invokeMethod('generateFilters', data);
    return result;
  }
}
