package edu.brandeis.cosi12b.lattetng.version9;

import java.io.Serializable;

class Student implements Serializable {
  private static final long serialVersionUID = 1L;
  String name;
  String major;
  String sID;
  String email;

  public Student(String name) {
    this.name = name;
    System.out.println("Created student: \"" + name + "\"");
  }

  public Student(String name, String email) {
    this.name = name;
    this.email = email;
  }
}
