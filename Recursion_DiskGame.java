import java.util.*;
/**
 * https://pcs.spruett.me/problems/117
 * @author Ariana Herbst
 * October 5, 2016
 */
public class Recursion_DiskGame {
    static Stack<Integer>[] stacks;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        stacks = new Stack[3];
        for (int n = 0; n < 3; n++)
            stacks[n] = new Stack<Integer>();
        for (int i = N - 1; i >= 0; i--)
            stacks[0].add(i);
        recur(0, 2, 1, N - 1);
    }
    
    public static void recur(int current, int target, int spare, int level)
    {
        if (stacks[current].peek() == level)   //base case
        {
            int disk = stacks[current].pop();
            stacks[target].push(disk);
            char from = (char) (current + 'A');
            char to = (char) (target + 'A');
            System.out.println(from + " -> " + to);
        }
        else
        {
            recur(current, spare, target,level - 1);
            int disk = stacks[current].pop();
            stacks[target].push(disk);
            char from = (char) (current + 'A');
            char to = (char) (target + 'A');
            System.out.println(from + " -> " + to);
            recur(spare, target, current, level - 1);
        } 
    }
}
