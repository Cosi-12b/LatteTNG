package edu.brandeis.cosi12b.lattetng.version3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Version3 {
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
  static String[] VALIDCMDS = { "exit", "add" };
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
    do {
      System.out.print("> ");
      tokens = console.nextLine().split("\\s");
    } while (!Arrays.asList(VALIDCMDS).contains(tokens[0]));
  }

  void execute() {
    if (tokens[0].equals("add") && tokens[1].equals("student")){
      univ.addStudent(new Student(tokens[2]));
    } else if (tokens[0].equals("add") && tokens[1].equals("course")) {
      univ.addCourse(new Course(tokens[2]));
    } else if (tokens[0].equals("register") && 
              tokens[2].equals("for") && 
              tokens[4].equals("in")) {
      univ.register(tokens[1], tokens[3], tokens[5]);
    } else
      System.out.println("Invalid command: " + tokens);
    }
}

class University {
  ArrayList<Course> courses = new ArrayList<Course>();
  ArrayList<Student> students = new ArrayList<Student>();
  
  void addCourse(Course c) {
    courses.add(c);
  }
  
  void addStudent(Student s) {
    students.add(s);
  }
  
  void register(String student, String course, String term) {
    // ??
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
    courseNum = "c"+count;
    System.out.println("Update: added course \"" + name + "\" (#" + courseNum + ")");
  }
}

class Registration {
  Student s;
  Course c;
  int grade;
}

class Student {
  static int count;
  String name;
  String major;
  String studentNum;
  ArrayList<Course> courses;

  public Student(String name) {
    courses = new ArrayList<Course>();
    count++;
    studentNum = "s" + count;
    System.out.println("Update: added student \"" + name + "\" (# " + studentNum + ")");
  }
}
