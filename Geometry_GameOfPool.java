import java.util.*;
import java.awt.*;
/**
 * https://pcs.spruett.me/problems/23
 * @author Ariana Herbst
 * March 31st, 2016
 */
public class Geometry_GameOfPool
{
   static double X;
   static double Y;
   static double EPS = 1e-10;
   static ArrayList<Double> angles;
   static ArrayList<Point> points;
   public static void main(String[] args)
   {
      Scanner sc = new Scanner(System.in);
      X = sc.nextDouble();
      Y = sc.nextDouble();
      angles = new ArrayList<Double>();
      points = new ArrayList<Point>();
      points.add(new Point( 0, 2));
      points.add(new Point( 1, 2));
      points.add(new Point( 2, 1));
      points.add(new Point( 2, 0));
      points.add(new Point( 1, -1));
      points.add(new Point( 0, -1));
      points.add(new Point( -1, 0));
      points.add(new Point( -1, 1));
      
      for(Point p : points)
      {
         angles.add(getAngle(p));
      }
      Collections.sort(angles);
      
      for(Double d : angles)
      {
         System.out.print( d + "\n");
      }
      
   }
   
   public static double getAngle(Point target)
   {
      double angle = Math.toDegrees(Math.atan2(target.y - Y, target.x - X));
      
      if(angle < 0)
      {
         angle += 360;
      }
      return angle;
   }
   
   static boolean floatEq(double a, double b)
   {
      return Math.abs(a - b) <= EPS;
   }
}