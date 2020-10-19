import 'dart:io';
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:filter/filter.dart';
import 'package:image_picker/image_picker.dart';

void main() {
  runApp(MyApp());
}

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
  final ImagePicker _picker = ImagePicker();

  void takeImage() async {
    PickedFile images = await _picker.getImage(source: ImageSource.gallery);
    setState(() {
      imagePath = images.path;
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
    } catch(e){
      results = 'Error: $e';
    }

    if (!mounted) return;

    setState(() {
      _results = results;
    });
  }

  Future<void> applyFinal(int index) async {
    var results;

    try {
      results = await Filter.finalOutput(imagePath, index + 1);
    } catch(e){
      results = 'Error: $e';
    }

    if (!mounted) return;

    print("###### results: $results");
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Plugin example app'),
        actions: [
          CircularProgressIndicator(
            backgroundColor: Colors.white,
          )
        ],
      ),
      body: Stack(
        alignment: Alignment.bottomCenter,
        children: <Widget>[
          Center(
            child: imagePath == null
                ? FlatButton(
                    onPressed: () {
                      takeImage();
                    },
                    child: Text("pick"),
                  )
                : Image.file(File(imagePath)),
          ),
          _results == null
              ? Container()
              : Container(
                  height: 180,
                  width: double.infinity,
                  child: ListView.builder(
                    itemCount: _results.length,
                    scrollDirection: Axis.horizontal,
                    itemBuilder: (BuildContext context, int index) {
                      return InkWell(
                        child: Padding(
                          padding: const EdgeInsets.all(8.0),
                          child: Column(
                            mainAxisSize: MainAxisSize.min,
                            children: [
                              Text("${Filter.filters[index]}"),
                              Image.memory(
                                _results[index],
                              ),
                            ],
                          ),
                        ),
                        onTap: () {
                          applyFinal(index);
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
