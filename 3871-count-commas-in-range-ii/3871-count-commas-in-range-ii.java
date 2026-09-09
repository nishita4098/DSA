class Solution {
    public long countCommas(long n) {
        long ans = 0;

        // First comma appears for numbers >= 1000
        long power = 1000;

        while (power <= n) {
            ans += n - power + 1;

            // Next comma appears at 1,000,000, then 1,000,000,000, ...
            power *= 1000;
        }

        return ans;
    }
}