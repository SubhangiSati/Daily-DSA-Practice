class Solution {
    public int minDifficulty(int[] jobDifficulty, int d) {
        if (jobDifficulty == null || d <= 0 || jobDifficulty.length < d) 
            return -1;
        
        int n = jobDifficulty.length;
        if (n == d) 
            return sum(jobDifficulty);
        
        int maxDiff[][] = new int[d][n];
        maxDiff[0][0] = jobDifficulty[0];
        for (int i = 1; i < n; i++) 
            maxDiff[0][i] = Math.max(maxDiff[0][i - 1], jobDifficulty[i]);
        
        for (int i = 1; i < d; i++) {
            for (int j = 0; j < n; j++) {
                if (j < i) 
                    maxDiff[i][j] = -1;
                else if (i == j) 
                    maxDiff[i][j] = maxDiff[i - 1][j - 1] + jobDifficulty[j]; 
                else 
                    maxDiff[i][j] = getMinDiff(maxDiff, i, j, jobDifficulty);
            }
        }
        
        return maxDiff[d - 1][n - 1];
    }
    
    private int sum(int[] jobDifficulty) {
        int maxDiff = 0;
        for (int job : jobDifficulty) 
            maxDiff += job;
        return maxDiff;
    }
    
    private int getMinDiff(int[][] maxDiff, int start, int end, int[] jobDifficulty) {
        int minDiff = Integer.MAX_VALUE;
        int lastDayTask = 0;
        for (int i = end; i >= start; i--) {
            lastDayTask = Math.max(lastDayTask, jobDifficulty[i]);
            int previousDayDiff = maxDiff[start - 1][i - 1];
            minDiff = Math.min(minDiff, previousDayDiff + lastDayTask);
        }
        return minDiff;
    }
}