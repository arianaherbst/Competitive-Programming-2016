import java.util.*;
/**
 * https://open.kattis.com/problems/rings2
 * @author Ariana Herbst
 * October 8, 2016
 */
public class FloodFill_Rings {
    static Queue<Point> queue;
    static int[][] states;
    static int N, M;
    static int maxLevel = 0;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        queue = new ArrayDeque<Point>();
        states = new int[N][M];
        for (int r = 0; r < N; r++)
        {
            char[] line = sc.next().toCharArray();
            for (int c = 0; c < M; c++)
            {
                switch(line[c]) {
                case '.':
                    states[r][c] = 0;
                    queue.add(new Point(r, c, 0));
                    break;
                default:
                    states[r][c] = -1;
                    break;
                }   
            }
        }
        
        for (int r = 0; r < N; r++)
            for (int c = 0; c < M; c++)
            {
                if (isBorder(r, c))
                    queue.add(new Point(r, c, 1));
            }
        
        //fill
        while (!queue.isEmpty())
        {
            
            Point p = queue.poll();
            
            if (states[p.r][p.c] <= 0)  //if unvisite3d
            {
                if (states[p.r][p.c] != 0) {
                    states[p.r][p.c] = p.l;
                }
                //System.out.println(p.l);
                //print();
                maxLevel = Math.max(maxLevel, p.l);
                for (int[] dir : dirs)
                {
                    int r = p.r + dir[0];
                    int c = p.c + dir[1];
                    if (inBounds(r, c) && states[r][c] == -1)
                        queue.add(new Point(r, c, p.l + 1));

                }           
            }
        }

        print();

    }

    public static void print()
    {
        int pad;
        String wall;
        if (maxLevel < 10)
        {
            pad = 2;
            wall = "..";
        }
        else
        {
            pad = 3;
            wall = "...";
        }
        for (int r = 0; r < N; r++)
        {
            for (int c = 0; c < M; c++)
            {
                int k = states[r][c];
                switch(k) {
                case 0:
                    System.out.print(wall);
                    break;
                default:
                    int l = (k + "").length();
                    for (int i = 0; i < pad - l; i++ )
                    {
                        System.out.print(".");
                    }
                    System.out.print(k);
                    break;
                }
            }
            System.out.print("\n");
        }
    }

    public static class Point {
        int r, c, l;
        public Point(int row, int col, int lev) {
            r = row;
            c = col;
            l = lev;
        }
    }

    public static boolean inBounds(int r, int c) {
        return !(r < 0 || r >= N || c < 0 || c >= M);
    }
    
    public static boolean isBorder(int r, int c ) {
        return (r == 0 || r == N - 1 || c == 0 || c == M - 1 );
    }

    public static int[][] dirs = new int[][] {
        {-1, 0},
        {1, 0},
        {0, -1},
        {0, 1}
    };
}