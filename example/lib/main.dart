import 'dart:io';

import 'package:christian_picker_image/christian_picker_image.dart';
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
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: HomePage(),
    );
  }
}

class HomePage extends StatefulWidget {
  const HomePage({
    Key key,
  }) : super(key: key);

  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  List _results;
  String imagePath;

  void takeImage(BuildContext context) async {
    List<File> images = await ChristianPickerImage.pickImages(maxImages: 1);
    setState(() {
      imagePath = images[0].path;
    });
    Navigator.of(context).pop();
  }

  Future _pickImage(BuildContext context) async {
    showDialog<Null>(
        context: context,
        barrierDismissible: false,
        builder: (BuildContext context) {
          takeImage(context);
          return Center();
        });
  }

  @override
  void initState() {
    super.initState();
  }

  Future<void> applyFilters() async {
    var results;

    try {
      results = await Filter.getThumbs(imagePath);
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
    return Scaffold(
      appBar: AppBar(
        title: const Text('Plugin example app'),
      ),
      body: Stack(
        alignment: Alignment.bottomCenter,
        children: <Widget>[
          Center(
            child: imagePath == null
                ? FlatButton(
                    onPressed: () {
                      _pickImage(context);
                    },
                    child: Text("pick"),
                  )
                : Image.file(File(imagePath)),
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
                              _results[index]
                              // File(),
                            ),
                          ),
                        ),
                        onTap: () {
                          // setState(() {
                          //   imagePath = _results[index];
                          // });
                        },
                      );
                    },
                  ),
                ),
        ],
      ),
      floatingActionButton: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          FloatingActionButton(
            heroTag: "btn2",
            child: Icon(Icons.done),
            onPressed: () => setState(() {}),
          ),
          SizedBox(height: 10),
          FloatingActionButton(
            heroTag: "btn1",
            child: Icon(Icons.add),
            onPressed: () => applyFilters(),
          ),
        ],
      ),
    );
  }
}
