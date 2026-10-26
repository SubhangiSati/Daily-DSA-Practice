class Solution {
    int[][] ggrid;
    int m, n;

    public int countIslands(int[][] grid, int k) {
        int areaMax = 0;
        ggrid = grid;
        m = grid.length;
        n = grid[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (ggrid[i][j] != 0) {
                    if (areaCount(i, j) % k == 0)
                        areaMax++;
                }
            }
        }
        return areaMax;
    }

    long areaCount(int i, int j) {
        long area = ggrid[i][j];
        ggrid[i][j] = 0;
        if (i > 0 && ggrid[i - 1][j] != 0)
            area += areaCount(i - 1, j);
        if (j > 0 && ggrid[i][j - 1] != 0)
            area += areaCount(i, j - 1);
        if (i < m - 1 && ggrid[i + 1][j] != 0)
            area += areaCount(i + 1, j);
        if (j < n - 1 && ggrid[i][j + 1] != 0)
            area += areaCount(i, j + 1);

        return area;
    }
}