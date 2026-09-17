class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        int INF = Integer.MAX_VALUE / 2;

        int[] best = new int[n];
        for (int i = 0; i < n; i++) {
            best[i] = INF;
        }

        int left = 0;
        int sum = 0;
        int answer = INF;
        int minLength = INF;

        for (int right = 0; right < n; right++) {
            sum += arr[right];

            while (sum > target) {
                sum -= arr[left++];
            }

            if (sum == target) {
                int length = right - left + 1;

                // Find an earlier non-overlapping subarray
                if (left > 0 && best[left - 1] != INF) {
                    answer = Math.min(answer, length + best[left - 1]);
                }

                minLength = Math.min(minLength, length);
            }

            // Best subarray ending at or before right
            if (right > 0) {
                best[right] = Math.min(best[right - 1], minLength);
            } else {
                best[right] = minLength;
            }
        }

        return answer == INF ? -1 : answer;
    }
}