import java.io.*;
import java.util.*;

/**
 * 2016 Mid-Atlantic Regionals
 * @author Ariana Herbst
 * November 5, 2016
 */
public class DFS_Floodfill_Islands {
    static int N, M;
    static int[][] arr;
 
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        arr = new int[N][M];
        for (int r = 0; r < N; r++)
        {
            char[] chars = sc.next().toCharArray();
            for (int c = 0; c < M; c++)
            {
                if (chars[c] == 'L') //land
                {
                    arr[r][c] = 1;
                }
                else if (chars[c] == 'C')   //clouds
                {
                    arr[r][c] = -1;
                }
                else if (chars[c] == 'W')   //water
                {
                    arr[r][c] = 0;
                }
                else    //error
                {
                    arr[r][c] = -2;
                }    
            }
        }
        
        //change clouds adgacent to land to land
        for (int r = 0; r < N; r++)
        {
            for (int c = 0; c < M; c++)
            {
                if (arr[r][c] == 1)
                {
                    fill(r, c, 1);
                }
            }
        }
        //turn remaining clouds into water
        for (int r = 0; r < N; r++)
        {
            for (int c = 0; c < M; c++)
            {
                if (arr[r][c] == -1)
                {
                    arr[r][c] = 0;
                }
            }
        }
        //fill each land chunk with water
        //increment count each separate time fill() is called
        int count = 0;
        for (int r = 0; r < N; r++)
        {
            for (int c = 0; c < M; c++)
            {
                if (arr[r][c] == 1)
                {
                    fill(r, c, 0);
                    count++;
                }
            }
        }
        System.out.println(count);
    }
    
    public static void fill(int r, int c, int fillWith)
    {
        Deque<Point> explore = new ArrayDeque<Point>();
        boolean[][] visited = new boolean[N][M];
        explore.addLast(new Point(r, c));
        while (!explore.isEmpty())
        {
            Point p = explore.pop();
            visited[p.r][p.c] = true;
            if (arr[p.r][p.c] == -1 || arr[p.r][p.c] == 1)  //if cloud or land
            {
                arr[p.r][p.c] = fillWith;
                for (int[] d : dirs)
                {
                    if (isValid(p.r + d[0], p.c + d[1]) && !visited[p.r + d[0]][p.c + d[1]])
                    {
                        explore.push(new Point(p.r + d[0], p.c + d[1]));
                    }
                }
            }
        }
    }
    
    public static int[][] dirs = { {1,0}, {-1, 0}, {0, 1}, {0, -1}  };
    
    public static class Point   {
        int r, c;
        public Point (int r, int c) {   this.r = r; this.c = c; }
    }
    
    public static boolean isValid(int r, int c)
    {
        if (r >= 0 && r < N && c >= 0 && c < M)
            return true;
        return false;
    }
}
