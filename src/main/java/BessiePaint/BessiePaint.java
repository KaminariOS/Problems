package BessiePaint;

import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * @author kaminari
 */
public class BessiePaint {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            int n = sc.nextInt();
            int q = sc.nextInt();
            char[] desire = sc.next().toCharArray();
            int[] leftCount = new int[n + 1];
            int[] rightCount = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                leftCount[i] = leftCount[i - 1] | (1 << (desire[i - 1] - 'A'));
            }
            for (int i = n - 1; i >= 0; i--){
                rightCount[i] = rightCount[i + 1] | (1 << (desire[i] - 'A'));
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new PrintWriter(System.out));
            for (int i = 0; i < q; i++) {
                int lo = sc.nextInt();
                int hi = sc.nextInt();
                int strokes = paintNew(leftCount, rightCount, lo, hi);
                bufferedWriter.write(String.valueOf(strokes));
                bufferedWriter.newLine();

            }
            bufferedWriter.flush();
        }
    }

    private static int paintNew(int[] leftCount, int[] rightCount, int lo, int hi) {
        int left = leftCount[lo - 1];
        int leftSum = 0;
        while (left != 0){
            leftSum += left & 1;
            left >>= 1;
        }
        int right = rightCount[hi];
        int rightSum = 0;
        while (right != 0){
            rightSum += right & 1;
            right >>= 1;
        }
        return leftSum + rightSum;
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
