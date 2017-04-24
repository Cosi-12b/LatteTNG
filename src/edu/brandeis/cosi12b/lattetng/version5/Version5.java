package edu.brandeis.cosi12b.lattetng.version5;

import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;

import org.mapdb.Atomic;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

public class Version5 {
  public static void main(String[] args) {
    Scanner console = new Scanner(System.in);
    DataStore store = new DataStore();
    University univ = new University(store);
    setupShutdownHook(store);

    Command cmd = new Command(univ);
    while (cmd.notExit(console)) {
      cmd.execute();
    }
    console.close();
    store.close();
  }

  public static void setupShutdownHook(DataStore store) {
    Runtime.getRuntime().addShutdownHook(new Thread() {
      public void run() {
        System.out.println("[Closing database and exiting.]");
        store.close();
      }
    });
  }
}

class Command {
  String[] tokens;
  University univ;

  Command(University univ) {
    this.univ = univ;
  }

  boolean notExit(Scanner console) {
    readCommand(console);
    return (!tokens[0].equals("exit"));
  }

  void readCommand(Scanner console) {
    System.out.print("> ");
    tokens = console.nextLine().split("\\s");
  }

  void execute() {
    if (hasTokens(tokens, 3) && tokens[0].equals("add") && tokens[1].equals("student")) {
      univ.addStudent(new Student(tokens[2]));
    } else if (hasTokens(tokens, 3) && tokens[0].equals("add") && tokens[1].equals("course")) {
      univ.addCourse(new Course(tokens[2]));
    } else if (hasTokens(tokens, 6) && tokens[0].equals("register") && tokens[2].equals("for")
        && tokens[4].equals("in")) {
      univ.register(tokens[1], tokens[3], tokens[5]);
    } else if (hasTokens(tokens, 2) && tokens[0].equals("list") && tokens[1].equals("students")) {
      univ.listStudents();
    } else if (hasTokens(tokens, 2) && tokens[0].equals("list") && tokens[1].equals("courses")) {
      univ.listCourses();
    } else if (hasTokens(tokens, 1) && tokens[0].equals("status")) {
      univ.printSummary();
    } else {
      System.out.println("Invalid command: " + tokensToString(tokens));
    }
  }

  boolean hasTokens(String[] tokens, int count) {
    return (tokens.length == count);
  }

  String tokensToString(String[] tokens) {
    String s = "";
    for (int i = 0; i < tokens.length; i++) {
      s = s + (tokens[i] + " ");
    }
    return s;
  }
}

class University {
  DataStore store;

  University(DataStore st) {
    store = st;
    st.open();
  }
  

   void register(String sID, String cID, String termId) {
    Registration reg = new Registration(sID, cID, termId);
    store.addReg(reg);
  }

  void addCourse(Course c) {
    store.putCourse(c);
  }

  void addStudent(Student s) {
    store.putStudent(s);
  }

  void listStudents() {
    store.listStudents();
  }
  
  public void listCourses() {
    store.listCourses();
  }
  
  public void printSummary() {
    store.status();
  }



}

class Course implements Serializable {
  private static final long serialVersionUID = 1L;
  static int count;
  String name;
  String teacherName;
  String departmentName;
  String cID;

  public Course(String name) {
    this.name = name;
    System.out.println("Update: added course \"" + name);
  }
}

class Registration implements Serializable {
  private static final long serialVersionUID = 1L;
  String sID;
  String cID;
  String rID;
  int grade;
  String term;

  public Registration(String sID, String cID, String t) {
    this.sID = sID;
    this.cID = cID;
    term = t;
  }

}

class Student implements Serializable {
  private static final long serialVersionUID = 1L;
  static int count;
  String name;
  String major;
  String sID;

  public Student(String name) {
    count++;
    sID = "s" + count;
    this.name = name;
    System.out.println("Update: added student \"" + name);
  }
}

class DataStore {
  DB db;
  NavigableMap<String, Course> courses;
  NavigableMap<String, Student> students;
  NavigableMap<String, Registration> regs;
  Atomic.Var<Integer> counter;

  void open() {
    db = DBMaker.fileDB("univ2.db").fileMmapEnable().make();
    courses = db.treeMap("courses", Serializer.STRING, Serializer.JAVA).createOrOpen();
    students = db.treeMap("students", Serializer.STRING, Serializer.JAVA).createOrOpen();
    regs = db.treeMap("registrations", Serializer.STRING, Serializer.JAVA).createOrOpen();
    counter = db.atomicVar("counter", Serializer.INTEGER).createOrOpen();
    if (counter.get() == null)
      counter.set(0);
  }

  void close() {
    db.close();
  }

  void status() {
    System.out.printf("Store: Counter: %d - %d students, %d courses, %d registrations\n", counter.get(),
        students.size(), courses.size(), regs.size());
  }

  int getIncr() {
    return counter.getAndSet(counter.get() + 1);
  }

  void putCourse(Course c) {
    c.cID = "c" + getIncr();
    courses.put(c.cID, c);
  }

  Course getCourse(String cid) {
    return (courses.get(cid));
  }

  void putStudent(Student s) {
    s.sID = "s" + getIncr();
    students.put(s.sID, s);
  }

  Student getStudent(String sid) {
    return (students.get(sid));
  }

  void addReg(Registration r) {
    r.rID = "r" + getIncr();
    regs.put(r.rID, r);
  }

  void listStudents() {
    Set<Entry<String, Student>> entries = students.entrySet();
    for (Entry<String, Student> e : entries) {
      System.out.println("Student " + e.getValue().name + "(" + e.getValue().sID + ")");
    }
  }
  
  void listCourses() {
    Set<Entry<String, Course>> entries = courses.entrySet();
    for (Entry<String, Course> e : entries) {
      System.out.println("Course " + e.getValue().name + "(" + e.getValue().cID + ")");
    }
  }

}
