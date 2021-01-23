package BessiePaint;

import java.io.*;
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
            // The desire color for the fence.
            char[] desire = sc.next().toCharArray();
            /*
             * The dp array that starts from left end, in which leftCount[i] stands for the minimum strokes needed to paint the fence from 0 to i.
             */
            int[] leftCount = new int[n + 1];
            /*
             * The dp array that starts from right end, in which rightCount[i] stands for the minimum strokes needed to paint the fence from n to i.
             */
            int[] rightCount = new int[n + 1];


            MonotonicStack stack = new MonotonicStack();
            // Scanning the fence from left to right
            for (int i = 0; i < n; i++) {
                leftCount[i + 1] = leftCount[i] + stack.push(desire[i]);
            }
            stack.clear();
            // Scanning the fence from right to left.
            for (int i = n - 1; i >= 0; i--) {
                rightCount[i] = rightCount[i + 1] + stack.push(desire[i]);
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

    /**
     * A simple array-based monotonic stack with a sentinel -1.
     */
    private static class MonotonicStack {
        int[] stack = new int[COUNT + 1];
        int sp = 0;
        public MonotonicStack(){
            stack[0] = -1;
        }
        public int peek(){
            return stack[sp];
        }

        public void push(int ordinal) {
            stack[++sp] = ordinal;
        }
        // Method overload.
        public int push(char color) {
            // Covert the char to int.
            int ordinal = color - 'A';
            // Here temp can be any integer as long as temp < 0 and temp >= 26. Set it to -1 Just for initialization.
            int temp = -1;
            // Keep the elements in the stack orderly(monotone increasing), which makes it a monotonic stack.
            while (ordinal <= peek()){
                temp = pop();
            }
            push(ordinal);
            // If ordinal color is already in the stack, we don' t need to paint it again.
            return temp == ordinal? 0:1;
        }

        public int pop() {
            int i = stack[sp--];
            return i;
        }
        public void clear(){
            sp = 0;
        }
    }
}
