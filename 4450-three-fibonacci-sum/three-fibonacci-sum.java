class Solution {
    static Set<Integer> f = new HashSet<>();
    static {
        int p = 0, q = 1;
        while (q < 1000000001) {
            int r = p + q;
            p = q;
            q = r;
            f.add(q);
        }
    }

    public boolean threeFibonacciSum(int n) {
        return n % 2 == 0 && f.contains(n / 2);
    }
}