import java.util.*;

class Solution {

    // factors[digit] = {power of 2, power of 3, power of 5, power of 7}
    static int[][] factors = {
        {0, 0, 0, 0}, // 0
        {0, 0, 0, 0}, // 1
        {1, 0, 0, 0}, // 2
        {0, 1, 0, 0}, // 3
        {2, 0, 0, 0}, // 4
        {0, 0, 1, 0}, // 5
        {1, 1, 0, 0}, // 6
        {0, 0, 0, 1}, // 7
        {3, 0, 0, 0}, // 8
        {0, 2, 0, 0}  // 9
    };

    int A, B, C, D;
    int totalStates;
    int[] dp;

    int encode(int a, int b, int c, int d) {
        return a
                + (A + 1) * (
                b
                + (B + 1) * (
                c
                + (C + 1) * d
        ));
    }

    int[] decode(int state) {
        int a = state % (A + 1);
        state /= (A + 1);

        int b = state % (B + 1);
        state /= (B + 1);

        int c = state % (C + 1);
        state /= (C + 1);

        int d = state;

        return new int[]{a, b, c, d};
    }

    /*
     * dp[state] = minimum number of digits required
     * to provide all prime factors represented by state.
     */
    void buildDP() {

        totalStates =
                (A + 1) *
                (B + 1) *
                (C + 1) *
                (D + 1);

        dp = new int[totalStates];

        Arrays.fill(dp, Integer.MAX_VALUE / 2);

        dp[0] = 0;

        for (int state = 1; state < totalStates; state++) {

            int[] need = decode(state);

            for (int digit = 1; digit <= 9; digit++) {

                int na = Math.max(
                        0,
                        need[0] - factors[digit][0]
                );

                int nb = Math.max(
                        0,
                        need[1] - factors[digit][1]
                );

                int nc = Math.max(
                        0,
                        need[2] - factors[digit][2]
                );

                int nd = Math.max(
                        0,
                        need[3] - factors[digit][3]
                );

                int previous = encode(na, nb, nc, nd);

                dp[state] = Math.min(
                        dp[state],
                        dp[previous] + 1
                );
            }
        }
    }

    /*
     * Construct the smallest zero-free number
     * having exactly len digits and satisfying state.
     */
    String buildSmallest(int len, int state) {

        if (dp[state] > len) {
            return null;
        }

        int[] need = decode(state);

        StringBuilder ans = new StringBuilder();

        for (int pos = 0; pos < len; pos++) {

            int remaining = len - pos - 1;

            for (int digit = 1; digit <= 9; digit++) {

                int na = Math.max(
                        0,
                        need[0] - factors[digit][0]
                );

                int nb = Math.max(
                        0,
                        need[1] - factors[digit][1]
                );

                int nc = Math.max(
                        0,
                        need[2] - factors[digit][2]
                );

                int nd = Math.max(
                        0,
                        need[3] - factors[digit][3]
                );

                int nextState = encode(na, nb, nc, nd);

                if (dp[nextState] <= remaining) {

                    ans.append(digit);

                    need[0] = na;
                    need[1] = nb;
                    need[2] = nc;
                    need[3] = nd;

                    break;
                }
            }
        }

        return ans.toString();
    }

    public String smallestNumber(String num, long t) {

        // -----------------------------------------
        // 1. Factorize t
        // -----------------------------------------

        long x = t;

        A = B = C = D = 0;

        while (x % 2 == 0) {
            A++;
            x /= 2;
        }

        while (x % 3 == 0) {
            B++;
            x /= 3;
        }

        while (x % 5 == 0) {
            C++;
            x /= 5;
        }

        while (x % 7 == 0) {
            D++;
            x /= 7;
        }

        // t contains a prime factor other than 2,3,5,7
        if (x != 1) {
            return "-1";
        }

        // -----------------------------------------
        // 2. Build DP
        // -----------------------------------------

        buildDP();

        int targetState = encode(A, B, C, D);

        // -----------------------------------------
        // 3. Check whether num itself works
        // -----------------------------------------

        boolean zeroFree = true;

        int[] have = {0, 0, 0, 0};

        for (char ch : num.toCharArray()) {

            int digit = ch - '0';

            if (digit == 0) {
                zeroFree = false;
                continue;
            }

            for (int j = 0; j < 4; j++) {
                have[j] += factors[digit][j];
            }
        }

        if (zeroFree &&
                have[0] >= A &&
                have[1] >= B &&
                have[2] >= C &&
                have[3] >= D) {

            return num;
        }

        // -----------------------------------------
        // 4. Try to create a valid number
        //    with the SAME length
        // -----------------------------------------

        int n = num.length();

        int[][] prefix = new int[n + 1][4];

        boolean[] prefixHasZero = new boolean[n + 1];

        for (int i = 0; i < n; i++) {

            for (int j = 0; j < 4; j++) {
                prefix[i + 1][j] = prefix[i][j];
            }

            prefixHasZero[i + 1] = prefixHasZero[i];

            int digit = num.charAt(i) - '0';

            if (digit == 0) {
                prefixHasZero[i + 1] = true;
            } else {
                for (int j = 0; j < 4; j++) {
                    prefix[i + 1][j] += factors[digit][j];
                }
            }
        }

        /*
         * Change the rightmost possible digit.
         * This gives the smallest possible same-length answer.
         */
        for (int i = n - 1; i >= 0; i--) {

            // Prefix cannot contain zero.
            if (prefixHasZero[i]) {
                continue;
            }

            int original = num.charAt(i) - '0';

            for (int digit = original + 1; digit <= 9; digit++) {

                int[] current = new int[4];

                for (int j = 0; j < 4; j++) {
                    current[j] =
                            prefix[i][j] +
                            factors[digit][j];
                }

                int ra = Math.max(0, A - current[0]);
                int rb = Math.max(0, B - current[1]);
                int rc = Math.max(0, C - current[2]);
                int rd = Math.max(0, D - current[3]);

                int remaining = n - i - 1;

                int requiredState =
                        encode(ra, rb, rc, rd);

                if (dp[requiredState] <= remaining) {

                    StringBuilder ans =
                            new StringBuilder();

                    ans.append(num, 0, i);
                    ans.append(digit);

                    String suffix =
                            buildSmallest(
                                    remaining,
                                    requiredState
                            );

                    ans.append(suffix);

                    return ans.toString();
                }
            }
        }

        // -----------------------------------------
        // 5. Same length is impossible.
        //
        // IMPORTANT:
        // We may need MORE than n + 1 digits.
        // -----------------------------------------

        int minimumDigits = dp[targetState];

        /*
         * We need:
         *   - at least n + 1 digits
         *   - at least minimumDigits digits
         */
        int answerLength =
                Math.max(n + 1, minimumDigits);

        return buildSmallest(
                answerLength,
                targetState
        );
    }
}