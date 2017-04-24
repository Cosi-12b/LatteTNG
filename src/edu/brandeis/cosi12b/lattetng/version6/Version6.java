package edu.brandeis.cosi12b.lattetng.version6;

import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mapdb.Atomic;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

public class Version6 {
  public static void main(String[] args) {
    Scanner console = new Scanner(System.in);
    University univ = new University();
    setupShutdownHook(univ);

    Command cmd = new Command(univ);
    while (cmd.notExit(console)) {
      cmd.execute();
    }
    console.close();
    univ.close();
  }

  public static void setupShutdownHook(University univ) {
    Runtime.getRuntime().addShutdownHook(new Thread() {
      public void run() {
        System.out.println("[Closing database and exiting.]");
        univ.close();
      }
    });
  }
}

class Command {
  List<String> tokens;
  University univ;

  Command(University univ) {
    this.univ = univ;
    tokens = new ArrayList<String>();
  }

  boolean notExit(Scanner console) {
    readCommand(console);
    return (tokens == null || !tokens.get(0).equals("exit"));
  }

  void readCommand(Scanner console) {
    System.out.print("> ");
    Matcher m = Pattern.compile("(?<=\")\\w[\\w\\s]*(?=\")|\\w+").matcher(console.nextLine());
    tokens.clear();
    while (m.find()) {
      tokens.add(m.group());
    }
  }

  void execute() {
    try {
      if (hasTokens(tokens, 3) && tokens.get(0).equals("add") && tokens.get(1).equals("student")) {
        univ.addStudent(new Student(tokens.get(2)));
      } else if (hasTokens(tokens, 3) && tokens.get(0).equals("add") && tokens.get(1).equals("course")) {
        univ.addCourse(new Course(tokens.get(2)));
      } else if (hasTokens(tokens, 6) && tokens.get(0).equals("register") && tokens.get(2).equals("for")
          && tokens.get(4).equals("in")) {
            univ.register(tokens.get(1), tokens.get(3), tokens.get(5));
      } else if (hasTokens(tokens, 2) && tokens.get(0).equals("list") && tokens.get(1).equals("students")) {
        univ.listStudents();
      } else if (hasTokens(tokens, 2) && tokens.get(0).equals("list") && tokens.get(1).equals("courses")) {
        univ.listCourses();
      } else if (hasTokens(tokens, 1) && tokens.get(0).equals("status")) {
        univ.printSummary();
      } else {
        System.out.println("Invalid command: " + tokensToString(tokens));
      }
    } catch (RuntimeException e) {
      System.err.println(e.getMessage());
    }
  }
  
    boolean hasTokens(List<String> tokens, int count) {
    return (tokens.size() == count);
  }

  String tokensToString(List<String> tokens) {
    return tokens.toString();
  }
}

class Course implements Serializable {
  private static final long serialVersionUID = 1L;
  String cID;
  String name;
  String teacherName;
  String departmentName;

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
  String name;
  String major;
  String sID;

  public Student(String name) {
    this.name = name;
    System.out.println("Created student: \"" + name + "\"");
  }
}

class University {
  DB db;
  NavigableMap<String, Course> courses;
  NavigableMap<String, Student> students;
  NavigableMap<String, Registration> regs;
  Atomic.Var<Integer> counter;

  // add db.<String, Course>treeMap().. later.
  University() {
    db = DBMaker.fileDB("univ4.db").fileMmapEnable().make();
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

  int getIncr() {
    return counter.getAndSet(counter.get() + 1);
  }

  void register(String sID, String cID, String termId) {
    if (! (courseExist(cID) && studentExist(sID))) throw new RuntimeException("Unknown course or student");
    Registration reg = new Registration(sID, cID, termId);
    reg.rID = "r" + getIncr();
    regs.put(reg.rID, reg);
  }
  
  boolean courseExist(String cID) {
    return (courses.containsKey(cID));
  }

  boolean studentExist(String sID) {
    return (students.containsKey(sID));
  }


  
  void addCourse(Course c) {
    c.cID = "c" + getIncr();
    courses.put(c.cID, c);
  }

  void addStudent(Student s) {
    s.sID = "s" + getIncr();
    students.put(s.sID, s);
  }

  void listStudents() {
    Set<Entry<String, Student>> entries = students.entrySet();
    for (Entry<String, Student> e : entries) {
      System.out.println("Student \"" + e.getValue().name + "\" (" + e.getValue().sID + ")");
    }
  }

  public void listCourses() {
    Set<Entry<String, Course>> entries = courses.entrySet();
    for (Entry<String, Course> e : entries) {
      System.out.println("Course \"" + e.getValue().name + "\" (" + e.getValue().cID + ")");
    }
  }

  public void printSummary() {
    System.out.printf("Store: Counter: %d - %d students, %d courses, %d registrations\n", counter.get(),
        students.size(), courses.size(), regs.size());
  }
}
