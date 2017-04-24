package edu.brandeis.cosi12b.lattetng.version2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Version2 {
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
    } else System.out.println("Invalid command: " + tokens);
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
}

class Course {
  String name;
  String teacherName;
  String departmentName;

  public Course(String name) {
    this.name = name;
  }
}

class Student {
  String name;
  String major;
  ArrayList<Course> courses;

  public Student(String name) {
    courses = new ArrayList<Course>();
  }

  public void register(Course c) {
    courses.add(c);
  }
}
