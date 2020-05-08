import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:filter/filter.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  List _results;
  Uint8List imageData;

  @override
  void initState() {
    super.initState();
    rootBundle.load('asset/test.jpeg').then((data) {
      Uint8List image = data.buffer.asUint8List();
      setState(() {
        imageData = image;
      });
    });
  }

  Future<void> applyFilters() async {
    var results;

    try {
      results = await Filter.getThumbs(imageData);
    } on PlatformException {
      results = 'Failed to get image thumbnails.';
    }

    if (!mounted) return;

    setState(() {
      _results = results;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Stack(
          alignment: Alignment.bottomCenter,
          children: <Widget>[
            Center(
              child: imageData == null
                  ? CircularProgressIndicator()
                  : Image.memory(imageData),
            ),
            _results == null
                ? Container()
                : Container(
                    height: 150,
                    width: double.infinity,
                    child: ListView.builder(
                      itemCount: _results.length,
                      scrollDirection: Axis.horizontal,
                      itemBuilder: (BuildContext context, int index) {
                        return InkWell(
                          child: Padding(
                            padding: const EdgeInsets.all(8.0),
                            child: Container(
                              child: Image.memory(
                                _results[index],
                              ),
                            ),
                          ),
                          onTap: () {
                            setState(() {
                              imageData = _results[index];
                            });
                          },
                        );
                      },
                    ),
                  ),
          ],
        ),
        floatingActionButton: FloatingActionButton(
          child: Icon(Icons.add),
          onPressed: () => applyFilters(),
        ),
      ),
    );
  }
}
