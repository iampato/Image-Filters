import 'dart:async';

import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

class Filter {
  static List<String> filters = [
    "original",
    "AweStruckVibe",
    "Clarendon",
    "OldMan",
    "Mars",
    "Rise",
    "April",
    "Amazon",
    "StarLit",
    "NightWhisper",
    "LimeStutter",
    "Haan",
    "BlueMess",
    "Adele",
    "Cruz",
    "Metropolis",
    "Audrey"
  ];

  static const MethodChannel _channel = const MethodChannel('filter');

  static Future getThumbs(String filePath) async {
    if (filePath == null) {
      throw PlatformException(code: "Filter", message: "Filepath is null");
    }
    var result = await _channel.invokeMethod('generateFilters', filePath);
    return result;
  }

  static Future finalOutput(String filePath, int index) async {
    var result = await _channel.invokeMethod(
      'final_output',
      <String, dynamic>{
        'path': filePath,
        'filter': index,
      },
    );
    return result;
  }
}
