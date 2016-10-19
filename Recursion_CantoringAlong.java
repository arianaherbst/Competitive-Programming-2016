import java.util.*;
/**
 * 2002 MidAtlantic USA Regionals
 * https://pcs.spruett.me/problems/114
 * @author Ariana Herbst
 * October 5, 2016
 */
public class Recursion_CantoringAlong {
    public static String blanks;
    public static Map<Integer, String> mem = new HashMap<Integer, String>();
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder(" ");
        mem.put(0, "-");
        for (int i = 0; i < 18; i++)
            sb.append(sb.toString());
        blanks = sb.toString();
        boolean notFirst = false;
        //System.out.println(blanks.length());
        while (sc.hasNext())
        {
            int N = sc.nextInt();
            if (notFirst)
                System.out.print("\n");
            System.out.print(recur(N));
            notFirst = true;
        }
    }
    public static String recur(int N)
    {
        String m = mem.get(N);
        if (m != null)
        {
            return m;
        }
        else {
            StringBuilder sb = new StringBuilder();
            String k = recur(N - 1);
            sb.append(k);
            String filled = blanks.substring(0, (int) Math.pow(3, N - 1));
            sb.append(filled);
            sb.append(k);
            mem.put(N, sb.toString());
            return sb.toString();
        }
    }
}