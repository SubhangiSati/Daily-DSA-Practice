class Solution {
    public boolean threeFibonacciSum(int n) {
        int p = 0;
        int q = 1;
        int r = p + q;

        if (n % 2 != 0)
            return false;

        while (r < 1000000000) {

            if (r == n / 2)
                return true;
            else {
                p = q;
                q = r;
                r = p + q;
            }

        }
        return false;
    }
}