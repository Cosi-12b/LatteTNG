package edu.brandeis.cosi12b.lattetng;
import java.util.ArrayList;
import java.util.List;

class Student {
  private String name;
  private String major;
  private List<Registration> registrations;
  
  public Student(String name, String major) {
    setName(name);
    setMajor(major);
    registrations = new ArrayList<Registration>();
  }
  
  public StringBuilder transcript() {
   StringBuilder result = new StringBuilder();
   appendNameInfo(result);
   appendCourseInfo(result);
   return result;
  }
  
  private void appendNameInfo(StringBuilder sb) {
    sb.append("\n= = = = = = = = = = = = = = = = = = = = \n");
    sb.append("TRANSCRIPT FOR STUDENT: " + getName() +" ["+getMajor()+"]\n");
    sb.append("= = = = = = = = = = = = = = = = = = = = = \n");
  }
  
  private void appendCourseInfo(StringBuilder sb) {
    sb.append("Courses\n");
    for (Registration r: registrations) {
      sb.append(String.format("%10s  %5s %10s %2s%n",
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
  
  public String getMajor() { return major; }
  public void setMajor(String major) {  this.major = major; }
  public String getName() { return name; }
  public void setName(String name) {  this.name = name; }
  
}
