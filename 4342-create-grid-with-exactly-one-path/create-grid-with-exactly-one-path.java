class Solution {
    public String[] createGrid(int m, int n) {
        char[][] g = new char[m][n];
        for (int i = 0; i < m; i++)
            Arrays.fill(g[i], '#');
        Arrays.fill(g[0], '.');
        for (int i = 0; i < m; i++)
            g[i][n - 1] = '.';
        String[] answer = new String[m];
        for (int i = 0; i < m; i++)
            answer[i] = new String(g[i]);
        return answer;
    }
}