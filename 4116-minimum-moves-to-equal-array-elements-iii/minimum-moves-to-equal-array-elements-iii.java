class Solution {
    public int minMoves(int[] nums) {
        int n = nums.length;
        int max = 0;
        int s = 0;
        for (int x : nums) {
            max = Math.max(max, x);
            s += x;
        }
        return max * n - s;
    }
}