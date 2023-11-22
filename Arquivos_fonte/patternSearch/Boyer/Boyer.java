package patternSearch.Boyer;

public class Boyer {

    private byte[] pattern;
    private byte[] input;

    public Boyer(byte[] pattern, byte[] input) {
        this.pattern = pattern;
        this.input = input;
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
                System.out.println("Number of comparisons: " + comparisonCount);
                return j;
            } else {
                int badCharShift = badChar[input[j + i] & 0xFF] - (m - 1 - i);
                int goodSuffixShift = goodSuffix[i];

                j += Math.max(badCharShift, goodSuffixShift);
            }
        }

        System.out.println("Number of comparisons: " + comparisonCount);
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
        int g = m - 1;
        int f = 0;

        for (int i = m - 2; i >= 0; i--) {
            if (i > g && suffix[i + m - 1 - f] < i - g) {
                suffix[i] = suffix[i + m - 1 - f];
            } else {
                if (i < g) {
                    g = i;
                }
                f = i;
                while (g >= 0 && pattern[g] == pattern[g + m - 1 - f]) {
                    g--;
                }
                suffix[i] = f - g;
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