package distFactoring;

import java.math.BigInteger;
import java.net.Socket;
import java.util.ArrayList;

public class Factorer implements Runnable
{
	BigInteger end;
	BigInteger number;
	BigInteger start;
	private final BigInteger TWO = new BigInteger("2");

	public Factorer(Socket s, BigInteger num, BigInteger start, BigInteger end)
	{
		this.end= end;
		number = num;
		this.start = start;
		if (start.mod(TWO).equals(BigInteger.ZERO))
		{
			start = start.subtract(BigInteger.ONE);
		}
		System.out.println(start);
		System.out.println(end);
	}

	public void run() 
	{
		ArrayList<BigInteger> factors = new ArrayList<BigInteger>();

		while (start.compareTo(end) <= 0)
		{
			if (number.mod(start).equals(BigInteger.ZERO))
			{
				factors.add(start);
				BigInteger other = number.divide(start);
				if (other != start)
				{
					factors.add(other);
				}

			}
			start = start.add(BigInteger.ONE);
		}

		if (factors.size() > 0)
			System.out.println("The factors are: " + factors.toString());
	}
}
