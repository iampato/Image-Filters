import 'dart:io';

import 'package:flutter/material.dart';

class TestPage extends StatelessWidget {
  final String filePath;

  const TestPage({Key key, this.filePath}) : super(key: key);
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Image.file(File(filePath)),
      ),
    );
  }
}
