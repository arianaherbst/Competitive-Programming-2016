import java.util.*;
/**
 * https://pcs.spruett.me/problems/25
 * @author Ariana Herbst
 * April 5, 2016
 */
public class Combinations_Subsets_PrimeCombinations
{
   static int[] factors;
   static ArrayList<Integer> subset;
   public static void main(String[] args)
   {
      Scanner sc = new Scanner(System.in);
      int n = sc.nextInt();
      subset = new ArrayList<Integer>();
      StringBuilder sb = new StringBuilder();
      factors = new int[n];
      for (int i = 0; i < n; i++)
      {
         factors[i] = sc.nextInt();
      }
      
      makeSubsets(factors, 0, 1);
      Collections.sort(subset);
      
      for (Integer i: subset)
      {
         sb.append(i + " ");
      }
      System.out.print(sb);
   
   }
   
   public static void makeSubsets(int[] arr, int ind, int multiple) {
      if ( ind == arr.length) {
         subset.add(multiple);
      }
      else {
         makeSubsets(arr, ind + 1, multiple);
         multiple *= arr[ind];
         makeSubsets(arr, ind + 1, multiple);
         multiple /= arr[ind];
         }
   }
}