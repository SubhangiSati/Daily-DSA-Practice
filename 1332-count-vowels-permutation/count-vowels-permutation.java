class Solution {
    public int countVowelPermutation(int n) {
        int[][] transition = new int[][] {
                {0,1,1,0,1}, {1,0,1,0,0}, {0,1,0,1,0}, {0,0,1,0,0}, {0,0,1,1,0}
        };
        int[][] f0 = new int[][] {
                {1}, {1}, {1}, {1}, {1}
        };
        int[][] powered = power(transition, n - 1, f0);
        int ret = 0;
        for (int[] row : powered) ret = (ret + row[0]) % mod;
        return ret;
    }

    int mod = 1000000007;
    public int[][] power(int[][] a, int n, int[][] f0) {
        int[][] res = f0;
        while (n > 0) {
            if ((n & 1) > 0) {
                res = multiply(a, res);
            }
            a = multiply(a, a);
            n >>= 1;
        }
        return res;
    }

    public int[][] multiply(int[][] a, int[][] b) {
        int[][] c = new int[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int k = 0; k < a[i].length; k++) {
                if (a[i][k] == 0) 
                    continue;
                for (int j = 0; j < b[k].length; j++) 
                    c[i][j] = (int) ((c[i][j] + (long) a[i][k] * b[k][j]) % mod);
            }
        }
        return c;
    }

    public long[][] power(long[][] a, int n, long[][] f0) {
        long[][] result = f0;
        while (n > 0) {
            if ((n & 1) > 0) 
                result = multiply(a, result);
            a = multiply(a, a);
            n >>= 1;
        }
        return result;
    }

    public long[][] multiply(long[][] a, long[][] b) {
        long[][] c = new long[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int k = 0; k < a[i].length; k++) {
                if (a[i][k] == 0) 
                    continue;
                for (int j = 0; j < b[k].length; j++) 
                    c[i][j] = (c[i][j] + a[i][k] * b[k][j]) % mod;
            }
        }
        return c;
    }
}