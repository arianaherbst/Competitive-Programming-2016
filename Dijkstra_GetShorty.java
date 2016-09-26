import java.util.*;
/**
 * https://open.kattis.com/problems/getshorty
 * @author Ariana Herbst
 * September 22, 2016
 */
public class Dijkstra_GetShorty {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while(true)
        {
            int N = sc.nextInt();
            int M = sc.nextInt();
            if (N == 0 && M == 0)
            {
                System.exit(0);
            }
            double[] best = new double[N];
            ArrayList<ArrayList<Edge>> corrs = new ArrayList<ArrayList<Edge>>();
            for (int i = 0; i < N; i++ )
            {
                corrs.add(new ArrayList<Edge>());
            }
            int m;
            for (m = 0; m < M; m++)
            {
                int a = sc.nextInt();
                int b = sc.nextInt();
                double d = sc.nextDouble();
                corrs.get(a).add(new Edge(b, d, m));   
                corrs.get(b).add(new Edge(a, d, m));   
            }
            
            PriorityQueue<Edge> queue = new PriorityQueue<Edge>();
            
            for (Edge e : corrs.get(0)) {
                queue.add(e); }
            
            boolean found = false;
            Set<Integer> seen = new HashSet<Integer>();
            double total = 1;
            while (!found) 
            {
                Edge e = queue.poll();
                if (!seen.contains(e.target))
                {
                    seen.add(e.target);
                    if (e.target == N - 1)
                    {
                        found = true;
                        total = e.shrink;
                    }
                    else
                    {
                        for (Edge v : corrs.get(e.target))
                        {
                            queue.add(new Edge(v.target, v.shrink * e.shrink, m++));
                        }
                    }
                }
                
                
            }
            
            
            System.out.printf("%.4f\n", total);
        }

    }

    static class Edge implements Comparable<Edge>
    {
        int target;
        double shrink;
        int id;
        public Edge(int t, double d, int id)
        {
            target = t;
            shrink = d;
            this.id = id;
        }
        public int compareTo(Edge other)
        {
            double temp = other.shrink - shrink;
            if (temp != 0)
            {
                if (temp > 0)
                    return 1;
                else
                    return -1;
            }
            return Integer.compare(this.id, other.id);
        }
    }

}
