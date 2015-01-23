package guiBased;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;


public class ClientManager extends Thread {
	
	private Socket s;
	private ArrayBlockingQueue<Socket> clientSockets;
	private boolean open;
	private BufferedReader reader;
	
	public ClientManager(Socket socket, ArrayBlockingQueue<Socket> sockets) throws InterruptedException, IOException
	{
		this.s = socket;
		clientSockets = sockets;
		open = true;
		clientSockets.put(s);
		reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
		System.out.println("How Many Clients: " + clientSockets.size());
	}
	
	public void start()
	{
		PrintWriter a = null;
		try {
			a = new PrintWriter(s.getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		a.println("Connected to: " + s.getLocalAddress());
		a.flush();
		while(open)
		{
			String messageToSend = "";
			try {
				messageToSend = getIncomingMessages();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			switch (messageToSend)
			{
			case "CLOSE":
				try {
					close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			default:
				try {
					sendMessageToOtherClients(messageToSend);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}
	
	public String getIncomingMessages() throws IOException
	{
		String line = reader.readLine();
		while(line.length() <= 0)
		{
			line = reader.readLine();
		}
		System.out.println("SERVER RECEIVED: " + line);
		return line;
	}
	
	public void sendMessageToOtherClients(String message) throws IOException
	{
		for(Socket s : clientSockets)
		{
			if(this.s != s)
			{
				PrintWriter writer = new PrintWriter(s.getOutputStream());
				writer.println(message);
				writer.flush();
			}
		}
	}
	
	public void close() throws IOException
	{
		open = false;
		reader.close();
		s.close();
		clientSockets.remove(s);
	}

}
