package edu.brandeis.cosi12b.lattetng;

import java.util.ArrayList;
import java.util.List;

public class University {
  private String name;
  private List<Student>students;
  private List<Course>courses;

  
  public University(String name) {
    this.name = name;
    students = new ArrayList<Student>();
    courses = new ArrayList<Course>();
  }
  
  public void addStudent(Student s) {
    students.add(s);
  }
  
  public void addCourse(Course c) {
    courses.add(c);
  }

  public String getName() { return name; }

  public StringBuilder transcriptAll() {
    StringBuilder sb = new StringBuilder();
    for (Student s: students) {
      sb.append(s.transcript());
    }
    // TODO Auto-generated method stub
    return sb;
  }
}
