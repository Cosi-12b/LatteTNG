package edu.brandeis.cosi12b.lattetng.version8;

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

  boolean execute() {
    try {
      if (hasTokens(tokens, 3) && tokens.get(0).equals("add"))
          return addCommands(tokens.get(1), tokens.subList(1, tokens.size()));
      if (hasTokens(tokens, 2) && tokens.get(0).equals("list"))
          return listCommands(tokens.get(1), tokens.subList(1, tokens.size()));
      if (hasTokens(tokens, 3) && tokens.get(0).equals("register"))
        return registerCommands(tokens.get(1), tokens.get(2));
      if (hasTokens(tokens, 1))
         return singleCommands(tokens.get(0));
      return false;
      } catch (RuntimeException e) {
        System.err.println(e.getMessage());
        return false;
      }
  }
  
  boolean addCommands(String verb, List<String> rest) {
    if (checkKeyword(verb, "course") && hasTokens(rest, 1)) {
      univ.addCourse(new Course(rest.get(0)));
      return true;
  } else if (checkKeyword(verb, "student") && hasTokens(rest, 1)) {
    univ.addStudent(new Student(rest.get(0)));
    return true;
    } else return false;
  }
  
  boolean listCommands(String verb, List<String> rest) {
    if (checkKeyword(verb, "course") && hasTokens(rest, 0)) {
      univ.listCourses();
      return true;
  } else if (checkKeyword(verb, "student") && hasTokens(rest, 0)) {
    univ.listStudents();
    return true;
  } else if (checkKeyword(verb, "registration") && hasTokens(rest, 0)) {
    univ.listRegistrations();
    return true;
    } else return false;
  }
  
  boolean registerCommands(String student, String course) {
    univ.register(student, course);
    return true;
  }
  
  boolean singleCommands(String verb) {
    if (checkKeyword(verb, "status")) {
      univ.printSummary();
      return true;
    } else if (checkKeyword(verb, "help")) {
      printHelp();
      return true;
    } else return false;
  }
  
  private void printHelp() {
    System.out.println("Commands: add, register, list, help, status.");
  }

    boolean checkKeyword(String word, String check) {
      return (word.equals(check) || String.valueOf(check.charAt(0)).equals(word));
    }
    boolean hasTokens(List<String> tokens, int count) {
    return (tokens.size() == count);
  }

  String tokensToString(List<String> tokens) {
    return tokens.toString();
  }
}

