package guiBased;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

public class OutputHandler extends Thread {
	
	private Socket socket;
	private ArrayBlockingQueue<String> outQueue;
	private PrintWriter writer;
	private boolean open;
	
	public OutputHandler(Socket s, ArrayBlockingQueue<String> out) throws IOException
	{
		this.socket = s;
		this.outQueue = out;
		writer = new PrintWriter(socket.getOutputStream());
		open = true;
	}
	
	public void start()
	{
		while(open)
			try {
				sendMessage();
			} catch (InterruptedException | IOException e) {
				e.printStackTrace();
			}
	}
	
	public void sendMessage() throws InterruptedException, IOException
	{
		String message = outQueue.take();
		writer.println(message);
		writer.flush();
		if(message.equals("CLOSE"))
		{
			this.close();
			return;
		}
	}
	
	private void close() throws IOException
	{
		open = false;
		writer.close();
		socket.close();
	}

}
