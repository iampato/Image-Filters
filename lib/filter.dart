import 'dart:async';

import 'package:flutter/services.dart';

class Filter {
  static const MethodChannel _channel = const MethodChannel('filter');

  static Future getThumbs(String filePath) async {
    var result = await _channel.invokeMethod('generateFilters', filePath);
    return result;
  }

  static Future finalOutput(String filePath) async {
    var result = await _channel.invokeMethod('final_output', filePath);
    return result;
  }
}
