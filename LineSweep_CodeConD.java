import java.util.*;
/**
 * Bloomberg Codecon 2016
 * 	  Given a list of event starting and ending times, what is
 * the maximum number of events occurring at any time?
 * 	  Input: the first line will have an integer N for the number
 * of events listed.  The following N lines will list the starting
 * and ending times of the i-th event with the format
 * 		[start time] | [end time]
 *    Output:  The maximum number of events occurring at one time.
 * @author Ariana Herbst
 *	September 22, 2016
 */

public class LineSweep_CodeConD {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		ArrayList<Event> events = new ArrayList<Event>();
		for (int n = 0; n < N; n++)
		{
			String next = sc.next();
			next = next.substring(next.indexOf("|") + 1);
			String s1 = next.substring(0, next.indexOf("|"));
			String s2 = next.substring(next.indexOf("|"));
			s2 = s2.substring(1);
			events.add(new Event(Integer.parseInt(s1), 1));
			events.add(new Event(Integer.parseInt(s2), 0));
			
		}
		Collections.sort(events);
		int c = 0;
		int m = 0;
		for (int i = 0; i < events.size(); i++)
		{
			Event e = events.get(i);
			if (e.state == 0)
			{
				c--;
			}
			else
			{
				c++;
			}
			m = Math.max(m, c);
		}
		System.out.print(m);

	}
	
	static class Event implements Comparable<Event>
	{
		int time;
		int state;
		
		public Event(int t, int s)
		{
			time = t;
			state = s;
		}
		
		public int compareTo(Event other)
		{
			int c = Integer.compare(time, other.time);
			if (c != 0)
			{
				return c;
			}
			return Integer.compare(state, other.state);
		}
	}

}
