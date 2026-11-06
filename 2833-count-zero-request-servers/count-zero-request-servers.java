class Solution {
    public int[] countServers(int n, int[][] logs, int x, int[] queries) {
        Arrays.sort(logs, (a, b) -> a[1] - b[1]);
        int q = queries.length;
        Integer[] order = new Integer[q];
        for (int i = 0; i < q; i++) order[i] = i;
        Arrays.sort(order, (a, b) -> queries[a] - queries[b]);
        int[] cnt = new int[n + 1];
        int active = 0, l = 0, r = 0;
        int[] answer = new int[q];
        for (int qi : order) {
            int time = queries[qi];
            while (r < logs.length && logs[r][1] <= time) {
                if (cnt[logs[r][0]]++ == 0) active++;
                r++;
            }
            while (l < r && logs[l][1] < time - x) {
                if (--cnt[logs[l][0]] == 0) active--;
                l++;
            }
            answer[qi] = n - active;
        }
        return answer;
    }
}