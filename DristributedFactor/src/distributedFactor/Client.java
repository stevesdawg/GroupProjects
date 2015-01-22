package distributedFactor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
	private final static int PORT = 8888;
	private static BigInteger numToFactor;
 
	private static ArrayList<Thread> tList=new ArrayList<Thread>();

	public static void main(String[] args) throws UnknownHostException,
			IOException {
		 int numOfClient;
		Socket s = null;
		InputStream is = null;
		OutputStream os = null;
		Scanner in = null;
		PrintWriter out = null;
		try {
			s = new Socket("127.0.0.1", PORT);
			is = s.getInputStream();
			os = s.getOutputStream();
			in = new Scanner(is);
			out = new PrintWriter(os);
			// gets the string representation of the BigInt, should change this
			// so
			// it gets an arraylist and then has the start num and the bigInt
			String BIString = in.nextLine();
			String intString = in.nextLine();
			int totalClients=Integer.parseInt(in.nextLine());
			numOfClient = Integer.parseInt(intString);
			System.out.println(BIString);
			numToFactor = new BigInteger(BIString);
			
			// creates new FactorerService with numToFactor and StartNum
			// provided by
			// the server
			
			// adds four threads to the arraylist
			for (int x = 0; x < 4; x++)
				tList.add(new Thread(new FactorerService(numToFactor,numOfClient,totalClients )));
			// starts all threads
			for (Thread t : tList)
				t.start();
			// waits until the factors are found by one of the threads
			while (!FactorerService.isFound()) {
			}
			// determines if the factors were found and if they were, prints
			// them to
			// the output stream
			if (FactorerService.isFound()) {
				out.print(FactorerService.getFactors().get(0).toString() + "   "
						+ FactorerService.getFactors().get(1).toString());
				out.flush();
			}
		} finally {
			s.close();
			in.close();
			out.close();
			os.close();
			is.close();
		}

	}

}
