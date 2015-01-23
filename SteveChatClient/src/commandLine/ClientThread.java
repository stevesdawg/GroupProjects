package commandLine;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;


public class ClientThread extends Thread {
	
	private Socket s;
	private boolean open;
	private OutputStream out;
	private InputStream in;
	
	private ArrayBlockingQueue<String> outQueue;
	private ArrayBlockingQueue<String> inQueue;
	
	public ClientThread(Socket socket, ArrayBlockingQueue<String> out, ArrayBlockingQueue<String> in) throws IOException
	{
		this.s = socket;
		open = true;
		this.in = s.getInputStream();
		this.out = s.getOutputStream();
		outQueue = out;
		inQueue = in;
	}
	
	public void start()
	{
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Type a message. Type CLOSE to exit.");		
		while(open)
		{
			try {
				String message = this.getIncomingMessages();
				System.out.println(message);
				inQueue.put(message);
			} catch (IOException | InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			String command = "";
			try {
				command = outQueue.take();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			switch (command) {
			case "CLOSE":
				try {
					this.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			default:
				try {
					this.sendMessage(command);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
		
		keyboard.close();
	}
	
	public void sendMessage(String message) throws IOException
	{
		PrintWriter outWriter = new PrintWriter(out);
		
		outWriter.println(message);
		outWriter.flush();
	}
	
	public String getIncomingMessages() throws IOException
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		
		String line = "";
		
		if((line = reader.readLine()) != null)
			return line;
		return "";
	}
	
	public void close() throws IOException
	{
		open = false;
		s.close();
	}

}
