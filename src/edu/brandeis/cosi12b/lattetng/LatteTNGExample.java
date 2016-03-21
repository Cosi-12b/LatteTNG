package edu.brandeis.cosi12b.lattetng;

public class LatteTNGExample {
  public static void main(String[] args) {
    Course c1 = new Course("Cosi12b", "Salas", "Cosi");
    Student s1 = new Student("Jacobs");
    s1.register(c1);
    
    System.out.println(s1.transcript());
  }

}
