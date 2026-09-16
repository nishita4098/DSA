class Solution {
    public int numberOfSets(int n, int k) {
        int MOD = 1000000007;

        // dp[j] = number of ways to draw current number of segments
        // using points 0...j
        long[][] dp = new long[k + 1][n];

        // 0 segments: one way for every prefix
        for (int j = 0; j < n; j++) {
            dp[0][j] = 1;
        }

        for (int segments = 1; segments <= k; segments++) {
            long sum = 0;

            for (int j = 0; j < n; j++) {
                // Start a segment at some point <= j
                if (j > 0) {
                    sum = (sum + dp[segments - 1][j - 1]) % MOD;
                }

                dp[segments][j] = (dp[segments][j] + sum) % MOD;

                // Segment must have at least two points.
                if (j > 0) {
                    dp[segments][j] =
                        (dp[segments][j] + dp[segments][j - 1]) % MOD;
                }
            }
        }

        return (int) dp[k][n - 1];
    }
}