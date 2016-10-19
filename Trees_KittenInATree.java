import java.util.*;
/**
 * https://pcs.spruett.me/problems/44
 * @author Ariana Herbst
 * May 11, 2016
 */
public class Trees_KittenInATree {
   
   static Map<Integer, Integer> branches;
   static int target;
   static Set<Integer> possRoot;
   static int root;
   static Set<Integer> nodes;
   public static void main(String[] args) 
   {
      branches = new HashMap<Integer, Integer>();
      possRoot = new HashSet<Integer>();
      nodes = new HashSet<Integer>();
      Scanner sc = new Scanner(System.in);
      target = sc.nextInt();
      sc.nextLine();
      
      //makes an array slot for each number on each line
      String[] next =  sc.nextLine().split(" ");
      
      //builds the tree with children pointing to parents
      while (Integer.parseInt(next[0]) != -1)
      {
         //the parent node at the beginning of the line is a possible root for the entire tree
         //we find the actual root later
         possRoot.add(Integer.parseInt(next[0]));
         //maps each child to the parent
         for( int i = 1; i < next.length; i++)
         {
            if (!next[i].equals("")) {   //just in case weird stuff makes the scanner not work
               nodes.add(Integer.parseInt(next[i]));  //used to find the root later
               branches.put(Integer.parseInt(next[i]),Integer.parseInt(next[0]));  //children point to parent
            }
         }           
         next =  sc.nextLine().split(" ");                                                                                                                                           
      }
      
      //starts finding the root
      Iterator it = possRoot.iterator();
      root = -1;
      int j;
      while (it.hasNext())
      {
         j = (int) it.next();
         if (!nodes.contains(j))
            root = j;
      }
      //we now have the root
      //starts reaching for the root from the target
      solve(target);
   }
   
   //reaches for the parent node until it reaches the root (and is done)
   //this method is recursive
   public static void solve(Integer j)
   {
      System.out.print(j +  " ");
      Integer k = branches.get(j); //gets the parent node
      if (!k.equals(root))
      {
         solve(k); //calls "solve" for the parent node
      }
      else
      {
         System.out.print(k + ""); //done B-)
      }
   }
}