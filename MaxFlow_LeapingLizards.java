import java.util.*;
/**
 * https://pcs.spruett.me/problems/119
 * 2005 ICPC Mid Central
 * @author Ariana Herbst
 * October 15, 2016
 */
public class MaxFlow_LeapingLizards {
    public static int T, R, D, C;
    public static ArrayList<Integer[]> dirs;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        T = sc.nextInt();
        for (int t = 1; t <= T; t++)
        {
            R = sc.nextInt();
            D = sc.nextInt();
            dirs = createDirs(D);
            MaxFlowSolver maxFlow = new FordFulkerson();
            
            sc.nextLine();
            char[] chars = sc.nextLine().toCharArray();
            C = chars.length;
            Node[][] left = new Node[R][C];
            Node[][] right = new Node[R][C];
            for (int r = 0; r < R; r++)     //adds each node weight
            {
                for (int c = 0; c < C; c++)
                {
                    left[r][c] = maxFlow.addNode();
                    right[r][c] = maxFlow.addNode();
                    maxFlow.link(left[r][c], right[r][c], ((int)chars[c] - '0'));
                }
                if (r != R - 1)
                    chars = sc.nextLine().toCharArray();
            }
            Node src = maxFlow.addNode();   //source node
            Node snk = maxFlow.addNode();   //sink node
            //connects adjacent nodes.  If adjacent node is out of bounds,
            //connects this node to snk
            for (int r = 0; r < R; r++) 
            {
                for (int c = 0; c < C; c++)
                {
                    //to sink
                    boolean toSink = false;
                    for (Integer[] dir : dirs)
                    {
                        int i = r + dir[0];
                        int j = c + dir[1];
                        if (inBounds(i, j)) {
                            maxFlow.link(right[r][c], left[i][j], Integer.MAX_VALUE);
                        }
                        else if (!toSink)   {
                            maxFlow.link(right[r][c], snk, Integer.MAX_VALUE);
                            toSink = true;
                        }
                    }
                }
            }
            int totalLizards = 0;
            for (int r = 0; r < R; r++)
            {
                chars = sc.nextLine().toCharArray();
                for (int c = 0; c < C; c++)
                {
                    if (chars[c] == 'L')    {
                        maxFlow.link(src, left[r][c], 1);
                        totalLizards++;
                    }
                }
            }
            int happyLizards = maxFlow.getMaxFlow(src, snk);
            int sadLizards = totalLizards - happyLizards;
            System.out.print("Case #" + t + ": ");
            if (sadLizards >= 2)
                System.out.println(sadLizards + " lizards were left behind.");
            else if (sadLizards == 1)
                System.out.println(sadLizards + " lizard was left behind.");
            else if (sadLizards == 0)
                System.out.println("no lizard was left behind.");      
        }
    }
    public static boolean inBounds(int r, int c)    {
        return ( r >= 0 && r < R && c >= 0 && c < C);
    }
    
    public static ArrayList<Integer[]> createDirs(int D)
    {
        ArrayList<Integer[]> temp = new ArrayList<Integer[]>();
        for (int i = 0; i <= D; i++)    {
            for (int j = 0; j <= D; j++)    {
                if ((i * i) + (j * j) <= (D * D) && !(i == 0 && j == 0))   {
                    temp.add(new Integer[] { i, j });
                    if (i != 0)
                        temp.add(new Integer[] {i * -1, j});
                    if (j != 0)
                        temp.add(new Integer[] {i, j * -1});
                    if (i != 0 && j != 0)
                        temp.add(new Integer[] {i * -1, j * -1});
                }  
            }
        }
        return temp;
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
