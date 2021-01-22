package BessiePaint;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * @author kaminari
 */
public class BessiePaint {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            int n = sc.nextInt();
            int q = sc.nextInt();
            char[] desire = sc.next().toCharArray();
            for (int i = 0; i < q; i++) {
                int lo = sc.nextInt();
                int hi = sc.nextInt();
                int strokes = paint(desire, lo, hi);
                System.out.println(strokes);
            }
        }
    }

    private static int paint(char[] desire, int lo, int hi) {
        int n = desire.length;
        Set<Character> left = new HashSet<>(lo - 1);
        Set<Character> right = new HashSet<>(hi - lo + 1);
        for (int i = 0; i < lo - 1; i++) {
            left.add(desire[i]);
        }
        for (int i = hi; i < n; i++) {
            right.add(desire[i]);
        }
        return left.size() + right.size();
    }
}
