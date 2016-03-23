package edu.brandeis.cosi12b.lattetng;

import java.util.ArrayList;
import java.util.List;

public class University {
  private String name;
  private List<Student>students;
  private List<Course>courses;
  private List<Registration>registrations;

  
  public University(String name) {
    this.name = name;
    students = new ArrayList<Student>();
    courses = new ArrayList<Course>();
    registrations = new ArrayList<Registration>();
  }
  
  public void addStudent(Student s) {
    students.add(s);
  }
  
  public void addCourse(Course c) {
    courses.add(c);
  }
  
  public void register(Student student, Course course, int year, String semester) {
    Registration reg = new Registration(course, student, year, semester);
    student.register(reg);
    registrations.add(reg);
  }

  public String getName() { return name; }

  public StringBuilder transcriptAll() {
    StringBuilder sb = new StringBuilder();
    for (Student s: students) {
      sb.append(s.transcript());
    }
    return sb;
  }
}
