package distributedFactor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
	private static BigInteger toFactor=new BigInteger("");
	private static int startNum = 3;
	private static int PORT = 8888;

	private static ArrayList<ClientManager> cm = new ArrayList<ClientManager>();

	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(PORT);
		System.out.println("waiting for clients....");
		Scanner kb=new Scanner(System.in);
		System.out.println("how many clients will connect?");
		int numOfClients=kb.nextInt();
		try {
			for (int x =0;x<numOfClients;x++) {
				Socket s = server.accept();
				cm.add(new ClientManager(s, toFactor,numOfClients));
				System.out.println("client connected");
			}
				for (ClientManager c : cm)
					c.service();

			
		} finally {
			for (ClientManager c : cm)
				c.closeAll();
		}

	}
}
