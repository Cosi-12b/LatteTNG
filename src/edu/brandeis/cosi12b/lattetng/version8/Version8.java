package edu.brandeis.cosi12b.lattetng.version8;

import java.util.*;

public class Version8 {
  public static void main(String[] args) {
    Scanner console = new Scanner(System.in);
    University univ = new University();
    setupShutdownHook(univ);

    Command cmd = new Command(univ);
    while (cmd.notExit(console)) {
      if (!cmd.execute())
        System.out.println("Invalid command!");
    }
    console.close();
    univ.close();
  }

  public static void setupShutdownHook(University univ) {
    Runtime.getRuntime().addShutdownHook(new Thread() {
      public void run() {
        System.out.println("[Closing database and exiting.]");
        univ.close();
      }
    });
  }
}




