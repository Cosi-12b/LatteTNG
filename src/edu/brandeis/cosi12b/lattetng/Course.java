package edu.brandeis.cosi12b.lattetng;

public class Course {
  private String name;
  private String teacherName;
  private String departmentName;
  
  public Course(String name, String teacherName, String departmentName) {
    this.name = name;
    this.teacherName = teacherName;
    this.departmentName = departmentName;
  }
  
  public String getName() { return name; }
<<<<<<< HEAD
  public String getTeacherName() { return "Prof. " + teacherName; }
=======
  public String getTeacherName() { return teacherName; }
>>>>>>> master
  public String getDepartmentName() { return departmentName; }

}
