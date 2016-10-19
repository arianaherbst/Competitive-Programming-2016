import java.util.*;
/**
 * Southwestern Europe Regional Contest (SWERC) 2014
 * https://pcs.spruett.me/problems/120
 * @author Ariana Herbst
 * October 12, 2016
 */
public class MaxFlow_BookClub {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        FordFulkerson ff = new FordFulkerson(N * 2 + 2);
        int src = N * 2;
        int snk = N * 2 + 1;
        
        for (int n = 0; n < N; n++)
        {
            ff.link(ff.nodes.get(src), ff.nodes.get(n), 1);
            ff.link(ff.nodes.get(n + N), ff.nodes.get(snk), 1);
        }
        for (int m = 0; m < M; m++)
        {
            int u = sc.nextInt();
            int v = sc.nextInt() + N;
            ff.link(ff.nodes.get(u), ff.nodes.get(v), 1);
        }
        int matches = ff.getMaxFlow(ff.nodes.get(src), ff.nodes.get(snk));
        if (matches == N)
            System.out.print("YES");
        else
            System.out.print("NO");        
    }
    
  //The following code is taken from Virginia Tech ACM ICPC Handbook//
    
    public static class Node {
        private Node() {  }
        List<Edge> edges = new ArrayList<Edge>();
        int index;
    }
    public static class Edge {
        boolean forward; //true: edge is in original graph
                        //false: edge is a backward edge
        Node from, to; //nodes connected
        int flow;
        final int capacity;
        Edge dual;      //reference to this edge's dual
        long cost;      //only used for MinCost.
        
        protected Edge(Node s, Node d, int c, boolean f)
        {
            forward = f;
            from = s;
            to = d;
            capacity = c;
        }
        
        int remaining() {   return capacity - flow;     }
        
        void addFlow(int amount) {
            flow += amount;
            dual.flow -= amount;
        }
    }
    
    public static abstract class MaxFlowSolver {
        List<Node> nodes = new ArrayList<Node>();
        
        public void link(Node n1, Node n2, int capacity) {
            Edge e12 = new Edge(n1, n2, capacity, true);
            Edge e21 = new Edge(n2, n1, 0, false);
            e12.dual = e21;
            e21.dual = e12;
            n1.edges.add(e12);
            n2.edges.add(e21);
        }
        public void link(Node n1, Node n2, int capacity, long cost)
        {
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
        public abstract int getMaxFlow(Node src, Node snk);
        
        public Node addNode() {
            Node n = new Node();
            n.index = nodes.size();
            nodes.add(n);
            return n;
        }
        
        List<Edge> getMinCut(Node src) {
            Queue<Node> bfs = new ArrayDeque<Node>();
            Set<Node> visited = new HashSet<Node>();
            bfs.offer(src);
            visited.add(src);
            while(!bfs.isEmpty()) {
                Node u = bfs.poll();
                for (Edge e : u.edges) {
                    if (e.remaining() > 0 && !visited.contains(e.to)) {
                        visited.add(e.to);
                        bfs.offer(e.to);
                    }
                }
            }
            List<Edge> minCut = new ArrayList<Edge>();
            for (Node s : visited) {
                for (Edge e : s.edges)
                    if (e.forward && !visited.contains(e.to))
                        minCut.add(e);
            }
            return minCut;
        }
    }
    static class FordFulkerson extends MaxFlowSolver 
    {
        FordFulkerson ()        {   this(0);    }
        FordFulkerson (int n)   {   super(n);   }
        
        @Override
        public int getMaxFlow(Node src, Node snk) {
            int total =  0;
            
            for (;;) {
                //find an augmenting path and record its edges in prev
                Edge [] prev = new Edge[nodes.size()];
                int addedFlow = findAugmentingPath(src, snk, prev);
                if (addedFlow == 0) break;
                
                total += addedFlow;
                
                //Go back along the path, and for each, move the
                //added flow from the edge to its dual.
                for (Edge edge = prev[snk.index];
                        edge != null;
                        edge = prev[edge.dual.to.index])
                {
                    edge.addFlow(addedFlow);
                }
            }
            return total;
        }
        
        int findAugmentingPath(Node src, Node snk, Edge [] from) {
            Deque<Node> queue = new ArrayDeque<Node>();
            queue.offer(src);
            
            int N = nodes.size();
            int [] minCapacity = new int[N];
            boolean [] visited = new boolean[N];
            visited[src.index] = true;
            Arrays.fill(minCapacity,  Integer.MAX_VALUE);
            
            while (queue.size() > 0) {
                Node node = queue.poll();
                if (node == snk)
                    return minCapacity[snk.index];
                
                for (Edge edge : node.edges) {
                    Node dest = edge.to;
                    if (edge.remaining() > 0 && !visited[dest.index]) {
                        visited[dest.index] = true;
                        from[dest.index] = edge;
                        minCapacity[dest.index] = Math.min (minCapacity[node.index], edge.remaining()); ///Math.min???
                        if (dest == snk)    
                            return minCapacity[snk.index];
                        
                        queue.push(dest);
                    }
                }
            }
            return 0; //no push
        }
    }
}