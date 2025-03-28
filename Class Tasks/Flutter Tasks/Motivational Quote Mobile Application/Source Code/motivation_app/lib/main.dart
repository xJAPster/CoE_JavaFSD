import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:path/path.dart' as path;
import 'package:sqflite/sqflite.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:cloud_firestore/cloud_firestore.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp();
  runApp(MyApp());
}

class Quote {
  final int? id;
  final String text;
  final String author;

  Quote({this.id, required this.text, required this.author});

  factory Quote.fromJson(Map<String, dynamic> json) {
    return Quote(
      text: json['text'] ?? 'No quote available.',
      author: json['author'] ?? 'Unknown',
    );
  }

  Map<String, dynamic> toMap() {
    return {'text': text, 'author': author};
  }
}

class DatabaseHelper {
  static final DatabaseHelper _instance = DatabaseHelper._internal();
  factory DatabaseHelper() => _instance;
  DatabaseHelper._internal();
  Database? _database;

  Future<Database> get database async {
    if (_database != null) return _database!;
    _database = await _initDB('quotes.db');
    return _database!;
  }

  Future<Database> _initDB(String filePath) async {
    final dbPath = path.join(await getDatabasesPath(), filePath);
    return await openDatabase(
      dbPath,
      version: 1,
      onCreate: (db, version) async {
        await db.execute('''
          CREATE TABLE favorites(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            text TEXT NOT NULL,
            author TEXT NOT NULL
          )
        ''');
      },
    );
  }

  Future<void> insertQuote(Quote quote) async {
    final db = await database;
    await db.insert(
      'favorites',
      quote.toMap(),
      conflictAlgorithm: ConflictAlgorithm.replace,
    );
  }

  Future<List<Quote>> getFavorites() async {
    final db = await database;
    final List<Map<String, dynamic>> maps = await db.query('favorites');
    return List.generate(maps.length, (i) {
      return Quote(
        id: maps[i]['id'],
        text: maps[i]['text'],
        author: maps[i]['author'],
      );
    });
  }
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Daily Motivation',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(primarySwatch: Colors.blue),
      home: HomeScreen(),
      routes: {'/favorites': (context) => FavoritesScreen()},
    );
  }
}

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  Quote? _quote;
  bool _loading = false;
  final dbHelper = DatabaseHelper();

  @override
  void initState() {
    super.initState();
    _fetchQuote();
  }

  Future<void> _fetchQuote() async {
    setState(() {
      _loading = true;
    });
    try {
      final response = await http.get(
        Uri.parse('https://thequoteshub.com/api/random'),
      );
      if (response.statusCode == 200) {
        final jsonResponse = json.decode(response.body);
        setState(() {
          _quote = Quote.fromJson(jsonResponse);
          _loading = false;
        });
      } else {
        setState(() {
          _loading = false;
        });
      }
    } catch (e) {
      setState(() {
        _loading = false;
      });
    }
  }

  void _saveFavorite() async {
    if (_quote != null) {
      await dbHelper.insertQuote(_quote!);
      FirebaseFirestore.instance.collection('favorites').add(_quote!.toMap());
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(SnackBar(content: Text('Quote saved!')));
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Daily Motivation'),
        actions: [
          IconButton(
            icon: Icon(Icons.favorite),
            onPressed: () {
              Navigator.pushNamed(context, '/favorites');
            },
          ),
        ],
      ),
      body: Center(
        child:
            _loading
                ? CircularProgressIndicator()
                : _quote != null
                ? Padding(
                  padding: const EdgeInsets.all(16.0),
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Text(
                        '"${_quote!.text}"',
                        textAlign: TextAlign.center,
                        style: TextStyle(
                          fontSize: 24,
                          fontStyle: FontStyle.italic,
                        ),
                      ),
                      SizedBox(height: 20),
                      Text(
                        '- ${_quote!.author}',
                        style: TextStyle(
                          fontSize: 20,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                      SizedBox(height: 30),
                      Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          ElevatedButton(
                            onPressed: _fetchQuote,
                            child: Text('Next Quote'),
                          ),
                          SizedBox(width: 20),
                          ElevatedButton(
                            onPressed: _saveFavorite,
                            child: Text('Save'),
                          ),
                        ],
                      ),
                    ],
                  ),
                )
                : Text('Error fetching quote'),
      ),
    );
  }
}

class FavoritesScreen extends StatefulWidget {
  const FavoritesScreen({super.key});

  @override
  _FavoritesScreenState createState() => _FavoritesScreenState();
}

class _FavoritesScreenState extends State<FavoritesScreen> {
  List<Quote> _favorites = [];
  final dbHelper = DatabaseHelper();

  @override
  void initState() {
    super.initState();
    _loadFavorites();
  }

  Future<void> _loadFavorites() async {
    final favs = await dbHelper.getFavorites();
    setState(() {
      _favorites = favs;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Favorites')),
      body:
          _favorites.isEmpty
              ? Center(child: Text('No favorite quotes yet.'))
              : ListView.builder(
                itemCount: _favorites.length,
                itemBuilder: (context, index) {
                  final quote = _favorites[index];
                  return ListTile(
                    title: Text('"${quote.text}"'),
                    subtitle: Text('- ${quote.author}'),
                  );
                },
              ),
    );
  }
}
