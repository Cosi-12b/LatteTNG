package edu.brandeis.cosi12b.lattetng;

import com.github.javafaker.Faker;


public class LatteTNGExample {
  public static void main(String[] args) {
    Command cmd = new Command();
    System.out.println("Welcome to LatteTNG");
    readDatabase();
    while (cmd.more()) {
      cmd.execute();
    }
    writeDatabase();
    System.out.println("Exiting...");
  }
 
  public static void readDatabase() {
    System.out.println("... Pretend to read database");
  }
  
  public static void writeDatabase() {
    System.out.println("... Pretend to write database");
  }
  
  public static void simpleExample() {
    University u = new University("Brandeis");
    
    Course c1 = new Course("Cosi12b", "Salas", "Cosi");
    Course c2 = new Course("Cosi11a", "Hickey", "Cosi");
    Student s1 = new Student("Marcus", "Cosi");
    Student s2 = new Student("Jacobs", "Cosi");

    u.addStudent(s1);
    u.addStudent(s2);
    u.addCourse(c1);
    u.addCourse(c2);
    
    u.register(s1, c1, 2016, "FALL");
    u.register(s2, c2, 2016, "FALL");
    
    s1.grade(c1, "A");
    s2.grade(c2, "B");
    
    System.out.println(u.transcriptAll());
  }
  
  public static void funExample() {
    University u = new University("Trumpet University");

    Course c1 = new Course("Philosophy -1", "Smoots", "PHIL");
    Course c2 = new Course("Golf Piano", "Lousy", "MUSIC");
    Course c3 = new Course("Curaçao in History", "Abram", "HIST");
    u.addCourse(c1);
    u.addCourse(c2);
    u.addCourse(c3);
    
    Faker faker = new Faker();
    for (int i=0; i<15; i++) {
      Student stud1 = new Student(faker.name().firstName() + " " + faker.name().lastName(), "GENL");
      u.addStudent(stud1);
      u.register(stud1,  c1,  2016,  "FALL");
      u.register(stud1,  c2,  2016,  "FALL");
      u.register(stud1,  c3,  2016,  "FALL");
    }
    
    System.out.println(u.transcriptAll());
  }
}

class Command {
  boolean more() {
    System.out.println("... Pretend to read a command line");
    return false;
  }
  void execute() {
    System.out.println("... Pretend to execute a command");
  }
 
}