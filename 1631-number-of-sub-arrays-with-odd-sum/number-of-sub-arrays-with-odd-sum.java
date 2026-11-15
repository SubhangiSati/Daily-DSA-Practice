class Solution {
    public int numOfSubarrays(int[] arr) {
        final int MOD = 1_000_000_007;
        int[] prefixCountByParity = { 1, 0 };
        int result = 0;
        int prefixSum = 0;
        for (int num : arr) {
            prefixSum += num;
            int currentParity = prefixSum & 1;
            int requiredParity = currentParity ^ 1;
            result = (result + prefixCountByParity[requiredParity]) % MOD;
            prefixCountByParity[currentParity]++;
        }
        return result;
    }
}