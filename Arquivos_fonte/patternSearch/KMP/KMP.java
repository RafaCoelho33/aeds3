package patternSearch.KMP;

import java.io.RandomAccessFile;

public class KMP{
    private byte[] pattern;
    private byte[] data;
    private static final String dbPath = "./Database/player_db.db";

    public KMP(byte[] pattern) throws Exception {

        RandomAccessFile raf = new RandomAccessFile(dbPath, "rw");
        byte[] input = new byte[(int)raf.length()];
        raf.read(input);

        this.pattern = pattern;
        this.data = input;

        raf.close();
    }

    public static int[] calculateLPS(byte[] pattern) {
        int m = pattern.length;
        int[] lps = new int[m];
        int j = 0;

        for (int i = 1; i < m;) {
            if (pattern[i] == pattern[j]) {
                lps[i] = j + 1;
                j++;
                i++;
            } else {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }

    public int[] search() {
        int m = pattern.length;
        int n = data.length;
        int[] lps = calculateLPS(pattern);
        int[] result = new int[2];

        int i = 0, j = 0, comparisons = 0;

        while (i < n) {
            if (data[i] == pattern[j]) {
                i++;
                j++;
                comparisons++;
                if (j == m) {
                    result[0] = comparisons;
                    result[1] = i - j;
                    return result; 
                }
            } else {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                    comparisons++;
                }
            }
        }

        result[0] = comparisons;
        result[1] = -1; 
        return result;
    }

}