package edu.brandeis.cosi12b.lattetng.version1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Version1 {
  public static void main(String[] args) {
    Command cmd = new Command();
    while (cmd.notExit()) {
      cmd.execute();
    }
  }
}

class Command {
  static String[] VALIDCMDS = { "exit" };
  String[] tokens;

  boolean notExit() {
    readCommand();
    return (!tokens[0].equals("exit"));
  }

  void readCommand() {
    Scanner console = new Scanner(System.in);
    do {
      System.out.print("> ");
      tokens = console.nextLine().split("\\s");
    } while (!Arrays.asList(VALIDCMDS).contains(tokens[0]));
    console.close();
  }

  void execute() {
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

  public Student(String name, String major) {
    courses = new ArrayList<Course>();
  }

  public void register(Course c) {
    courses.add(c);
  }
}
