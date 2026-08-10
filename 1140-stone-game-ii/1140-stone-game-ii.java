class Solution {
    int[][] dp;
    int[] suffix;
    int n;

    public int stoneGameII(int[] piles) {
        n = piles.length;
        dp = new int[n][n + 1];
        suffix = new int[n + 1];

        for (int i = n - 1; i >= 0; i--) {
            suffix[i] = suffix[i + 1] + piles[i];
        }

        return solve(piles, 0, 1);
    }

    private int solve(int[] piles, int i, int M) {
        if (i >= n) {
            return 0;
        }

        if (dp[i][M] != 0) {
            return dp[i][M];
        }

        // If Alice can take all remaining piles
        if (2 * M >= n - i) {
            return dp[i][M] = suffix[i];
        }

        int best = 0;

        for (int X = 1; X <= 2 * M; X++) {
            int taken = suffix[i] - suffix[i + X];

            int opponent = solve(piles, i + X, Math.max(M, X));

            best = Math.max(best, taken + suffix[i + X] - opponent);
        }

        return dp[i][M] = best;
    }
}