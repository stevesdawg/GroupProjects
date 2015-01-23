package clientServer;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.JTextArea;

public class ClientSideInput implements Runnable {
	
	private Scanner scanner;
	private ArrayBlockingQueue<String> incomingMessages;
	private JTextArea outputTextArea;
	private JTextArea usersOnlineTextArea;
	
	public ClientSideInput(Socket s, ArrayBlockingQueue<String> in, JTextArea outputArea, JTextArea userArea) throws IOException
	{
		this.scanner = new Scanner(s.getInputStream());
		incomingMessages = in;
		outputTextArea = outputArea;
		usersOnlineTextArea = userArea;
	}
	
	public void run()
	{
		while(true)
		{
			try {
				readIncomingMessages();
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void readIncomingMessages() throws IOException, InterruptedException
	{
		while(!scanner.hasNext())
		{}
		String message = scanner.nextLine();
		if(incomingMessages != null && outputTextArea != null && usersOnlineTextArea != null)
		{
//			incomingMessages.put(message);
			if(message.startsWith("#$!USERS "))
				usersOnlineTextArea.setText(message.substring(9));
			else
				outputTextArea.append(message+"\n");
		}
		System.out.println(message);
	}

}
