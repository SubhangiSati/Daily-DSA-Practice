class Solution {
    private int ans;
    private boolean[][][] memo;

    public int minCost(int[][] grid) {
        int startVal = grid[0][0];
        ans = Integer.MAX_VALUE;
        memo = new boolean[grid.length][grid[0].length][1024];
        dfs(grid, 0, 0, 0, grid.length, grid[0].length);
        return ans;
    }

    private void dfs(int[][] grid, int i, int j, int xor, int m, int n) {
        xor ^= grid[i][j];
        if (ans == 0)
            return;
        if (memo[i][j][xor])
            return;
        memo[i][j][xor] = true;
        if (i == m - 1 && j == n - 1) {
            ans = Math.min(ans, xor);
            return;
        }
        if (i + 1 < m)
            dfs(grid, i + 1, j, xor, m, n);
        if (j + 1 < n)
            dfs(grid, i, j + 1, xor, m, n);
    }
}