import java.io.*;
import java.util.*;
import java.util.concurrent.locks.*;

class Book {
    private String title, author, ISBN;
    public Book(String title, String author, String ISBN) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
    }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getISBN() { return ISBN; }
}

class User {
    private String name, userID;
    private List<Book> borrowedBooks = new ArrayList<>();
    public User(String name, String userID) {
        this.name = name;
        this.userID = userID;
    }
    public String getUserID() { return userID; }
    public String getName() { return name; }
    public List<Book> getBorrowedBooks() { return borrowedBooks; }
}

interface ILibrary {
    void borrowBook(String ISBN, String userID) throws Exception;
    void returnBook(String ISBN, String userID) throws Exception;
    void reserveBook(String ISBN, String userID) throws Exception;
    Book searchBook(String title);
}

abstract class LibrarySystem implements ILibrary {
    protected List<Book> books = new ArrayList<>();
    protected List<User> users = new ArrayList<>();
    public abstract void addBook(Book book);
    public abstract void addUser(User user);
}

class LibraryManager extends LibrarySystem {
    private final Lock lock = new ReentrantLock();
    public void addBook(Book book) { books.add(book); }
    public void addUser(User user) { users.add(user); }
    public Book searchBook(String title) {
        return books.stream().filter(b -> b.getTitle().equalsIgnoreCase(title)).findFirst().orElse(null);
    }
    public void borrowBook(String ISBN, String userID) throws Exception {
        lock.lock();
        try {
            User user = users.stream().filter(u -> u.getUserID().equals(userID)).findFirst().orElseThrow(Exception::new);
            Book book = books.stream().filter(b -> b.getISBN().equals(ISBN)).findFirst().orElseThrow(Exception::new);
            if (user.getBorrowedBooks().size() >= 3) throw new Exception();
            user.getBorrowedBooks().add(book);
            System.out.println(user.getName() + " borrowed " + book.getTitle());
        } finally {
            lock.unlock();
        }
    }
    public void returnBook(String ISBN, String userID) throws Exception {
        lock.lock();
        try {
            User user = users.stream().filter(u -> u.getUserID().equals(userID)).findFirst().orElseThrow(Exception::new);
            Book book = user.getBorrowedBooks().stream().filter(b -> b.getISBN().equals(ISBN)).findFirst().orElseThrow(Exception::new);
            user.getBorrowedBooks().remove(book);
            System.out.println(user.getName() + " returned " + book.getTitle());
        } finally {
            lock.unlock();
        }
    }
    public void reserveBook(String ISBN, String userID) throws Exception {
        lock.lock();
        try {
            if (books.stream().noneMatch(b -> b.getISBN().equals(ISBN))) throw new Exception();
            if (users.stream().noneMatch(u -> u.getUserID().equals(userID))) throw new Exception();
            System.out.println("Book with ISBN " + ISBN + " has been reserved by user " + userID);
        } finally {
            lock.unlock();
        }
    }
}

public class task2 {
    public static void main(String[] args) {
        LibraryManager library = new LibraryManager();
        library.addBook(new Book("Java Basics", "Author A", "123"));
        library.addUser(new User("Alice", "U1"));
        Thread t1 = new Thread(() -> {
            try { library.borrowBook("123", "U1"); } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
        });
        Thread t2 = new Thread(() -> {
            try { library.returnBook("123", "U1"); } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
        });
        t1.start();
        t2.start();
    }
}
