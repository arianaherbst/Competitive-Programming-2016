import java.util.*;
/**
 * https://naq16.kattis.com/problems/bigtruck
 * 2016 ICPC North American Qualifier Contest
 * @author Ariana Herbst
 * September 24, 2016
 */
public class DijkstraVariation_BigTruck {
    static int N, M;
    static ArrayList<ArrayList<Edge>> edges;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        int[] items = new int[N];
        for (int n = 0; n < N; n++)
        {
            items[n] = sc.nextInt();
        }
        
        
        M = sc.nextInt();
        
        edges = new ArrayList<ArrayList<Edge>>();
        int p;
        for(p = 0; p < N; p++) {    //build empty graph
            ArrayList<Edge> temp = new ArrayList<Edge>();
            edges.add(temp);
        }
        
        //add edges to graph
        for (int i = 0; i < M; i++) {
            int s = sc.nextInt() - 1;
            int e = sc.nextInt() - 1;
            int l = sc.nextInt();
            
            edges.get(s).add(new Edge( e, l, i, items[e]));
            edges.get(e).add(new Edge (s, l, i, items[s]));
        }
        
        PriorityQueue<Edge> queue = new PriorityQueue<Edge>();
        
        for(Edge e : edges.get(0)) {
            e.totalItems += items[0];
            queue.add(e);
        }
        
        boolean found = false;
        Set<Integer> seen = new HashSet<Integer>();
        int total = 0;
        int toEnd = Integer.MAX_VALUE / 4;
        int maxItems = 0;
        int[] leastPath = new int[N];
        Arrays.fill(leastPath, Integer.MAX_VALUE / 41);
        while (!queue.isEmpty()) {
            Edge e = queue.poll();
            
            if (found && e.cost > toEnd)
            {
                System.out.print(toEnd + " " + maxItems);
                System.exit(0);
            }
            
            if (e.cost <= leastPath[e.target]) //if we have not seen it
            {
                leastPath[e.target] = e.cost;
                if (e.target == N - 1) {    //we've reached the end
                    found = true;
                    total = e.cost;
                    toEnd = e.cost;
                    
                    maxItems = Math.max(maxItems, e.totalItems);
                }
                else {
                    for (Edge k : edges.get(e.target)) {
                        
                        queue.add(new Edge(k.target, k.cost + e.cost, p++, e.totalItems + k.totalItems));
                    }
                }
            }
        }
        
        if (!found)
            System.out.print("impossible");
        else
            System.out.print(toEnd + " " + maxItems);
        
    }
    
    static class Edge implements Comparable<Edge> {
        public int target;
        public int cost;
        public int id;
        public int totalItems;
        
        public Edge (int target, int cost, int ID, int items) {
            this.target = target;
            this.cost = cost;
            this.id = ID;
            this.totalItems = items;
        }
        public int compareTo(Edge other) {
            int temp = cost - other.cost;
            if (temp != 0)
                return temp;
            return id - other.id;
        }
        
    }

}