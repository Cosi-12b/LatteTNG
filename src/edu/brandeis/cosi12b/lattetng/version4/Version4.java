package edu.brandeis.cosi12b.lattetng.version4;

import java.util.*;

public class Version4 {
  public static void main(String[] args) {
    Scanner console = new Scanner(System.in);
    University univ = new University();
    Command cmd = new Command(univ);
    while (cmd.notExit(console)) {
      cmd.execute();
    }
    console.close();
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
    } else if (hasTokens(tokens, 6) && tokens[0].equals("register") && tokens[2].equals("for") && tokens[4].equals("in")) {
      univ.register(tokens[1], tokens[3], tokens[5]);
    } else {
      System.out.println("Invalid command: " + tokensToString(tokens));
    }
  }

  boolean hasTokens(String[] tokens, int count) {
    return (tokens.length == count);
   }
  
  String tokensToString(String[] tokens) {
    String s = "";
    for (int i=0; i<tokens.length; i++) {
      s =  s + (tokens[i] + " ");
    }
    return s;
  }
}

class University {
  Map<String, Course> courses = new HashMap<String, Course>();
  Map<String, Student> students = new HashMap<String, Student>();
  List<Registration> regs = new ArrayList<Registration>();

  void register(String sId, String cId, String termId) {
    Registration reg = new Registration(students.get(sId), courses.get(cId), termId);
    regs.add(reg);
  }
  
  void addCourse(Course c) {
    courses.put(c.name, c);
  }

  void addStudent(Student s) {
    students.put(s.name, s);
  }
}

class Course {
  static int count;
  String name;
  String teacherName;
  String departmentName;
  String courseNum;

  public Course(String name) {
    this.name = name;
    count++;
    courseNum = "c" + count;
    System.out.println("Update: added course \"" + name + "\" (#" + courseNum + ")");
  }
}

class Registration {
  Student student;
  Course course;
  int grade;
  String term;

  public Registration(Student s, Course c, String t) {
    student = s;
    course = c;
    term = t;
    System.out.println("Update: Student " + s.name);
  }
}

class Student {
  static int count;
  String name;
  String major;
  String studentNum;

  public Student(String name) {
    count++;
    studentNum = "s" + count;
    System.out.println("Update: added student \"" + name + "\" (# " + studentNum + ")");
  }
}
