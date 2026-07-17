import java.util.*;

class Solution {

    List<List<Integer>> result = new ArrayList<>();

    public List<List<Integer>> subsets(int[] nums) {

        backtrack(nums, 0, new ArrayList<>());

        return result;
    }

    private void backtrack(int[] nums, int index, List<Integer> current) {

        // Every current subset is valid
        result.add(new ArrayList<>(current));

        for (int i = index; i < nums.length; i++) {

            // Choose
            current.add(nums[i]);

            // Explore
            backtrack(nums, i + 1, current);

            // Unchoose (Backtrack)
            current.remove(current.size() - 1);
        }
    }
}