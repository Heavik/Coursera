package coursera.algorithms.strings.week2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BWMatching {

    class FastScanner {

        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements()) {
                tok = new StringTokenizer(in.readLine());
            }
            return tok.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    final int NUMBER_OF_LETTERS = 5;

    int letterToInt(char letter) {
        switch (letter) {
            case '$':
                return 0;
            case 'A':
                return 1;
            case 'C':
                return 2;
            case 'G':
                return 3;
            case 'T':
                return 4;
            default:
                assert (false);
                return -1;
        }
    }

    char[] sort(char[] bwt, int length) {
        char[] output = new char[length];

        int[] count = new int[NUMBER_OF_LETTERS];

        for (int i = 0; i < length; i++) {
            ++count[letterToInt(bwt[i])];
        }

        for (int i = 1; i <= NUMBER_OF_LETTERS - 1; i++) {
            count[i] += count[i - 1];
        }

        for (int i = length - 1; i >= 0; i--) {
            int letterIndex = letterToInt(bwt[i]);
            output[count[letterIndex] - 1] = bwt[i];
            --count[letterIndex];
        }

        return output;
    }

    // Preprocess the Burrows-Wheeler Transform bwt of some text
    // and compute as a result:
    //   * starts - for each character C in bwt, starts[C] is the first position
    //       of this character in the sorted array of
    //       all characters of the text.
    //   * occ_count_before - for each character C in bwt and each position P in bwt,
    //       occ_count_before[C][P] is the number of occurrences of character C in bwt
    //       from position 0 to position P inclusive.
    private void PreprocessBWT(String bwt, Map<Character, Integer> starts, Map<Character, int[]> occ_counts_before) {
        int bwtLength = bwt.length();
        char[] sortedBwt = sort(bwt.toCharArray(), bwtLength);

        for (int i = 0; i < bwtLength; i++) {
            char letter = sortedBwt[i];
            if (!starts.containsKey(letter)) {
                starts.put(letter, i);
            }
        }

        char[] letters = {'$', 'A', 'C', 'T', 'G'};
        Map<Character, Integer> counters = new HashMap<>();

        for (int i = 0; i < letters.length; i++) {
            occ_counts_before.put(letters[i], new int[bwtLength + 1]);
            counters.put(letters[i], 0);
        }

        for (int i = 0; i < bwtLength; i++) {
            char letter = bwt.charAt(i);
            for (char l : letters) {
                int letterCount = counters.get(l);
                if (l == letter) {
                    ++letterCount;
                    counters.put(letter, letterCount);
                }
                int[] positions = occ_counts_before.get(l);
                positions[i + 1] = letterCount;
            }
        }
    }

    // Compute the number of occurrences of string pattern in the text
    // given only Burrows-Wheeler Transform bwt of the text and additional
    // information we get from the preprocessing stage - starts and occ_counts_before.
    int CountOccurrences(String pattern, String bwt, Map<Character, Integer> starts, Map<Character, int[]> occ_counts_before) {
        int bwtLength = bwt.length();
        int top = 0;
        int bottom = bwtLength;
        int currentLetterInPattern = pattern.length() - 1;
        while (top <= bottom) {
            if (currentLetterInPattern >= 0) {
                char symbol = pattern.charAt(currentLetterInPattern);
                currentLetterInPattern--;
                int[] occurences = occ_counts_before.get(symbol);
                if (occurences[top] > 0 || occurences[bottom] > 0) {
                    top = starts.get(symbol) + occurences[top];
                    bottom = starts.get(symbol) + occurences[bottom];
                } else {
                    return 0;
                }
            } else {
                return bottom - top;
            }
        }
        return bottom - top;
    }

    static public void main(String[] args) throws IOException {
        new BWMatching().run();
    }

    public void print(int[] x) {
        for (int a : x) {
            System.out.print(a + " ");
        }
        System.out.println();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String bwt = scanner.next();
        // Start of each character in the sorted list of characters of bwt,
        // see the description in the comment about function PreprocessBWT
        Map<Character, Integer> starts = new HashMap<Character, Integer>();
        // Occurrence counts for each character and each position in bwt,
        // see the description in the comment about function PreprocessBWT
        Map<Character, int[]> occ_counts_before = new HashMap<Character, int[]>();
        // Preprocess the BWT once to get starts and occ_count_before.
        // For each pattern, we will then use these precomputed values and
        // spend only O(|pattern|) to find all occurrences of the pattern
        // in the text instead of O(|pattern| + |text|).
        PreprocessBWT(bwt, starts, occ_counts_before);
        int patternCount = scanner.nextInt();
        String[] patterns = new String[patternCount];
        int[] result = new int[patternCount];
        for (int i = 0; i < patternCount; ++i) {
            patterns[i] = scanner.next();
            result[i] = CountOccurrences(patterns[i], bwt, starts, occ_counts_before);
        }
        print(result);
    }
}