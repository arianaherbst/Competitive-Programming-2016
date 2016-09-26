import java.util.*;
/**
 * Topological Sorting
 * https://pcs.spruett.me/problems/65
 * 2012 Southeast USA Regionals
 * @author Ariana Herbst
 * August 28, 2016
 */
public class DuelingPhilosophers {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        
        List<ArrayList<Integer>> outGoing = new ArrayList<ArrayList<Integer>>();
        int[] inDeg = new int[N + 1];
        for (int i = 0; i <= N; i++)
        {
            outGoing.add(new ArrayList<Integer>());
        }
        for (int m = 0; m < M; m++) //create outGoing map, record # incoming edges
        {
            int u = sc.nextInt();
            int v = sc.nextInt();
            outGoing.get(u).add(v);
            inDeg[v]++;
        }
        ArrayList<Integer> noInc = new ArrayList<Integer>();
        for (int i = 1; i <= N; i++)    //find nodes w/ no incoming edges, add to sort
        {
            if (inDeg[i] == 0)
            {
                noInc.add(i);
            }
        }
        boolean multSorts = false;
        for (int i = 0; i < noInc.size(); i++)  //topological sort
        {
            if (i < noInc.size() - 1)
            {
                multSorts = true;
            }
            int u = noInc.get(i);
            for (Integer v : outGoing.get(u))
            {
                inDeg[v]--;
                if (inDeg[v] == 0)
                {
                    noInc.add(v);
                }
            }
        }
        if (noInc.size() == N)
        {
            if (multSorts)
            {
                System.out.print("2");
                return;
            }
            else
            {
                System.out.print("1");
                return;
            }
        }
        else
        {
            System.out.print("0");
        }
        
    }

}
