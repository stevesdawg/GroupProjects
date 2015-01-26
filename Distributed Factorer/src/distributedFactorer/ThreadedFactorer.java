package distributedFactorer;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;

import sun.awt.image.OffScreenImage;

/**
 * Created by steve on 1/24/15.
 */
public class ThreadedFactorer implements Runnable {

    private int startNumber;
    private int spacing;
    private BigInteger numberToFactor;
    private ArrayBlockingQueue<BigInteger> factors;

    public ThreadedFactorer(BigInteger num, int start, int space, ArrayBlockingQueue<BigInteger> factors)
    {
        this.startNumber = start;
        this.numberToFactor = num;
        this.spacing = space;
        this.factors = factors;
    }

    public void run()
    {
        try {
        	System.out.println("Factorer Started. START: " + startNumber + ". JUMP: " + spacing + ". BIG NUMBER: " + numberToFactor.toString());
            factor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void factor() throws InterruptedException {
        BigInteger start = new BigInteger(""+startNumber);
        BigInteger jump = new BigInteger(""+spacing);
        while(start.compareTo(numberToFactor) < 0 && !FactorerSideNetworkHandler.stopped)
        {
            if(numberToFactor.mod(start).equals(BigInteger.ZERO))
            {
            	if(factors.isEmpty())
            		factors.put(start);
                FactorerSideNetworkHandler.stopped = true;
                return;
            }
            start = start.add(jump);
        }
    }

}
