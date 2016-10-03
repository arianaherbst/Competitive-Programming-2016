import java.util.*;
/**
 * http://codeforces.com/contest/723/problem/D
 * @author Ariana Herbst
 * October 3rd, 2016
 */
public class FloodFill_LakesInBerland {

	static int N, M , K;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		K = sc.nextInt();
		boolean[][] area = new boolean[N][M];
		sc.nextLine();
		for (int r = 0; r < N; r++)
		{
			String next = sc.next();
			for (int c = 0; c < M; c++)
			{
				if (next.charAt(c) == '.')
					area[r][c] = true;
			}
		}
		boolean[][] copy = new boolean[N][M];
		for (int i = 0; i < N; i++)
			copy[i] = Arrays.copyOf(area[i], M);


		ArrayList<Lake> lakes = new ArrayList<Lake>();
		for (int r = 0; r < N; r++)
		{
			for (int c = 0; c < M; c++)
			{
				if (copy[r][c])
				{
					int size = fill(r, c, copy);
					if (size != 0)
					{
						lakes.add(new Lake(new Point(r, c), size));
					}
				}
			}
		}

		int lakesToRemove = lakes.size() - K;
		Collections.sort(lakes);
		int landAdded = 0;
		for (int k = 0; k < lakesToRemove; k++)
		{
			Lake l = lakes.get(k);
			landAdded += l.size;
			fill(l.p.r, l.p.c, area);

		}
		System.out.print(landAdded);
		StringBuilder sb = new StringBuilder();
		for (boolean[] row : area)
		{
			sb.append("\n");
			for (boolean b : row)
			{
				if (b)
					sb.append("."); //if it's a lake
				else
					sb.append("*");
			}
		}
		System.out.print(sb);
	}

	public static int fill(int r, int c, boolean[][] arr)
	{
		Queue<Point> explore = new ArrayDeque<Point>();
		explore.add(new Point(r, c));
		int size = 0;
		while (!explore.isEmpty())
		{
			Point p = explore.poll();
			if (arr[p.r][p.c]) {
				if (isBorder(p.r, p.c))		//not a lake
				{
					do
					{
						if (arr[p.r][p.c]) {
							arr[p.r][p.c] = false;
							for (int[] d : dirs)
							{
								if (isValid(p.r + d[0], p.c + d[1]))
									explore.add(new Point(p.r + d[0], p.c + d[1]));

							}
							arr[p.r][p.c] = false;		//turn everything into land to make sure we don't check this again.
						}
						p = explore.poll();
					}  while (p != null);
					return 0;	
				}
				//if this is water, keep exploring
				arr[p.r][p.c] = false;
				size++;
				for (int[] d : dirs)
				{
					if (isValid(p.r + d[0], p.c + d[1]))
						explore.add(new Point(p.r + d[0], p.c + d[1]));
				}
			}
		}
		return size;
	}

	public static int[][] dirs = { {1, 0}, {-1, 0}, {0, 1}, {0, -1} }; 

	public static class Lake implements Comparable<Lake>{
		Point p;
		int size;
		public Lake (Point p, int size) { this.p = p; this.size = size; }
		public int compareTo(Lake other) { return Integer.compare(this.size, other.size); }
	}


	public static class Point {
		int r, c;
		public Point (int r, int c) { this.r = r;  this.c = c;}
	}

	public static boolean isValid(int r, int c)
	{
		if (r >= 0 && r < N && c >= 0 && c < M)
			return true;
		return false;
	}


	public static boolean isBorder(int r, int c)
	{
		if (c == 0 || c == M - 1 || r == 0 || r == N - 1)
			return true;
		return false;

	}

}
