import java.util.*;
/**
 * https://pcs.spruett.me/problems/57
 * @author Ariana Herbst
 * August 23rd, 2016
 */
public class Tries_RandomStrings {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> words = new ArrayList<String>();

        int N = sc.nextInt();
        for (int n = 0; n < N; n++)
        {
            words.add(sc.next());

        }
        TrieNode root = new TrieNode(false);
        TrieNode curr, child;
        for (String str : words)
        {
            curr = root;
            for (int i = 0; i < str.length(); i++)
            {
                child = curr.children.get(str.charAt(i));
                if (child != null)
                {
                    curr = child;
                    if (i == str.length() - 1)
                    {
                        curr.endOfWord = true;
                    }
                }
                else
                {

                    if (i == str.length() - 1)
                    {
                        child = new TrieNode(true);
                    }
                    else
                    {
                        child = new TrieNode(false);
                    }
                    curr.children.put(str.charAt(i), child);
                    curr = child;
                }
            }
        } ///done building trie

        String in = sc.next();
        curr = root;
        int c = 0;
        int j;
        TrieNode k;
        for (int i = 0; i < in.length(); i++)
        {
            j = i + 1;
            k = curr.children.get(in.charAt(i));
            while (k != null)
            {
                if (k.endOfWord)
                {
                    c++;
                }

                curr = k;
                k = curr.children.get(in.charAt(j));
                j++;
            }

        }
        System.out.print(c);



    }
    public static class TrieNode
    {
        Map<Character, TrieNode> children;
        boolean endOfWord;

        public TrieNode(boolean end)
        {
            children = new HashMap<Character, TrieNode>();
            endOfWord = end;
        }
    }  


}





