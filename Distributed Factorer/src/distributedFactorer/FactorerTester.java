package distributedFactorer;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by steve on 1/24/15.
 */
public class FactorerTester {

    public static void main(String[] args)
    {
        ArrayBlockingQueue<BigInteger> factors = new ArrayBlockingQueue<BigInteger>(2);

        BigInteger number = new BigInteger("77");

        ArrayList<Thread> factorerThreads = new ArrayList<Thread>();
        int start = 3;
        for(int i = 0; i < 4; i++)
        {
            factorerThreads.add(new Thread(new ThreadedFactorer(number, start, 8, factors)));
            start += 2;
        }

        for(Thread t : factorerThreads)
            t.start();

        while(factors.size() < 2)
        {System.out.println("Still Factoring.");}
        if(factors.size() == 2)
            System.out.println(factors);
    }

}
