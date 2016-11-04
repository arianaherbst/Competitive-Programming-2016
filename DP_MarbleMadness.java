import java.util.*;
/**
 * https://pcs.spruett.me/problems/106
 * @author Ariana Herbst
 * November 4, 2016
 */
public class DP_MarbleMadness {
    static long[] cache;
    static int N;
    static long[] nums;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        cache = new long[N];
        Arrays.fill(cache, -1);
        nums = new long[N];
        long sum = 0;
        for (int n = 0; n < N; n++) {
            nums[n] = sc.nextLong();
            sum += nums[n];
        }
        if (N == 1) {
            System.out.println("0 0");
            System.exit(0);
        }
        if (N == 2) {
            System.out.println(sum + " " + Math.min(nums[0], nums[1]));
            System.exit(0);
        }
        long ans = 0;
        ans += nums[0] + nums[N - 1];
        nums[1] += nums[0]; nums[N - 2] += nums[N - 1];
        nums[0] = 0; nums[N - 1] = 0;
        ans += dp(1);
        System.out.println(sum * 2 + " " + ans);
    }
    public static long dp(int index)
    {
        if (index > N - 2)
            return 0;
        else if (index < 1)
            return 0;
        if (cache[index] != -1)
            return cache[index];
        long moveAdjacents = nums[index - 1] + nums[index + 1] + dp(index + 3);
        long moveThis = nums[index] + dp(index + 2);
        long ans = Math.min(moveAdjacents, moveThis);
        cache[index] = ans;
        return ans;        
    }
}
