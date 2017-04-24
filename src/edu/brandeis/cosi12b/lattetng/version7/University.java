package edu.brandeis.cosi12b.lattetng.version7;

import java.util.NavigableMap;
import java.util.Set;
import java.util.Map.Entry;

import org.mapdb.Atomic;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

class University {
  DB db;
  NavigableMap<String, Course> courses;
  NavigableMap<String, Student> students;
  NavigableMap<String, Registration> regs;
  Atomic.Var<Integer> counter;

  // add db.<String, Course>treeMap() later
  University() {
//    db = DBMaker.fileDB("univ.db").checksumHeaderBypass().fileMmapEnable().make();
    db = DBMaker.fileDB("univ.db").closeOnJvmShutdown().fileMmapEnable().make();
    courses = db.treeMap("courses", Serializer.STRING, Serializer.ELSA).createOrOpen();
    students = db.treeMap("students", Serializer.STRING, Serializer.ELSA).createOrOpen();
    regs = db.treeMap("registrations", Serializer.STRING, Serializer.ELSA).createOrOpen();
    counter = db.atomicVar("counter", Serializer.INTEGER).createOrOpen();
    if (counter.get() == null)
      counter.set(0);
  }

  void close() {
    db.close();
  }

  int getIncr() {
    return counter.getAndSet(counter.get() + 1);
  }

  void register(String sID, String cID, String termId) {
    if (! (courseExist(cID) && studentExist(sID))) throw new RuntimeException("Unknown course or student");
    Registration reg = new Registration(sID, cID, termId);
    reg.rID = "r" + getIncr();
    regs.put(reg.rID, reg);
  }
  
  boolean courseExist(String cID) {
    return (courses.containsKey(cID));
  }

  boolean studentExist(String sID) {
    return (students.containsKey(sID));
  }
  
  void addCourse(Course c) {
    c.cID = "c" + getIncr();
    courses.put(c.cID, c);
  }

  void addStudent(Student s) {
    s.sID = "s" + getIncr();
    students.put(s.sID, s);
  }

  void listStudents() {
    Set<Entry<String, Student>> entries = students.entrySet();
    for (Entry<String, Student> e : entries) {
      System.out.println("Student \"" + e.getValue().name + "\" (" + e.getValue().sID + ")");
      listStudentRegs(e.getValue().sID);
    }
  }
  
  void listStudentRegs(String sID) {
    for (Entry<String, Registration> s: regs.entrySet()) {
      if (s.getValue().sID.equals(sID)) {
        System.out.println("  registered in: \"" + courses.get(s.getValue().cID).name+ "\"");
      }
    }
  }

  public void listCourses() {
    Set<Entry<String, Course>> entries = courses.entrySet();
    for (Entry<String, Course> e : entries) {
      System.out.println("Course \"" + e.getValue().name + "\" (" + e.getValue().cID + ")");
    }
  }
  
  public void listRegistrations() {
    Set<Entry<String, Registration>> entries = regs.entrySet();
    for (Entry<String, Registration> e : entries) {
      System.out.printf("Reg: Student: %s is taking %s (%s)\n", e.getValue().sID, e.getValue().cID, e.getValue().rID);
    }
  }

  public void printSummary() {
    System.out.printf("Store: Counter: %d - %d students, %d courses, %d registrations\n", counter.get(),
        students.size(), courses.size(), regs.size());
  }
}
