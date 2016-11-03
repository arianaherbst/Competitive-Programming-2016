import java.util.*;
/**
 * https://code.google.com/codejam/contest/dashboard?c=11254486
 * @author Ariana Herbst
 * April 30, 2016
 */
public class HashTable_CodeJam1B_Digits {

	static int N;
	public static List<String> in;
	static HashMap<Integer, Integer> nums;
	static Map<Character, Integer> let;
	public static void main(String[] args) {
		StringBuilder output = new StringBuilder();
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		in = new ArrayList();
		for (int i = 0; i < N; i++)
		{
			in.add(sc.next());
		}
		
		
		//decipher
		
		for (int i = 0; i < N; i++) { 
			let = new HashMap();
			for (int j = 0; j < in.get(i).length(); j++) {
				Character temp = in.get(i).charAt(j);
				Integer val = let.get(temp);
				if ( val != null) {
					let.put(temp, val + 1);
				} else {
					let.put(temp, 1);
				}
			}
			
			nums = new HashMap<Integer, Integer>();
			nums.put(0, getNums('Z', "ZERO"));
			nums.put(2, getNums('W', "TWO"));
			nums.put(6, getNums('X', "SIX"));
			nums.put(8, getNums('G', "EIGHT"));
			nums.put(3, getNums('H', "THREE"));
			nums.put(7, getNums('S', "SEVEN"));
			nums.put(5, getNums('V', "FIVE"));
			nums.put(4, getNums('F', "FOUR"));
			nums.put(1, getNums('O', "ONE"));
			nums.put(9, getNums('I', "NINE"));
			
			StringBuilder testCase = new StringBuilder();
			for(int k = 0; k < 10; k++)
			{
				Integer temp = nums.get(k);
				int freq;
				if (temp == null)
				{
					freq = 0;
				}
				else {
					freq = temp;
				}
				for(int n = 0; n < freq; n++)
				{
					testCase.append("" + k);
				}
				
			}
			output.append("Case #" + (i + 1) + ": " + testCase + "\n");
			//System.out.print(output);
			let.clear();
			
			
		}
		String printme = output.substring(0, output.length() - 1);
		System.out.print(printme);
		
		
	}
	public static int getNums(char c, String word)
	{
		Integer freq = let.get(c);
		
		if ((freq != null) && !(freq.equals(0))) {
			//System.out.print( c );
			for(int i = 0; i < word.length(); i++)
			{
				//System.out.print(word + " " + i);
				int t = let.get(word.charAt(i));
				let.put(word.charAt(i), t - freq);
			}
			return freq;
		}
		return 0;	
	}
}
