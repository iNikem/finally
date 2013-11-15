package eu.nikem;

public class Main {

  public static void main(String[] args) {
    try {
      Class.forName("android.app.Application");
    } catch (ClassNotFoundException e) {
      //Ignore
    }

    /* What I want to achieve
    try {
      Track.before();
      try {
        Class.forName("android.app.Application");
        Track.after();
      } finally {
        Track.thrown();
      }
    } catch (ClassNotFoundException e) {
      //Ignore
    }
     */
  }
}
