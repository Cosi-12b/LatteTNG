package edu.brandeis.cosi12b.lattetng.version9;

import java.io.Serializable;

class Registration implements Serializable {
  private static final long serialVersionUID = 1L;
  String sID;
  String cID;
  String rID;
  int grade;
  String term;

  public Registration(String sID, String cID) {
    this.sID = sID;
    this.cID = cID;
  }
}
