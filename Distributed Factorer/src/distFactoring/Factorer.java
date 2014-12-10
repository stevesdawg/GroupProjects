package distFactoring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigInteger;
import java.net.Socket;
import java.util.ArrayList;

public class Factorer implements Runnable
{
	private BigInteger end;
	private BigInteger number;
	private BigInteger start;
	
	private Socket socket;
	private boolean started;
	
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
		
		this.socket = s;
		started = false;
	}
	
	public Socket getSocket()
	{
		return socket;
	}
	
	public String getIncomingMessages() throws IOException
	{
		InputStream in = socket.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		
		String line = "";
		String incoming = "";
		
		while((line = reader.readLine()) != null)
			incoming += line;
		
		return incoming;
	}

	public void run() 
	{
		
	}
	
	public void factor()
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
