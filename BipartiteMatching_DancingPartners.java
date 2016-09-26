import java.util.*;
/**
 * https://pcs.spruett.me/problems/35
 * @author Ariana Herbst
 * May 18, 2016
 */
public class BipartiteMatching_DancingPartners {

    static int M,F,P;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        M = sc.nextInt();
        F = sc.nextInt();
        P = sc.nextInt();
        ArrayList<ArrayList<Integer>> g = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < M + F; i++) {
            g.add(new ArrayList<Integer>());
        }
        for (int j = 0; j < P; j++) {
            g.get(sc.nextInt()).add(sc.nextInt() + M);
        }
        int matchings = findMaxMatching(g);
        int lonelyppl = M + F - 2 * matchings;
        System.out.print(lonelyppl + "");

    }
    
    public static int findMaxMatching(ArrayList<ArrayList<Integer>> graph) {
        int N = graph.size();
        int matches = 0;
        int[] matchedTo = new int[N];
        Arrays.fill(matchedTo, -1);
        for (int node = 0; node < N; node++) {
            boolean[] visited = new boolean[N];
            if (findMatchingNode(node, graph, matchedTo, visited)) {
                matches++;
            }
        }
        return matches;
    }
    
    public static boolean findMatchingNode(
            int node,
            ArrayList<ArrayList<Integer>> graph,
            int[] matchedTo,
            boolean[] visited
            ) {
        if (visited[node]) 
            return false;
        
        visited[node] = true;
        
        for (int neighbor : graph.get(node)) {
            if (matchedTo[neighbor] == -1 ||
                findMatchingNode(matchedTo[neighbor], graph, matchedTo, visited))
            {
                matchedTo[neighbor] = node;
                return true;
            }
        }
        return false;
    }

    
    
    
    
    
}