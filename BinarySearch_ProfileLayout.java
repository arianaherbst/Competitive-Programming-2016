import java.util.*;
/**
 * https://pcs.spruett.me/problems/16
 * @author Ariana Herbst
 * April 14, 2016
 */
public class BinarySearch_ProfileLayout
{
   static int P,C;
   static long max;
   static long[] heights;
   
   public static void main(String[] args)
   {
      Scanner sc = new Scanner(System.in);
      P = sc.nextInt();
      C = sc.nextInt();
      
      heights = new long[P];
      max = 0;
      for(int i = 0; i < P; i++)
      {
         heights[i] = sc.nextInt();
         max = Math.max(max, heights[i]);
      }
      long testMax = 10000000000L;
      long testMin = 0;
      System.out.print(binarySearch(heights, testMin, testMax, 0));
        
   }
   
   private static long binarySearch(long[] a, long fromIndex, long toIndex, long key)
   {
      int run = 0;
      long low = fromIndex;
      long high = toIndex - 1;
      while (low <= high) {
         run++;

         long mid = (low + high) >>> 1;
         long midVal = sparePixels(mid);
         if (run > 100)
         {
            return mid + 1;
         }
         if (midVal < key)
            low = mid - 1;
         else if (midVal > key)
            high = mid + 1;
         else
            return mid;
         }
         
         return -(low + 1);
   }
   
   
   public static long sparePixels(long height)
   {
      if (height < max)
      {
         return height - max;
      }
      int index = 0;
      long col = 1;
      
      long spare;
      long temp = height;
      boolean bufferNeeded = false;
      while (index < heights.length)
      {
         if ((temp - heights[index]) >= 0 )
         {
             temp -= heights[index];
             temp -= 10;
         }
         else {
            temp = height;
            col++;
            index--;
         }
         
         index++;
      }
      return ((C - col)*height) + height - temp;
   
   
   }
}