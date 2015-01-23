package clientServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

public class ServerSideOutput implements Runnable {
	
	private ArrayBlockingQueue<String> messagePool;
	private ArrayList<Socket> sockets;
	
	public ServerSideOutput(ArrayBlockingQueue<String> messages, ArrayList<Socket> sockets)
	{
		this.messagePool = messages;
		this.sockets = sockets;
	}
	
	public void run()
	{
		try {
			pushMessagesToAllClients();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void pushMessagesToAllClients() throws InterruptedException, IOException
	{
		for(Socket s : sockets)
		{
			if(true/*! (s == socket)*/)
			{
				String messageString = messagePool.poll();
				PrintWriter writer = new PrintWriter(s.getOutputStream());
				writer.println(messageString);
				writer.flush();
			}
		}
	}

}
