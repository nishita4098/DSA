class Solution {
    public int minOperations(int[] nums, int x) {
        int n = nums.length;

        int total = 0;
        for (int num : nums) {
            total += num;
        }

        int target = total - x;

        // We need to remove everything
        if (target == 0) {
            return n;
        }

        if (target < 0) {
            return -1;
        }

        int left = 0;
        int sum = 0;
        int maxLength = -1;

        for (int right = 0; right < n; right++) {
            sum += nums[right];

            while (sum > target && left <= right) {
                sum -= nums[left];
                left++;
            }

            if (sum == target) {
                maxLength = Math.max(maxLength, right - left + 1);
            }
        }

        return maxLength == -1 ? -1 : n - maxLength;
    }
}