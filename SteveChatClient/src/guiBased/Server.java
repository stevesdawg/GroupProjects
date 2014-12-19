package guiBased;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

public class Server {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		ServerSocket server = new ServerSocket(9090);
		ArrayBlockingQueue<Socket> clients = new ArrayBlockingQueue<Socket>(20);
		
		while(true)
		{
			System.out.println("How Many Clients: " + clients.size());
			Socket clientConnecting = server.accept();
			ClientManager client = new ClientManager(clientConnecting, clients);
		}

	}

}
