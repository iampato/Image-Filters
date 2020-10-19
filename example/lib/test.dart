import 'dart:io';

import 'package:flutter/material.dart';

class TestPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Image.file(File(
            "/data/user/0/com.example.filter_example/cache/image_filter_65c8d81f-aca9-4961-a1e2-46d546139dfb551677842.jpg")),
      ),
    );
  }
}
