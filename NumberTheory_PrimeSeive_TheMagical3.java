import java.util.*;
import java.io.*;
/**
 * https://open.kattis.com/problems/magical3
 * @author Ariana Herbst and Daniel Moyer
 * October 22, 2016
 */
public class NumberTheory_PrimeSeive_TheMagical3 {
    private static List<Integer> primes;
    
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder output = new StringBuilder();
        String nextLine;
        String newLine = System.lineSeparator();
        PrimeSieve ps = new PrimeSieve(46350);

        primes = ps.getPrimeNumbers();
        while ((nextLine = input.readLine()) != null)
        {
            int N = Integer.parseInt(nextLine);
            if (N == 0)
                break;

            else if (N == 3) {
                output.append(4);
                output.append(newLine);
                continue;
            }
            N -= 3;
            int k = smallestFactor(N);
            if (k < 4)
                output.append("No such base");

            else
                output.append(k);

            output.append(newLine);
        }
        System.out.print(output.toString());
    }

    private static int smallestFactor(int k)
    {
        int i = 4;
        for (i = 4; i <= 9; i++) {
            if (k % i == 0)
                return i;
        }
        for (i = 0; i < primes.size() && primes.get(i)*primes.get(i) <= k; i++) {
            if (k % primes.get(i) == 0)
                return primes.get(i);
        }
        if (k % 3 == 0 && (k / 3 ) >= 4) {
            return k / 3;
        }
        if (k % 2 == 0 && (k / 2) >= 4) {
            return k / 2;
        }
        return k;
    }
    
//The following code is taken from Virginia Tech ACM ICPC Handbook//

    private static class PrimeSieve {
        private boolean[] isComposite;
        private int max;
        private PrimeSieve(int max) {
            this.max = max;

            isComposite = new boolean[(max + 1) >> 1];
            isComposite[0] = true;

            for (int i = 3; i * i <= max; i += 2) {
                for (int j = i * i; j <= max; j += i << 1) {
                    isComposite[j >> 1] = true;
                }
            }
        }

        private boolean isPrime(int num) {
            boolean result = false;
            if ((num & 1) == 0) {
                result = (num == 2);
                return result;
            } else {
                return !isComposite[num >> 1];
            }
        }
        
        private ArrayList<Integer> getPrimeNumbers() {
            int pi = (int) (Double.valueOf(max) / Math.log(max));
            ArrayList<Integer> primeNums = new ArrayList<Integer>(pi);
            // primeNums.add(2);
            for (int i = 7; i <= max; i+= 2) {
                if (isPrime(i)) {
                    primeNums.add(i);
                }
            }
            return primeNums;
        }
    }
}