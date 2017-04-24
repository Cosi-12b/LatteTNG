package edu.brandeis.cosi12b.lattetng.version9;
import java.io.Serializable;

class Course implements Serializable {
  private static final long serialVersionUID = 1L;
  String cID;
  String name;
  String teacherName;
  String departmentName;

  public Course(String name) {
    this.name = name;
    System.out.println("Update: added course \"" + name);
  }
}
