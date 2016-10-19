import java.util.*;
/**
 * https://pcs.spruett.me/problems/26
 * @author Ariana Herbst
 * April 5, 2016
 */
public class Permutations_RandomElections
{

   public static void main(String[] args)
   {
      Scanner sc = new Scanner(System.in);
      int n = sc.nextInt();
      int r = sc.nextInt();
      
      long k = 1;
      for (int i = n; i > n - r; i--)
      {
         k *= i;
      }
      
      System.out.print(k + "");
   }
}