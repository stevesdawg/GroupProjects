package commandLine;
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
		while(open)
		{
			String inCommands = "";
			try {
				inCommands = this.getIncomingMessages();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			switch (inCommands) {
			case "CLOSE":
				try {
					this.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;

			default:
				try {
					this.sendMessageToClients(inCommands);
				} catch (IOException e) {
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
				PrintWriter outWriter = new PrintWriter(outStream);
				outWriter.println(message);
				outWriter.flush();
			}
		}
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
		outStreams.remove(out);
		out.close();
		s.close();
	}

}
