package edu.brandeis.cosi12b.lattetng.version8;

import java.io.Serializable;

class Student implements Serializable {
  private static final long serialVersionUID = 1L;
  String name;
  String major;
  String sID;

  public Student(String name) {
    this.name = name;
    System.out.println("Created student: \"" + name + "\"");
  }
}
