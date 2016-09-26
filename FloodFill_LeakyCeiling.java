import java.util.*;
/**
 * https://pcs.spruett.me/problems/30
 * @author Ariana Herbst
 * May 15, 2016
 */
public class FloodFill_LeakyCeiling {
   static int N;
   static int[][] states;
   static boolean[][] visited;
   static Queue<Cor> leaks = new ArrayDeque<Cor>();
   public static void main(String[] args)
   {
      Scanner sc = new Scanner(System.in);
      N = sc.nextInt();
      states =  new int[N][N];
      visited = new boolean[N][N];
      sc.nextLine();
      for(int r = 0; r < N; r++)
      {
         String line = sc.nextLine();
         for(int c = 0; c < N; c++)
         {
            char k = line.charAt(c);
            if(k == ' ')
            {
               states[r][c] = 10;
            }
            else if(k == 'X')
            {
               states[r][c] = -1;
            }
            else
            {
               states[r][c] = -2;
               leaks.add(new Cor(r, c, 0));
            }
         }
      }
      for( int i = 0; i < N; i++) {
         boolean[] temp = new boolean[N];
         Arrays.fill(temp, false); 
         visited[i] = temp;
      }
      
      while(!leaks.isEmpty())
      {
         Cor cor = leaks.remove();
         spread(cor);
      }
      print(); 
   }
   
   public static void print() {
      StringBuilder sb = new StringBuilder();
      for(int r = 0; r < N; r++)
      {
         for(int c = 0; c < N; c++)
         {
            if(states[r][c] == -1)
            {
               sb.append("X");
            }
            else if(!visited[r][c])
               sb.append(" ");
            else if(states[r][c] == -2)
            {
               sb.append("o");
            }
            
            else if(states[r][c] == 0)
            {
               sb.append(" ");
            }
            else if(states[r][c] > 9)
            {
               sb.append("9");
            }
            else {
               sb.append(states[r][c]);
            }
         }
         sb.append("\n");
      }
      System.out.print(sb);
   }
   
   public static void spread(Cor cor)
   {
      int r = cor.x;
      int c = cor.y;
      if(!visited[r][c])
         states[r][c] = Math.min(cor.min, states[r][c]);
      visited[r][c] = true;
      for (int i = 0; i < 4; i++)
      {
         
         int x = dirs[i][0] + r;
         int y = dirs[i][1] + c;
         Cor temp = new Cor(x,y, cor.min + 1);
         if (!isBorder(temp) && !visited[x][y] && states[r][c] != -1)
         {
            leaks.add(temp);
         }
      } 
   }
   
   public static boolean isBorder(Cor cor)
   {
      if (cor.x < 0 || cor.x >= N)
         return true;
      if (cor.y < 0 || cor.y >= N)
         return true;
      return false;
   
   }
   
   static class Cor 
   {
      public int x;
      public int y;
      public int min;
      public Cor(int X, int Y, int MIN) {
         x = X;
         y = Y;
         min = MIN;
      }
   
   
   }
   static int[][] dirs = new int[][] {
      { 1, 0 },
      {-1, 0},
      {0, 1},
      {0, -1}  
   };
}