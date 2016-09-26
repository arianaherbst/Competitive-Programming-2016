import java.util.*;
public class Backtracking_NQueens {

    static int N;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        fill(new State(new int[N][N], 0));   
    }
    public static boolean fill(State st)
    {
        if (st.complete())
        {
            print(st);
            return true;
        }
        else
        {
            int row = st.nextRow;
            //try to find a spot for a queen
            for (int k = 0; k < N; k++)
            {
                
                //may have found a place for a queen in this row
                if (st.board[row][k] == 0)
                {
                    //make each following spot be unavailable
                    State copy = new State(copy(st), st.nextRow);
                    copy.board[row][k] = 1;
                    for (int r = row + 1; r < N; r++)
                    {
                        int c = r - row;
                        if (k + c < N)
                            copy.board[r][k + c] = -1;
                        if (k - c >= 0)
                            copy.board[r][k - c] = -1;
                        copy.board[r][k] = -1;
                    }
                    if(fill(new State(copy.board, st.nextRow + 1)))
                        return true;
                    else
                    {
                        st.board[row][k] = 0;
                    }
                }
            }
            return false;
        }
        
    }
    public static int[][] copy(State st)
    {
        int[][] copy = new int[N][N];
        for (int r = 0; r < N; r++)
        {
            int[] temp = st.board[r].clone();
            copy[r] = temp;
        }
        return copy; 
    }
    public static void print(State st)
    {
        StringBuilder sb = new StringBuilder();
        for(int[] r : st.board)
        {
            
            for(int c : r)
            {
                if (c == 1)
                {
                    sb.append("X");
                }
                else
                {
                    sb.append(".");
                }
            }
            sb.append("\n");
        }
        System.out.print(sb + "\n\n");
        
    }
    
    
    public static class State
    {
        int[][] board;
        int nextRow;
        
        public State(int[][] BOARD, int row)
        {
            board = BOARD;
            nextRow = row;
        }
        public boolean complete()
        {
            return nextRow == N;
        }
    }
}