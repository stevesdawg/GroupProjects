package clientServer;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.JOptionPane;

public class GuiClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		
		ArrayBlockingQueue<String> outgoingMessages = new ArrayBlockingQueue<String>(40);
		ArrayBlockingQueue<String> incomingMessagse = new ArrayBlockingQueue<String>(40);
		
		String hostName = "10.0.0.2";
//		String hostName = JOptionPane.showInputDialog("Enter a hostname (ip address)");
		String userName = JOptionPane.showInputDialog("Enter a screen name");
		Socket socket = new Socket(hostName, 1809);
		
		ChatFrame chatGui = new ChatFrame(outgoingMessages, incomingMessagse, userName);
		Thread guiThread = new Thread(chatGui);
		Thread output = new Thread(new ClientSideOutput(socket, outgoingMessages, userName));
		Thread input = new Thread(new ClientSideInput(socket, incomingMessagse, ChatFrame.output, ChatFrame.users));
		
		guiThread.start();
		output.start();
		input.start();

	}

}
