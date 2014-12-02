import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ClientThread extends Thread {
	
	private Socket s;
	private boolean open;
	
	public ClientThread(Socket socket)
	{
		this.s = socket;
		open = true;
	}
	
	public void start()
	{
		Scanner keyboard = new Scanner(System.in);
		while(open)
		{
			try {
				System.out.println(this.getIncomingMessages());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			System.out.println("Type a message. Type CLOSE to exit.");
			String command = keyboard.next();
			
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
		OutputStream out = s.getOutputStream();
		PrintWriter outWriter = new PrintWriter(out);
		
		outWriter.println(message);
		outWriter.flush();
	}
	
	public String getIncomingMessages() throws IOException
	{
		InputStream in = s.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		
		String line = "";
		String incomingMessages = "";
		
		while((line = reader.readLine()) != null)
			incomingMessages += line;
		
		return incomingMessages;
	}
	
	public void close() throws IOException
	{
		open = false;
		s.close();
	}

}
