package edu.brandeis.cosi12b.lattetng.version7;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Command {
  List<String> tokens;
  University univ;

  Command(University univ) {
    this.univ = univ;
    tokens = new ArrayList<String>();
  }

  boolean notExit(Scanner console) {
    readCommand(console);
    boolean exit = (tokens != null && tokens.size() > 0 && tokens.get(0).equals("exit"));
    return (!exit);
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
      } else if (hasTokens(tokens, 2) && tokens.get(0).equals("list") && tokens.get(1).equals("registrations")) {
        univ.listRegistrations();
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

