package clientServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

public class Server {
	
	public static void main(String[] args) throws IOException {
		
		ArrayList<Socket> sockets = new ArrayList<Socket>();
		ArrayBlockingQueue<String> messagePool = new ArrayBlockingQueue<String>(40);
		
		ServerSocket socket = new ServerSocket(1809);
		new Thread(new ServerSideOutput(messagePool, sockets)).start();
		while(true)
		{
			Socket incomingSocket = socket.accept();
			System.out.println("Socket connected from: " + incomingSocket.getInetAddress());
			sockets.add(incomingSocket);
			new Thread(new ServerSideInput(incomingSocket, messagePool)).start();
		}
		
	}

}
