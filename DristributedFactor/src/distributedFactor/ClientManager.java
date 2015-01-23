package distributedFactor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Scanner;

public class ClientManager {
	private Socket s;
	private int startNum;
	private InputStream is;
	private OutputStream os;
	private Scanner in;
	private PrintWriter out;
	private BigInteger toFactor;
	private static int clientNum = 0;
	private static int totalNumOfClients;
	private int cNum;
	public ClientManager(Socket s, BigInteger toFactor,int numOfClients) throws IOException {
		clientNum++;
		// sets the client number for this client
		cNum=clientNum;
		this.s = s;
		this.toFactor = toFactor;
		is = s.getInputStream();
		os = s.getOutputStream();
		in = new Scanner(is);
		out = new PrintWriter(os);
		System.out.println("client manager instantiated for client number "
				+ cNum);
		totalNumOfClients=numOfClients;

	}

	public void service() {
		// sends the number to be factored and the client number
		
		System.out.println("number to factor is " + toFactor.toString()+"\n"
				+ "client number is " + cNum);
		out.print(toFactor.toString()+"\n"+cNum+"\n"+totalNumOfClients+"\n");

		out.flush();
		
		// waits for one of the clients to return the factors and then closes
		// the socket
		while (true) {
			if (in.hasNext()) {
				System.out.println("the factors are " + in.nextLine());

			}
		}

	}

	public void closeAll() throws IOException {
		s.close();
		in.close();
		out.close();
		is.close();
		os.close();
	}
}
