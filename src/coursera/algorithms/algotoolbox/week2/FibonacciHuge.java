package coursera.algorithms.algotoolbox.week2;

import java.util.*;

public class FibonacciHuge {
    
    private static long getPisanoPeriod(long m) {
        
        long previous = 0, current = 1, next = 1;
        long period = 0;
        
        for(;period < 2000; period++) {
            next = (previous + current) % m;
            previous = current;
            current = next;
            if((current == 1) && (previous == 0)) {
                break;
            }
        }
        return period + 1;
    }
    
    private static long getFibonacciHuge(long n, long m) {
        if (n <= 1)
            return n;

        n = n % getPisanoPeriod(m);
        
        long previous = 0;
        long current  = 1;
        long result = n;

        for (long i = 0; i < n - 1; ++i) {
            result = (previous + current) % m;
            previous = current;
            current = result;
        }

        return result % m;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long n = scanner.nextLong();
        long m = scanner.nextLong();
        System.out.println(getFibonacciHuge(n, m));
    }
}