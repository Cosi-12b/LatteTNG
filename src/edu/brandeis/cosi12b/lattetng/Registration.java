package edu.brandeis.cosi12b.lattetng;

public class Registration {
  private Course course;
  private Student student;
  private String grade;
  int year;
  private String semester;
  
  public Registration(Course course, Student student, int year, String semester) {
    this.course = course;
    this.student = student;
    this.year = year;
    this.semester = semester;
    grade = "N/A";
  }
  
  public void setGrade(String grade) {
    this.grade = grade;
  }
  
  public String getCourseName() { return course.getName(); }
  public String getDepartmentName() { return course.getDepartmentName(); }
  public String getTeacherName() { return course.getTeacherName(); }
  public String getStudentName() { return student.getName(); }

  public String getGrade() { return grade; }
  public Course getCourse() { return course; }
  public int getYear() { return year; }
  public String getSemester() { return semester; }
  
}
