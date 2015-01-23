package clientServer;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

public class ServerSideInput implements Runnable {
	
	private Scanner in;
	private ArrayBlockingQueue<String> messages;
	
	public ServerSideInput(Socket s, ArrayBlockingQueue<String> messagePool) throws IOException
	{
		this.in = new Scanner(s.getInputStream());
		this.messages = messagePool;
	}
	
	public void run()
	{
		while(true)
		{
			try {
				readMessage();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void readMessage() throws IOException, InterruptedException
	{
		while(!in.hasNext())
		{}
		String messageString = in.nextLine();
		messages.add(messageString);
		System.out.println("MESSAGE: " + messageString);
	}

}
