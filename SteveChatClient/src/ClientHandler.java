import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;


public class ClientHandler extends Thread {
	
	private Socket s;
	private OutputStream out;
	private InputStream in;
	private ArrayBlockingQueue<OutputStream> outStreams;
	private boolean open;
	
	public ClientHandler(Socket socket, ArrayBlockingQueue<OutputStream> outs) throws InterruptedException, IOException
	{
		this.s = socket;
		this.out = s.getOutputStream();
		this.in = s.getInputStream();
		this.outStreams = outs;
		outStreams.put(out);
	}
	
	public void start()
	{
		System.out.println("Client has been connected to this server.");
		PrintWriter writer = new PrintWriter(out);
		writer.println("Connected");
		writer.flush();
//		w.close();
		
		while(open)
		{
			String inCommands = "";
			try {
				inCommands = this.getIncomingMessages();
				System.out.println(inCommands);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			switch (inCommands) {
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
					System.out.println(inCommands);
					this.sendMessageToClients(inCommands);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
	}
	
	public void sendMessageToClients(String message) throws IOException
	{
		for(OutputStream outStream : outStreams)
		{
			if(outStream != out)
			{
				PrintWriter writer = new PrintWriter(out);
				writer.println(message);
				writer.flush();
				System.out.println(message);
			}
		}
	}
	
	public String getIncomingMessages() throws IOException
	{
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
		outStreams.remove(out);
		out.close();
		s.close();
	}

}
