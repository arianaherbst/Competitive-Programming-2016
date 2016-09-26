import java.util.*;
public class Trees_CountYourCousins {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        

        while (true)
        {
            int N = sc.nextInt();
            int K = sc.nextInt();
            if (N == 0 && K == 0)
            {
                return;
            }

            ArrayList<Integer> upTree = new ArrayList<Integer>();
            ArrayList<ArrayList<Integer>> downTree = new ArrayList<ArrayList<Integer>>();

            int prev = -1;
            int pInd = -1;

            for (int n = 0; n <= N; n++)
            {
                ArrayList<Integer> temp2 = new ArrayList<Integer>();
                downTree.add(temp2);
            }
            int[] names = new int[N + 1];
            names[0] = -1;
            upTree.add(-1);
            for (int n = 1; n <= N; n++)        //build upTree and downTree
            {
                int next = sc.nextInt();
                names[n] = next;
                if (next - prev > 1)    //if they're not siblings
                {
                    pInd++;
                }
                upTree.add(pInd);
                downTree.get(pInd).add(n);
                prev = next;
            }
            int target = -1;
            for (int i = 1; i <= N;i++)     //find index of the node you're trying to find
            {
                if (names[i] == K)
                    target = i;
            }
            int parent = upTree.get(target);
            if (parent <= 0)
            {
                System.out.println("0");
                continue;
            }
            int grandparent = upTree.get(parent);
            if (grandparent <= 0)
            {
                System.out.println("0");
                continue;
            }
            int cousins = 0;
            for (Integer uncle : downTree.get(grandparent))
            {
                if (uncle != parent)
                {
                    cousins += downTree.get(uncle).size();
                }
            }
            System.out.println(cousins);
        }
    }

}