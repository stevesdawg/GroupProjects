package clientServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

public class ClientSideOutput implements Runnable {
	
	private PrintWriter writer;
	private ArrayBlockingQueue<String> outgoingMessages;
	
	public ClientSideOutput(Socket s, ArrayBlockingQueue<String> out, String username) throws IOException
	{
		writer = new PrintWriter(s.getOutputStream());
		outgoingMessages = out;
		writer.println("#$! " + username + " JOINED");
		writer.flush();
	}
	
	public void run()
	{
		while(true)
		{
			try {
				pushMessages();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void pushMessages() throws InterruptedException
	{
		String message = outgoingMessages.take();
		writer.println(message);
		writer.flush();
	}

}
