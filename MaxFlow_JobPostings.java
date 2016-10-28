import java.util.*;
/**
 * 2013 University of Chicago Invitational Programming Contest
 * https://pcs.spruett.me/problems/122
 * @author Ariana Herbst
 * October 28, 2016
 */
public class MaxFlow_JobPostings {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(true)
        {
            int N = sc.nextInt();   // # postings
            int M = sc.nextInt();   // # students
            if (N == 0 && M == 0)
                System.exit(0);

            int[][] sat = new int[3][4];
            int k = -12;
            for (int year = 2; year >= 0; year--)
                for (int choice = 0; choice < 4; choice++)  {
                    sat[year][choice] = k;  //satisfaction matrix with negative values of those given
                    k++;
                }
            //We look for the minimum cost and maximum flow of this graph, where
            //cost is (-1 * satisfaction) and flow are jobs.
            MinCostMaxFlowSolver ek = new EdmondsKarp();
            Node[] students = new Node[M];
            Node[] postings = new Node[N];
            Node src = ek.addNode();
            Node snk = ek.addNode();
            for (int  n = 0; n < N; n++)
            {
                postings[n] = ek.addNode();
                ek.link(postings[n], snk, sc.nextInt(), 0);
            }
            for (int m = 0; m < M; m++)
            {
                students[m] = ek.addNode();
                ek.link(src, students[m], 1);
                int year = sc.nextInt() - 1;
                for (int c = 0; c < 4; c++)
                    ek.link(students[m], postings[sc.nextInt()], 1, sat[year][c]);
            }

            long[] ans = ek.getMinCostMaxFlow(src, snk);  
            System.out.println(-1 * ans[1]);  
        }
    }
    
    //The following code is taken from Virginia Tech ACM ICPC Handbook//
    
    static abstract class MinCostMaxFlowSolver extends MaxFlowSolver {
        //returns [maxflow, mincost]
        abstract long [] getMinCostMaxFlow(Node src, Node snk);
        //unavoidable boiler plate
        MinCostMaxFlowSolver()      {   this(0);  }
        MinCostMaxFlowSolver(int n) {   super(n); }
    }
    static class EdmondsKarp extends MinCostMaxFlowSolver
    {
        EdmondsKarp ()      { this(0); }
        /* Create a graph with n nodes. */
        EdmondsKarp (int n) { super(n); }

        long minCost;

        @Override
        public long [] getMinCostMaxFlow(Node src, Node snk) {
            long maxflow = getMaxFlow(src, snk);
            return new long [] { maxflow, minCost };
        }
        static final long INF = Long.MAX_VALUE/4;

        /**
         * Maximize the flow, and simultaneously minimize its cost.
         * Code taken from judge solution to Chicago 2013/Job Postings
         * http://serjudging.vanb.org/wp-content/uploads/jobpostings_artur.java
         */
        @Override
        public long getMaxFlow(Node src, Node snk) {
            final int n = nodes.size();
            final int source = src.index;
            final int sink = snk.index;
            long flow = 0;
            long cost = 0;
            long[] potential = new long[n]; // allows Dijkstra to work with negative edge weights

            while (true) {
                Edge[] parent = new Edge[n]; // used to store an augmenting path
                long[] dist = new long[n]; // minimal cost to vertex
                Arrays.fill(dist, INF);
                dist[source] = 0;

                // Dijkstra on cost
                PriorityQueue<Item> que = new PriorityQueue<Item>();
                que.add(new Item(0, source));
                while (!que.isEmpty()) {
                    Item item = que.poll();
                    if (item.dist != dist[item.v])
                        continue;

                    for (Edge e : nodes.get(item.v).edges) {
                        long temp = dist[item.v] + e.cost + potential[item.v] - potential[e.to.index];
                        // if can push some flow, and new cost is cheaper than push
                        if (e.capacity > e.flow && dist[e.to.index] > temp) {
                            dist[e.to.index] = temp;
                            parent[e.to.index] = e;
                            que.add(new Item(temp, e.to.index));
                        }
                    }
                }

                // couldn't reach sink
                if (parent[sink] == null)
                    break;
                // update potentials for Dijkstra
                for (int i = 0; i < n; i++)
                    if (parent[i] != null)
                        potential[i] += dist[i];

                // maximum flow that can be pushed
                long augFlow = Long.MAX_VALUE;
                for (int i = sink; i != source; i = parent[i].from.index)
                    augFlow = Math.min(augFlow, parent[i].capacity - parent[i].flow);

                // push the flow
                for (int i = sink; i != source; i = parent[i].from.index) {
                    Edge e = parent[i];
                    e.addFlow(augFlow);
                    cost += augFlow * e.cost;
                }
                flow += augFlow;
            }

            minCost = cost;
            return flow;
        }
 
        static class Item implements Comparable<Item>   {
            long dist;
            int v;
            public Item(long dist, int v)   {
                this.dist = dist;
                this.v = v;
            }
            
            public int compareTo(Item that) {
                return Long.compare(this.dist, that.dist);
            }
        }
    }
    
    public static class Node {
        private Node() {}

        List<Edge> edges =new ArrayList<Edge>();
        int index;
    }

    public static class Edge
    {
        boolean forward;
        Node from, to;
        int flow;
        final long capacity;
        Edge dual;
        long cost;

        protected Edge(Node s, Node d, long c, boolean f)
        {
            forward = f;
            from = s;
            to = d;
            capacity = c;
        }
        long remaining() { return capacity - flow;  }
        void addFlow(long augFlow) {
            flow += augFlow;
            dual.flow -= augFlow;
        }
    }

    public static abstract class MaxFlowSolver {
        List<Node> nodes = new ArrayList<Node>();

        public void link(Node n1, Node n2, int capacity)
        {
            Edge e12 = new Edge(n1, n2, capacity, true);
            Edge e21 = new Edge(n2, n1, 0, false);
            e12.dual = e21;
            e21.dual = e12;
            n1.edges.add(e12);
            n2.edges.add(e21);
        }

        public void link(Node n1, Node n2, int capacity, long cost) {
            Edge e12 = new Edge(n1, n2, capacity, true);
            Edge e21 = new Edge(n2, n1, 0, false);
            e12.dual = e21;
            e21.dual = e12;
            n1.edges.add(e12);
            n2.edges.add(e21);
            e12.cost = cost;
            e21.cost = -cost;
        }

        void link(int n1, int n2, int capacity)
        {
            link(nodes.get(n1), nodes.get(n2), capacity);
        }
        
        protected MaxFlowSolver(int n) {
            for (int i = 0; i < n; i++) 
                addNode();
        }

        protected MaxFlowSolver() { this(0);  }

        public abstract long getMaxFlow(Node src, Node snk);

        public Node addNode()  {
            Node n = new Node();
            n.index = nodes.size();
            nodes.add(n);
            return n;
        }
    }
}
