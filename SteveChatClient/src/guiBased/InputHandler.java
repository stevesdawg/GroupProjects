package guiBased;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

public class InputHandler extends Thread {
	
	private Socket socket;
	private ArrayBlockingQueue<String> inQueue;
	private boolean open;
	private BufferedReader reader;
	
	public InputHandler(Socket socket, ArrayBlockingQueue<String> in) throws IOException
	{
		this.socket = socket;
		inQueue = in;
		open = true;
		reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
	}
	
	public void start()
	{
		while(open)
		{
			try {
				getIncomingMessages();
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void getIncomingMessages() throws IOException, InterruptedException
	{
		String line = reader.readLine();
		while(line.length() <= 0)
			line = reader.readLine(); 
		inQueue.put(line);
		System.out.println(line);
		if(line.equals("CLOSE"))
			this.close();
	}
	
	public void close() throws IOException
	{
		open = false;
		reader.close();
		socket.close();
	}

}
