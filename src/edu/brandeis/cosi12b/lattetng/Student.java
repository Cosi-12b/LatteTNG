package edu.brandeis.cosi12b.lattetng;

import java.util.ArrayList;
import java.util.List;

class Student {
  private String name;
  List<Course> courses;
  
  public Student(String name) {
    courses = new ArrayList<Course>();
    this.name = name;
  }
  
  public void register(Course course) {
    courses.add(course);
  }
  
  public String transcript() {
   StringBuilder result = new StringBuilder();
   appendNameInfo(result);
   appendCourseInfo(result);
   return result.toString();
  }
  
  private void appendNameInfo(StringBuilder sb) {
    sb.append("\n= = = = = = = = = = = = = = = = \n");
    sb.append("Transcript for student: " + name);
    sb.append("\n= = = = = = = = = = = = = = = = \n");
  }
  
  private void appendCourseInfo(StringBuilder sb) { 
    for (Course c: courses) {
      sb.append(c.getName()+"  "+ c.getDepartmentName() + " " + c.getTeacherName());
    }
  }
}
