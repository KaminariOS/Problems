package BessiePaint;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;


/**
 * @author kaminari
 */
public class BessiePaint {
    private static final int COUNT = 26;
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNext()){
            int n = sc.nextInt();
            int q = sc.nextInt();
            char[] desire = sc.next().toCharArray();
            int[] leftCount = new int[n + 1];
            int[] rightCount = new int[n + 1];

            Istack istack = new Istack();
            for (int i = 0; i < n; i++) {
                int ordinal = desire[i] - 'A';
                if (ordinal > istack.peek()){
                    leftCount[i + 1] = leftCount[i] + 1;
                    istack.push(ordinal);
                    continue;
                }
                int temp = -1;
                while (ordinal <= istack.peek()){
                    temp = istack.pop();
                }
                istack.push(ordinal);

                leftCount[i + 1] = leftCount[i] + (ordinal == temp? 0: 1);
            }
            istack.clear();
            for (int i = n - 1; i >= 0; i--) {
                int ordinal = desire[i] - 'A';
                if (ordinal > istack.peek()){
                    rightCount[i] = rightCount[i + 1] + 1;
                    istack.push(ordinal);
                    continue;
                }
                int temp = -1;
                while (ordinal <= istack.peek()){
                    temp = istack.pop();
                }
                istack.push(ordinal);
                rightCount[i] = rightCount[i + 1] + (ordinal == temp? 0: 1);
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
        int leftSum = leftCount[lo - 1];

        int rightSum = rightCount[hi];

        return leftSum + rightSum;
    }

    private static class Istack{
        int[] stack = new int[COUNT + 1];
        int sp = 0;
        public Istack(){
            stack[0] = -1;
        }
        public int peek(){
            return stack[sp];
        }

        public void push(int ordinal) {
            sp++;
            stack[sp] = ordinal;
        }

        public int pop() {
            int i = stack[sp];
            sp--;
            return i;
        }
        public void clear(){
            sp = 0;
        }
    }
}
