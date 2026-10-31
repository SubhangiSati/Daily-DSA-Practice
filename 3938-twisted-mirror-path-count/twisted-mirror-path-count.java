class Solution {
    private static final int MOD = 1_000_000_007;
    public int uniquePaths(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        long[][] dp = new long[m][n];
        dp[0][0] = 1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1 || dp[i][j] == 0) 
                    continue;
                long ways = dp[i][j];
                if (j + 1 < n) {
                    int r = i;
                    int c = j + 1;
                    boolean movingRight = true;
                    while (r < m && c < n && grid[r][c] == 1) {
                        if (movingRight) {
                            r++;
                            movingRight = false;
                        } else {
                            c++;
                            movingRight = true;
                        }
                    }

                    if (r < m && c < n) 
                        dp[r][c] = (dp[r][c] + ways) % MOD;
                }
                if (i + 1 < m) {
                    int r = i + 1;
                    int c = j;
                    boolean movingDown = true;
                    while (r < m && c < n && grid[r][c] == 1) {
                        if (movingDown) {
                            c++;
                            movingDown = false;
                        } else {
                            r++;
                            movingDown = true;
                        }
                    }

                    if (r < m && c < n) 
                        dp[r][c] = (dp[r][c] + ways) % MOD;
                }
            }
        }
        return (int) dp[m - 1][n - 1];
    }
}
