import java.util.*;
/**
 * https://pcs.spruett.me/problems/116
 * @author Ariana Herbst
 * October 21, 2016
 */
public class Recursion_TheEndOfTheWorld {
	static Stack<Integer>[] stacks;
	static long count;
	static char[] c;
	static long[] moves = new long[63];
	public static void main(String[] args) {
		createMoves();
		Scanner sc = new Scanner(System.in);
		String next = sc.next();
		while (!next.equals("X")) {
			count = 0;
			c = next.toCharArray();
			char prev = 'B';
			int i = c.length - 1;	
			boolean subtract = false;
			for (; i >= 0; i--)
			{
				if (c[i] != prev) {
					count += subtract? -1 *moves[i] : moves[i];
					subtract = subtract? false : true;
				}
				prev = c[i];
			}
			System.out.println(count);
			next = sc.next();
		}
	}

	public static void createMoves()
	{
		moves[0] = 1;
		for (int i = 1; i < moves.length; i++) {
			moves[i] = moves[i - 1] * 2 + 1;
		}
	}

}