import java.util.*;;
/**
 * http://codeforces.com/problemset/problem/676/B
 * @author Ariana Herbst
 * May 25, 2016
 */
public class Simulation_PyramidOfGlasses {
    static int N, S;
    static int glassesFilled;
    static ArrayList<ArrayList<Glass>> tree;
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        S = sc.nextInt();
        tree = new ArrayList<ArrayList<Glass>>();
        
        for(int row = 0; row < N; row++)
        {
            ArrayList<Glass> temp = new ArrayList<Glass>();
            for(int j = 0; j <= row; j++)
            {   
                temp.add(new Glass(row, j));
            }
            tree.add(temp);
        }
        pour(tree.get(0).get(0), S, 1);
        System.out.print(glassesFilled + "");
    }
    public static void pour(Glass glass, double sec, double rate)
    {
        
        if (glass.curr != 1)
        {
            double possibleSecondsSpent = (1 - glass.curr) / rate;
            if (possibleSecondsSpent <= sec) {
                glass.curr = 1;
                sec -= possibleSecondsSpent;
                glassesFilled++;
            }
            else
            {
                glass.curr += sec * rate;
                return;
            }
        }
        if (glass.row < (tree.size() - 1))
        {
            pour(tree.get(glass.row + 1).get(glass.ind), sec, rate / 2);
            pour(tree.get(glass.row + 1).get(glass.ind + 1), sec, rate / 2);
        }
        
    }
    
    public static class Glass {
        double curr;
        int row;
        int ind;
        public Glass(int ROW, int INDEX)
        {
            row = ROW;
            ind = INDEX;
            curr = 0;
        }
    }

}