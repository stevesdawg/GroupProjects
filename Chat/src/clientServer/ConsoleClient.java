package clientServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class ConsoleClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		
//		String hostName = JOptionPane.showInputDialog("Enter a hostname (ip address)");
		String hostName = "10.0.0.2";
		String userName = JOptionPane.showInputDialog("Enter a screen name");
		Socket socket = new Socket(hostName, 1809);
		PrintWriter writer = new PrintWriter(socket.getOutputStream());
		writer.println(userName);
		writer.flush();
		
		Scanner keyboard = new Scanner(System.in);
		Thread clientInput = new Thread(new ClientSideInput(socket, null, null, null));
		clientInput.start();
		while(true)
		{
			System.out.println("Enter a message to the server: ");
			String messageString = keyboard.nextLine();
			writer.println(userName + ": " + messageString);
			writer.flush();
		}

	}

}
