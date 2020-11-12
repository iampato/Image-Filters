import 'dart:io';
import 'package:filter_example/test.dart';
import 'package:flutter/material.dart';
import 'dart:async';
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
  String original;
  String imagePath;

  final ImagePicker _picker = ImagePicker();

  void takeImage() async {
    PickedFile images = await _picker.getImage(source: ImageSource.gallery);
    setState(() {
      imagePath = images.path;
      original = images.path;
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
    } catch (e) {
      results = 'Error: $e';
    }

    if (!mounted) return;
    print("returned");
    print(results);

    setState(() {
      _results = results;
    });
  }

  Future applyFinal(int index) async {
    var results;

    try {
      results = await Filter.finalOutput(original, index + 1);
    } catch (e) {
      results = 'Error: $e';
    }

    if (!mounted) return;

    setState(() {
      imagePath = results;
    });

    print("###### results: $results");
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Plugin example app'),
        actions: [
          IconButton(
            icon: Icon(Icons.done),
            onPressed: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) {
                  return TestPage(
                    filePath: imagePath,
                  );
                }),
              );
            },
          )
        ],
      ),
      body: Stack(
        alignment: Alignment.bottomCenter,
        children: <Widget>[
          Center(
            child: imagePath == null
                ?  Text("Press the plus icon to add image")
                  
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
                              Text(
                                "${Filter.filters[index]}",
                                style: TextStyle(
                                  fontSize: 20,
                                  color: Colors.white,
                                ),
                              ),
                              CircleAvatar(
                                radius: 60,
                                backgroundImage: MemoryImage(
                                  _results[index],
                                ),
                              ),
                            ],
                          ),
                        ),
                        onTap: () {
                          if (index == 0) {
                            return null;
                          } else {
                            print("###index## $index");
                            applyFinal(index);
                          }
                        },
                      );
                    },
                  ),
                ),
        ],
      ),
      floatingActionButton: FloatingActionButton(
        heroTag: "btn1",
        child: imagePath == null ? Icon(Icons.add) : Icon(Icons.color_lens),
        onPressed: imagePath == null ? () => takeImage() : () => applyFilters(),
      ),
    );
  }
}
