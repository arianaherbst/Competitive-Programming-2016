import java.util.*;
/**
 * https://pcs.spruett.me/problems/69
 * https://icpcarchive.ecs.baylor.edu/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=2200
 * @author Ariana Herbst
 * September 10, 2016
 */
public class BFS_BellmanFord_SignalStrength {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        ArrayList<ArrayList<Connection>> map = new ArrayList<ArrayList<Connection>>();

        double[] path = new double[N];
        for (int n = 0; n < N; n++)
        {
            map.add(new ArrayList<Connection>());
        }
        double[] mults = new double[N];
        for (int n = 0; n < N; n++)
        {
            mults[n] = sc.nextDouble();
            int K = sc.nextInt();
            for (int k = 0; k < K; k++)
            {
                map.get(n).add(new Connection(sc.nextInt(), sc.nextDouble()));
            }
        }
        path[0] = mults[0] ;
        for (int a = 0; a < N - 1 ; a++)
        {
            for (int u = 0; u < N; u++) // u is index of parent node
            {
                for (Connection v : map.get(u))
                {
                    path[v.target] = Math.max(path[v.target],
                            path[u] * v.strength * mults[v.target]);
                }
            }
        }
        System.out.printf("%.2f", path[N - 1]);

    }
    static class Connection
    {
        double strength;
        int target;
        public Connection(int t, double s)
        {
            target = t;
            strength = s;
        }   
    }
}
