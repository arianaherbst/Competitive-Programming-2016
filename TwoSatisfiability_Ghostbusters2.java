import java.util.*;
/*
 * Midatlantic Regionals 2016
 * https://pcs.spruett.me/problems/145
 * @author Ariana Herbst
 * November 19, 2016
 */
public class TwoSatisfiability_Ghostbusters2 {

	static int N;
	static ArrayList<Cor> cors = new ArrayList<Cor>();
	static ArrayList<Edge> xShared = new ArrayList<Edge>();
	static ArrayList<Edge> yShared = new ArrayList<Edge>();
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		for (int n = 0; n < N; n++)	{
			cors.add(new Cor(n, sc.nextInt(), sc.nextInt()));
		}
		buildMap();
		int ans = findPower();
		System.out.println(ans >= 500_000 ? "UNLIMITED" : ans);
	}
	
	public static int findPower()
	{
		int low = 0;
		int high = 500_001;
		int mid = -1;
		boolean midVal = false;
		while (low < high)	{
			mid = (low + high) >>> 1; //avoid overflow
			midVal = findSat(mid);
			if (midVal)
				low = mid + 1;
			else
				high = mid;
		}
		return midVal ? mid : mid - 1; 
	}
	
	public static boolean findSat(int power)
	{
		TwoSAT twoSAT = new TwoSAT(2*N);
		for (Edge e : xShared)	{
			if (e.dist < power * 2 + 1)
				twoSAT.setOr(e.u*2, e.v*2);
		}
		for (Edge e : yShared)	{
			if (e.dist < power * 2 + 1)
				twoSAT.setOr(e.u*2 + 1, e.v*2 + 1);
		}
		for (int i = 0; i < 2*N; i+=2)	{
			twoSAT.setXor(i, i + 1);
		}
		return twoSAT.isSatisfiable();
	}
	
	
	public static class Edge	{
		int u, v;
		int dist;
		public Edge(int u, int v, int dist)	{
			this.u = u; this.v = v; this.dist = dist;
		}
	}
	
	public static void buildMap()
	{
		//add edges between nodes that share an x coordinate
		ArrayList<Cor> clone = new ArrayList<Cor>();
		for (Cor c : cors)	{
			clone.add(new Cor(c.index, c.x, c.y));
		}
		Collections.sort(clone, new Comparator<Cor>(){
			public int compare(Cor a, Cor b)
			{
				int c = Integer.compare(a.x, b.x);
				return c != 0 ? c : a.index - b.index;
			}
		});
		for (int n = 0; n < N - 1; n++)
		{
			if (clone.get(n).x == clone.get(n + 1).x)
			{
				int k = n + 1;
				while ( k < N && clone.get(k).x == clone.get(n).x)	{
					k++;
				}
				for (int i = n; i < k - 1; i++)
				{
					for (int j = i + 1; j < k; j++)
					{
						if (i != j)	{
							int a = clone.get(i).index;
							int b = clone.get(j).index;
							xShared.add(new Edge(a, b, Math.abs(clone.get(i).y - clone.get(j).y)));
						}
					}
				}
				n = k - 1;

			}
		}
		//add edges between nodes that share a y coordinate
		Collections.sort(clone, new Comparator<Cor>(){
			public int compare(Cor a, Cor b)
			{
				int c = Integer.compare(a.y, b.y);
				return c != 0 ? c : a.index - b.index;
			}
		});
		for (int n = 0; n < N - 1; n++)
		{
			if (clone.get(n).y == clone.get(n + 1).y)
			{
				int k = n + 1;
				while (k < N && clone.get(k).y == clone.get(n).y)	{
					k++;
				}
				for (int i = n; i < k - 1; i++)
				{
					for (int j = i + 1; j < k; j++)
					{
						if (i != j)	{
							int a = clone.get(i).index;
							int b = clone.get(j).index;
							yShared.add(new Edge(a, b, Math.abs(clone.get(i).x - clone.get(j).x)));
						}
					}
				}
				n = k - 1;
			}
		}
	}
	
	static class Cor	{
		int index;
		int x, y;
		public Cor(int index, int x, int y)
		{
			this.index = index;
			this.x = x; this.y = y;
		}
	}
	
	public static class SCC	{
		private boolean[] marked;	//marked[v] = has v been visited?
		private int[] id;			//id[v] = id of strong component containing v
		private int[] low;			//low[v] = low number of v
		private int pre;			//preorder number counter
		private int count;			//number of strongly-connected components
		private Stack<Integer> stack;
		private int[] sccsize;		//size of scc[i], only if desired
		private List<Integer> [] G;  //store underlying graph, only needed for reduce
		
		public SCC(List<Integer> [] G) 	{
			this.G = G;
			marked = new boolean[G.length];
			stack = new Stack<Integer>();
			id = new int[G.length];
			low = new int[G.length];
			sccsize = new int[G.length];
			for (int v = 0; v < G.length; v++)	{
				if (!marked[v])  dfs(G, v);
			}
		}
		
		private void dfs(List<Integer> [] G, int v)	{
			marked[v] = true;
			low[v] = pre++;
			int min = low[v];
			stack.push(v);
			for (int w : G[v])	{
				if (!marked[w])  dfs(G, w);
				if (low[w] < min) min = low[w];
			}
			if (min < low[v]) 	{	low[v] = min; return;	}
			int w;
			do 	{
				w = stack.pop();
				id[w] = count;
				sccsize[count]++;
				low[w] = G.length;
			}	while (w != v);
			count++;
		}
		
		//return the number of strongly conneted components
		public int count() 	{ return count;	}
		
		//are v and w strongly connected?
		public boolean stronglyConnected(int v, int w)	{
			return id[v] == id[w];
		}
		
		//in which strongly connected component is vertex v?
		public int id(int v)	{	return id[v];	}
		
		//how big is the strongly connected component with number #x
		//assert 0 <= x < count
		public int sccsize(int x)	{	return sccsize[x];	}
		
		/*
		 * Return reduced acyclic graph in which all SCC are collapsed into one node
		 */
		public List<Integer> [] reduce()	{
			Set<Integer> [] set = new Set[count];
			for (int i = 0; i < count; i++)
				set[i] = new HashSet<>();
			
			for (int i = 0; i < G.length; i++)
				for (Integer j : G[i])
					if (!stronglyConnected(i, j))
						set[id[i]].add(id[j]);
			
			List<Integer> [] r = new List[count];
			for (int  i = 0; i < count; i++)
				r[i] = new ArrayList<>(set[i]);
			
			return r;
		}
	}
	
	static class TwoSAT	{
		List<Integer>[] adj;	//implication graph, size 2 * N
		
		int N;
		/*
		 * N variables, 2*N nodes to represent var[i] and !var[i]
		 * following http://codeforces.com/blog/entry/16205 we represent
		 * X[i] at 2*i and !X[i] at 2*i+1
		 * 
		 * Convention: polarity 'true' means the var. polarity 'false' means !var
		 */
		
		private int getindex(int var, boolean polarity)	{
			return 2*var + (polarity ? 0 : 1);
		}
		
		@SuppressWarnings("unchecked")
		public TwoSAT(int N)	{
			this.N = N;
			adj = new List[2*N];
			for (int i = 0; i < 2*N; i++)
				adj[i] = new ArrayList<Integer>();
		}
		
		/*var1 xor var2 means var1 || var2 && !var1 || !var2 */
		void setXor(int var1, int var2)	{
			addClause(var1, true, var2, true);
			addClause(var1, false, var2, false);
		}
		
		void setOr(int var1, int var2)	{
			addClause(var1, true, var2, true);
		}
		
		/*
		 * Record a clause 'var1' || 'var2;, optionally !var1 or !var2
		 * 
		 * Since x || y == (!x -> y) && (!y -> x), we must add two edges
		 * to the implication graph.
		 * 
		 * Note: for usability, we set the boolean such that passing true
		 * means passing the actual value, and passing false means the negated value.
		 * 
		 * Pass 'polarity' to be true if you mean 'var'
		 * Pass 'polarity' to be false if you mmean '!var'
		 */
		void addClause(int var1, boolean polarity1, int var2, boolean polarity2)	{
			//!var1-> var2
			adj[getindex(var1, !polarity1)].add(getindex(var2, polarity2));
			// !var2 -> var1
			adj[getindex(var2, !polarity2)].add(getindex(var1, polarity1));
		}
		
		/* Fix a variable by adding an implication connecting either
		 * !X -> X or X -> !X for true and false resp. */
		void set(int var, boolean value)	{
			adj[getindex(var, !value)].add(getindex(var, value));
		}
		
		//Setting a variable to true is saying that !var -> var
		void setTrue(int var)	{
			set(var, true);
		}
		
		//Setting a variable to false is saying that var -> !var
		void setFalse(int var)	{
			set(var, false);
		}
		
		/*  Undo the last fixed value assignment for variable var or !var.
		 * This is useful if you need to set a variable to true/false, test
		 * satisfiability, and then undo the setting.
		 * Added for WF/2009/Minister's Mess.
		 */
		void removeLastFixedAssignment (int var, boolean value)	{
			int i = getindex(var, !value);
			adj[i].remove(adj[i].size()-1);
		}
		
		/*
		 * Determine satisfiability: if any x and !x are in the same SCC,
		 * the answer is false - why?  Because x -> @x && !x -> x is a contraduction.
		 */
		boolean isSatisfiable()	{
			SCC scc = new SCC(adj);
			for (int i = 0; i < N; i++)
				if (scc.stronglyConnected(2*i, 2*i + 1))
					return false;
			return true;
		}
		
		/**
		 * Determine satisfiability and return an assignment if satisfiable.
		 * See Aspvall, Plass, and Tarjan, IPL Vol 8(3) 1979
		 * A Linear-Time Algorithm for Testing the Truth of Certain
		 * Quantified Boolean Formulas.
		 */
		@SuppressWarnings("unchecked")
		boolean isSatisfiable(boolean [] vassn)	{
			SCC scc = new SCC(adj);
			int nS = scc.count();
			// compute dual components
			int [] dual = new int[nS];		// Scomp
			for (int i = 0; i < 2 * N; i += 2)	{
				dual[scc.id(i)] = scc.id(i^1);
				dual[scc.id(i^1)] = scc.id(i);
			}
			
			Boolean [] assn = new Boolean[nS];
			for (int S = 0; S < nS; S++)	{
				if (assn[S] == null)	{
					assn[S] = Boolean.TRUE;
					if (S == dual[S])
						return false;
					
					assn[dual[S]] = Boolean.FALSE;
				}
			}
			for (int i = 0; i < N; i++)
				vassn[i] = assn[scc.id(2*i)];
			return true;
		}
	}
}
