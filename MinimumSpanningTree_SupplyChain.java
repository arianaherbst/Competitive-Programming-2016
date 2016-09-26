import java.util.*;
/**
 * https://pcs.spruett.me/problems/8
 * @author Ariana Herbst
 * March 17, 2016
 */
public class MinimumSpanningTree_SupplyChain {
   static int N;
   static int M;
   static List<Edge> l;
   static Set minEdges;
   static DisjointSets djs;
   
   public static void main(String[] args)
   {
      Scanner sc = new Scanner(System.in);
      N = sc.nextInt();
      M = sc.nextInt();
      l = new ArrayList<Edge>();
      minEdges = new HashSet<Edge>();
      DisjointSets djs = new DisjointSets(N);
      for (int i = 0; i < M; i++)
      {
         l.add(new Edge(sc.nextInt(), sc.nextInt(), sc.nextInt()));
      }
      Collections.sort(l);
      for ( Edge e : l)
      {
         if ( djs.find(e.p1) != djs.find(e.p2) )
         {
            minEdges.add(e);
            djs.union(e.p1, e.p2);            
         }  
      }
      
      Iterator<Edge> it = minEdges.iterator();
      int totalCost = 0;
      while (it.hasNext())
      {
         totalCost += it.next().cost;    
      
      }
      if (djs.N == 1)
         System.out.print(totalCost + "");
      else
         System.out.print("not possible");
      
      
   }
   
   public static class Edge implements Comparable<Edge> {
      int p1, p2;
      int cost;
      public Edge(int a, int b, int cost)
      {
         p1 = a;
         p2 = b;
         this.cost = cost;
      }
      
      public int compareTo(Edge other)
      {
         return Integer.compare(cost, other.cost);
      }
   }

   public static class DisjointSets {
        int N; // The number of disjoint items
        int[] depth; // Depth of the set if < 0, else parent
        int[] parent;
 
        public DisjointSets(int numElements) {
            this.N = numElements;
            depth = new int[N];
            parent = new int[N];
            Arrays.fill(depth, 1);
            Arrays.fill(parent, -1);
        }
 
        public boolean union(int a, int b) {
            int root1 = find(a);
            int root2 = find(b);
 
            if (root1 == root2)
                return false;
 
            if (depth[root2] < depth[root1]) {
                parent[root2] = root1;
            } else {
                if (depth[root2] == depth[root1]) {
                    depth[root2]++;
                }
                parent[root1] = root2;
            }
 
            N--;
            return true;
        }
 
        public int find(int a) {
            if (parent[a] < 0) return a;
 
            parent[a] = find(parent[a]);
            return parent[a];
        }
    }
}