import java.util.*;
/**
 * https://pcs.spruett.me/problems/33
 * @author Ariana Herbst
 * April 19, 2016
 */
public class SegmentTree_AccountMaximumQueries {
    static int N, M;
    static SegmentTree st;
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        st = new SegmentTree(new Interval(0, N - 1));
        
        for (int i = 0; i < M; i++)
        {
            if (sc.next().equals("ADD"))
            {
                st.update(sc.nextInt(), sc.nextInt());
            }
            else { //for MAX
                System.out.print(st.rangeQuery(sc.nextInt(), sc.nextInt()) + "\n");
            }   
        }   
    }
    
  //The following code is taken from Virginia Tech ACM ICPC Handbook//
    
    public static class SegmentTree {
        SegmentTree left, right;
        int val;
        Interval range;
        
        public static final int IDENTITY = Integer.MIN_VALUE;
        
        public int combine(int x, int y) {
            return Math.max(x, y);
        }
        
        public SegmentTree (int l, int r) {
            this(new Interval(l,r));
        }
        
        public SegmentTree (Interval i) {
            range = i;
            if (range.length() > 1) {
                left = new SegmentTree(range.leftInterval());
                right = new SegmentTree(range.rightInterval());
            }
        }
        
        public int rangeQuery(int l, int r){
            Interval range = new Interval(l, r);
            return rangeQuery(range);
        }
        
        public int rangeQuery(Interval i) {
            if (!this.range.intersects(i)) {
                return IDENTITY;
            }
            else if (this.range.contained(i)) {
                return this.val;
            }
            else {
                return combine(this.left.rangeQuery(i), this.right.rangeQuery(i));
            }
        }
        
        public void update(int i, int val) {
            if (range.length() == 1) {
                this.val += val;
            }
            else {
                if (this.range.leftInterval().containsPoint(i)) {
                    this.left.update(i, val);
                }
                else {
                    this.right.update(i,  val);
                }
                this.val = combine(this.left.val, this.right.val);
            }
        }
    }
        
        public static class Interval {
            final int left, right;
            
            public Interval(int l, int r) {
                left = l;
                right = r;
            }
            
            public int midPoint() {
                return (left + right)/2;
            }
            
            public int length() {
                return right - left + 1;
            }
            
            public boolean containsPoint(int i) {
                return this.left <= i && i <= this.right;
            }
            
            public Interval leftInterval() {
                return new Interval(left, midPoint());
            }
            
            public Interval rightInterval() {
                return new Interval(midPoint()+1, right);
            }
            
            public boolean contained(Interval i) {
                return i.left <= this.left && this.right <= i.right;
            }
            
            public boolean intersects(Interval i) {
                return !(i.right < this.left || this.right < i.left);
            }
        }   
}