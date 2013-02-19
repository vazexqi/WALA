package ssa;

// Figure 3.6 of The Compiler Design Handbook
public class SSA2 {
  public static void start(int a) {
    int lsr = 1;
    int rsr = a;
    int sr = (lsr + rsr) / 2;

    do {
      int t = sr * sr;
      if (t > a)
        rsr = sr;
      else {
        if (t < a) {
          lsr = sr;
        } else {
          lsr = sr;
          rsr = sr;
        }
      }
      sr = (lsr + rsr) / 2;
    } while (lsr != rsr);

    System.out.println(sr);
  }
}
