package distributedFactor;

import java.math.BigInteger;
import java.util.ArrayList;

public class FactorerService implements Runnable {
	BigInteger numberToFactor, range, number;
	static BigInteger numOfThreads = BigInteger.ZERO;
	static int totalNumberOfThreads = 0;
	static boolean found = false;
	static ArrayList<BigInteger> factors = null;
	static int tNum, count = 0;

	public FactorerService(BigInteger n, int clientNum, int totalNumOfClients) {
		count++;
		numberToFactor = n;
		totalNumberOfThreads = 4 * totalNumOfClients;
		// increases the thread count by four for every client already connected
		tNum = ((clientNum - 1) * 4) + count;
		range = BigInteger.valueOf(((tNum * 2) + 3) - 2);
		factors = new ArrayList<BigInteger>();
	}

	public void run() {

		while (!numberToFactor.mod(range).equals(BigInteger.ZERO)
				&& range.doubleValue() < numberToFactor.doubleValue() / 2
				&& !found) {
			range = range.add(BigInteger.valueOf(2 * totalNumberOfThreads));
		}

		if (numberToFactor.mod(range).equals(BigInteger.ZERO)) {
			found = true;
			factors.add(range);
			factors.add(numberToFactor.divide(range));
			System.out.println(range + "  "
					+ numberToFactor.divide(range).toString());
		}

	}

	public static ArrayList<BigInteger> getFactors() {
		return factors;
	}

	public static boolean isFound() {
		return found;
	}

}
