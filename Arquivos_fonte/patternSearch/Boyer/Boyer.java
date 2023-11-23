package patternSearch.Boyer;

import java.io.*;

public class Boyer {

    private byte[] pattern;
    private byte[] input;
    private static final String dbPath = "./Database/player_db.db";

    public Boyer(byte[] pattern) throws Exception {
        RandomAccessFile raf = new RandomAccessFile(dbPath, "rw");
        byte[] input = new byte[(int) raf.length()];
        raf.read(input);

        this.pattern = pattern;
        this.input = input;

        raf.close();
    }

    public int boyerMooreSearch() {
        int m = pattern.length;
        int n = input.length;

        int[] badChar = createCharTable(pattern);
        int[] goodSuffix = createSuffixTable(pattern);

        int j = 0;
        int i = m - 1;
        int comparisonCount = 0;

        while (j <= n - m) {
            i = m - 1;
            while (i >= 0 && pattern[i] == input[j + i]) {
                comparisonCount++;
                i--;
            }

            if (i < 0) {
                System.out.println("Total comparisons: " + comparisonCount);
                return j;
            } else {
                int badCharShift = badChar[input[j + i] & 0xFF] - (m - 1 - i);
                int goodSuffixShift = goodSuffix[i];

                j += Math.max(badCharShift, goodSuffixShift);
            }
        }

        System.out.println("Total comparisons: " + comparisonCount);
        return -1;
    }

    private static int[] createCharTable(byte[] pattern) {
        int m = pattern.length;
        int[] badChar = new int[256];

        for (int i = 0; i < 256; i++) {
            badChar[i] = m;
        }

        for (int i = 0; i < m - 1; i++) {
            badChar[pattern[i] & 0xFF] = m - 1 - i;
        }

        return badChar;
    }

    private static int[] createSuffixTable(byte[] pattern) {
        int m = pattern.length;
        int[] goodSuffix = new int[m];
        int[] suffix = new int[m];

        suffix[m - 1] = m;
        int h = m - 1;
        int f = 0;

        for (int i = m - 2; i >= 0; i--) {
            if (i > h && suffix[i + m - 1 - f] < i - h) {
                suffix[i] = suffix[i + m - 1 - f];
            } else {
                if (i < h) {
                    h = i;
                }
                f = i;
                while (h >= 0 && pattern[h] == pattern[h + m - 1 - f]) {
                    h--;
                }
                suffix[i] = f - h;
            }
        }

        for (int i = 0; i < m - 1; i++) {
            goodSuffix[i] = m;
        }

        for (int i = m - 1; i >= 0; i--) {
            if (suffix[i] == i + 1) {
                for (int j = 0; j < m - 1 - i; j++) {
                    if (goodSuffix[j] == m) {
                        goodSuffix[j] = m - 1 - i;
                    }
                }
            }
        }

        return goodSuffix;
    }

}