package edu.brandeis.cosi12b.lattetng;
import java.util.ArrayList;
import java.util.List;

class Student {
  private String name;
  List<Registration> registrations;
  
  public Student(String name) {
    this.name = name;
    registrations = new ArrayList<Registration>();
  }
  
  public String transcript() {
   StringBuilder result = new StringBuilder();
   appendNameInfo(result);
   appendCourseInfo(result);
   return result.toString();
  }
  
  private void appendNameInfo(StringBuilder sb) {
    sb.append("\n= = = = = = = = = = = = = = = = = \n");
    sb.append("TRANSCRIPT FOR STUDENT: " + name +"\n");
    sb.append("= = = = = = = = = = = = = = = = = \n");
  }
  
  private void appendCourseInfo(StringBuilder sb) {
    sb.append("Courses\n");
    for (Registration r: registrations) {
      sb.append(String.format("%10s  %5s %10s %2s",
                r.getCourseName(), r.getDepartmentName(),
                r.getTeacherName(), r.getGrade()));
    }
  }

  public void register(Registration r) {
    registrations.add(r);
  }
  
  public boolean grade(Course course, String grade) {
    for (Registration r: registrations) {
      if (r.getCourse() == course) {
          r.setGrade(grade);
          return true;
      }
    }
    return false;
  }
}
