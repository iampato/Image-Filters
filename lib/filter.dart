import 'dart:async';
import 'dart:typed_data';

import 'package:flutter/services.dart';

class Filter {
  static const MethodChannel _channel =
      const MethodChannel('filter');

  static Future getThumbs(Uint8List data) async {
    var result = await _channel.invokeMethod('generateFilters', data);
    return result;
  }
}
