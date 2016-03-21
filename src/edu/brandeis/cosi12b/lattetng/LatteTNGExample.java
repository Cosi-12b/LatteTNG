package edu.brandeis.cosi12b.lattetng;

public class LatteTNGExample {
  public static void main(String[] args) {
    University u = new University("Brandeis");
    
    Course c1 = new Course("Cosi12b", "Salas", "Cosi");
    Course c2 = new Course("Cosi11a", "Hickey", "Cosi");
    Student s1 = new Student("Marcus");
    Student s2 = new Student("Jacobs");

    u.addStudent(s1);
    u.addStudent(s2);
    u.addCourse(c1);
    u.addCourse(c2);
    
   
    s1.register(new Registration( c1, 2016, "F"));
    s2.register(new Registration(c2, 2016, "S"));
    
    s1.grade(c1, "A");
    s2.grade(c2, "B");
    
    System.out.println(u.transcriptAll());
  }

}
