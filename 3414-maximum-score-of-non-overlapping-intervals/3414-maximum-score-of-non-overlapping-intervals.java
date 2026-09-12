import java.util.*;

class Solution {

    static class Interval {
        int l, r, w, idx;

        Interval(int l, int r, int w, int idx) {
            this.l = l;
            this.r = r;
            this.w = w;
            this.idx = idx;
        }
    }

    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();

        Interval[] a = new Interval[n];

        for (int i = 0; i < n; i++) {
            a[i] = new Interval(
                intervals.get(i).get(0),
                intervals.get(i).get(1),
                intervals.get(i).get(2),
                i
            );
        }

        // Sort by starting point
        Arrays.sort(a, (x, y) -> {
            if (x.l != y.l) {
                return Integer.compare(x.l, y.l);
            }
            return Integer.compare(x.r, y.r);
        });

        // next[i] = first interval whose starting point > a[i].r
        int[] next = new int[n];

        for (int i = 0; i < n; i++) {
            int low = i + 1;
            int high = n;

            while (low < high) {
                int mid = low + (high - low) / 2;

                if (a[mid].l > a[i].r) {
                    high = mid;
                } else {
                    low = mid + 1;
                }
            }

            next[i] = low;
        }

        /*
         * dp[k][i] = best answer using at most k intervals
         * from intervals i ... n-1.
         */
        long[][] dp = new long[5][n + 1];

        // Code representing selected original indices.
        // Each index uses 16 bits. 4 indices = 64 bits.
        long[][] code = new long[5][n + 1];

        for (int k = 0; k <= 4; k++) {
            Arrays.fill(dp[k], 0);
            Arrays.fill(code[k], encodeEmpty());
        }

        for (int i = n - 1; i >= 0; i--) {

            for (int k = 1; k <= 4; k++) {

                // Option 1: skip this interval
                dp[k][i] = dp[k][i + 1];
                code[k][i] = code[k][i + 1];

                // Option 2: take this interval
                long takeScore = a[i].w + dp[k - 1][next[i]];

                long takeCode = insert(code[k - 1][next[i]], a[i].idx);

                if (takeScore > dp[k][i]) {
                    dp[k][i] = takeScore;
                    code[k][i] = takeCode;
                } else if (takeScore == dp[k][i]
                        && compareCode(takeCode, code[k][i]) < 0) {
                    code[k][i] = takeCode;
                }
            }
        }

        long bestCode = code[4][0];

        // Decode the selected indices
        int count = 0;
        for (int p = 0; p < 4; p++) {
            int value = getIndex(bestCode, p);

            if (value != 65535) {
                count++;
            }
        }

        int[] answer = new int[count];

        for (int p = 0; p < count; p++) {
            answer[p] = getIndex(bestCode, p);
        }

        return answer;
    }

    // Empty index = 65535
    private long encodeEmpty() {
        return 0xFFFFFFFFFFFFFFFFL;
    }

    // Insert an original index while maintaining sorted order
    private long insert(long code, int idx) {
        int[] arr = new int[4];

        for (int i = 0; i < 4; i++) {
            arr[i] = getIndex(code, i);
        }

        int pos = 0;

        while (pos < 4 && arr[pos] < idx) {
            pos++;
        }

        for (int i = 3; i > pos; i--) {
            arr[i] = arr[i - 1];
        }

        arr[pos] = idx;

        long result = 0;

        for (int i = 0; i < 4; i++) {
            result = (result << 16) | (arr[i] & 0xFFFFL);
        }

        return result;
    }

    private int getIndex(long code, int pos) {
        int shift = (3 - pos) * 16;
        return (int) ((code >>> shift) & 0xFFFFL);
    }

    // Lexicographical comparison of the 4 stored indices
    private int compareCode(long a, long b) {
        for (int i = 0; i < 4; i++) {
            int x = getIndex(a, i);
            int y = getIndex(b, i);

            if (x != y) {
                return Integer.compare(x, y);
            }
        }

        return 0;
    }
}