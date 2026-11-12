class Solution {
    public int longestMonotonicSubarray(int[] nums) {
        int answer = 1;
        for (int i = 1, t = 1; i < nums.length; ++i) { 
            if (nums[i - 1] < nums[i]) 
                answer = Math.max(answer, ++t);
            else 
                t = 1;
        }
        for (int i = 1, t = 1; i < nums.length; ++i) {
            if (nums[i - 1] > nums[i]) 
                answer = Math.max(answer, ++t);
            else 
                t = 1;
        }
        return answer;
    }
}