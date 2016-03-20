package edu.brandeis.cosi12b.lattetng;
import java.util.List;

class Student {
  private String name;
  List<Course> courses;
  
  public Student() {
    courses = new ArrayList<Course>();
  }
  
  public String transcript() {
   StringBuilder result = new StringBuilder();
   appendNameInfo(result);
   appendCourseInfo(result);
   return result.toString();
  }
  
  private void appendNameInfo(StringBuilder sb) {
    sb.append(name);
  }
  
  private void appendCourseInfo(StringBuilder sb) { 
  }
}
