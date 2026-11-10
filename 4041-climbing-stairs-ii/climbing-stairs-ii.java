class Solution {
    public int climbStairs(int n, int[] costs) {
        if (n == 1)
            return costs[0] + 1;
        int thirdBack = 0;
        int secondBack = costs[0] + 1;
        int firstBack = costs[1] + Math.min(secondBack + 1, 4);
        int answer = firstBack;
        for (int i = 3; i <= n; i++) {
            int oneStep = firstBack + 1;
            int twoStep = secondBack + 4;
            int threeStep = thirdBack + 9;
            answer = costs[i - 1] + Math.min(oneStep, Math.min(twoStep, threeStep));
            thirdBack = secondBack;
            secondBack = firstBack;
            firstBack = answer;
        }
        return answer;
    }
}