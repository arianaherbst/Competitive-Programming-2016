import java.util.*;
/**
 * http://codeforces.com/contest/733/problem/C
 * @author Ariana Herbst
 * November 1, 2016
 */
public class GreedyDP_EpidemicInMonstropolis {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Long> in = new ArrayList<Long>();
        ArrayList<Long> out = new ArrayList<Long>();
        int N = sc.nextInt();
        for (int n = 0; n < N; n++) {
            in.add(sc.nextLong());
        }
        int K = sc.nextInt();
        for (int k = 0; k < K; k++) {
            out.add(sc.nextLong());
        }
        int i = in.size() - 1;
        int o = out.size() - 1;
        long sum = 0;
        StringBuilder ans = new StringBuilder();
        while (i >= 0 && o >= 0)
        {
            while (sum < out.get(o) && i >= 0)
            {
                sum += in.get(i);
                i--;

            }
            i++;
            if (sum > out.get(o))
            {
                System.out.println("NO");
                System.exit(0);
            }
            if (sum == out.get(o))
            {
                int iterations = in.size() - i - 1;
                for (int j = 0; j < iterations; j++)
                {
                    int bigIndex = -1;
                    long bigNum = -1;
                    for (int k = in.size() - 2; k >= i; k--) //find eating monster
                    {
                        if (in.get(k) > in.get(k + 1) && in.get(k) > bigNum)
                        {
                            bigIndex = k;
                            bigNum = in.get(k);
                        }
                        else if (in.get(k) < in.get(k + 1) && in.get(k + 1) > bigNum)
                        {
                            bigIndex = k + 1;
                            bigNum = in.get(k + 1);
                        }
                    }
                    if (bigIndex > i  && bigNum > in.get(bigIndex - 1))  //eat left
                    {
                        ans.append((bigIndex + 1) + " L\n");
                        in.set(bigIndex - 1, bigNum + in.get(bigIndex - 1));
                        in.remove(bigIndex);
                    }
                    else if (bigIndex < in.size() - 1 && bigNum > in.get(bigIndex + 1))  //eat right
                    {
                        ans.append((bigIndex + 1) + " R\n");
                        in.set(bigIndex, bigNum + in.get(bigIndex + 1));
                        in.remove(bigIndex + 1);
                    }
                    else if (bigIndex == -1)
                    {
                        System.out.println("NO");
                        System.exit(0);
                    }
                }
                long a = in.remove(i);
                long b = out.remove(o);
                if (a != b)
                {
                    System.out.println("NO");
                    System.exit(0);
                }
                sum = 0;
                o--;  
                i--;
            }
        }
        if (in.size() == 0 && out.size() == 0)  {
            System.out.println("YES");
            System.out.println(ans);
        }
        else
            System.out.println("NO");
    }
}
