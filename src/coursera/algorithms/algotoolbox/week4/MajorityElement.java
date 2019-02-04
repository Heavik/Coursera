/* Majority Element
 * Description: The goal in this code problem is to check whether an input sequence contains a majority element.
 *
 * Input: The first line contains an integer 𝑛, the next one contains a sequence of 𝑛 non-negative integers 𝑎0, 𝑎1,...,𝑎[𝑛−1].
 *
 * Output: Output 1 if the sequence contains an element that appears strictly more than 𝑛/2 times, and 0 otherwise.
 *
 * Constraints: 1 ≤ 𝑛 ≤ 10^5; 0 ≤ 𝑎𝑖 ≤ 10^9 for all 0 ≤ 𝑖 < 𝑛.
 */
package coursera.algorithms.algotoolbox.week4;

import java.util.*;
import java.io.*;

public class MajorityElement {

    private static int getMajorityElement(int[] a, int left, int right) {
        if (left == right) {
            return -1;
        }
        if (left + 1 == right) {
            return a[left];
        }

        int mid = (right + left) / 2;
        int leftMajority = getMajorityElement(a, left, mid);
        int rightMajority = getMajorityElement(a, mid + 1, right);

        int leftCount = 0;
        int rightCount = 0;

        int upperBound = right >= a.length ? a.length - 1 : right;

        for (int i = left; i <= upperBound; ++i) {
            if (leftMajority != -1 && leftMajority == a[i]) {
                ++leftCount;
            }
            if (rightMajority != -1 && rightMajority == a[i]) {
                ++rightCount;
            }
        }

        int majorityCriteria = (right - left) / 2;

        if (leftCount > majorityCriteria) {
            return leftMajority;
        }

        if (rightCount > majorityCriteria) {
            return rightMajority;
        }

        return -1;
    }

    public static void main(String[] args) {
        FastScanner scanner = new FastScanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        if (getMajorityElement(a, 0, a.length) != -1) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }
    }

    static class FastScanner {

        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream stream) {
            try {
                br = new BufferedReader(new InputStreamReader(stream));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }
}
