package ssa;

// Figure 3.4 of The Compiler Design Handbook
public class SSA1 {
  public static void start(int a, int b, int c) {
    int max;

    if (a > b) {
      if (a > c) {
        max = a;
      } else {
        max = c;
      }
    } else {
      if (b > c) {
        max = b;
      } else {
        max = c;
      }
    }

    id(max);
  }

  private static void id(int max) {
  }
}
