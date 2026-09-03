class Solution {
    public boolean uniformArray(int[] nums1) {
        int minOdd = Integer.MAX_VALUE;
        int minEven = Integer.MAX_VALUE;

        for (int num : nums1) {
            if (num % 2 == 0) {
                minEven = Math.min(minEven, num);
            } else {
                minOdd = Math.min(minOdd, num);
            }
        }

        // All numbers are even
        if (minOdd == Integer.MAX_VALUE) {
            return true;
        }

        // All numbers are odd
        if (minEven == Integer.MAX_VALUE) {
            return true;
        }

        // Mixed parity: make everything odd
        return minOdd < minEven;
    }
}