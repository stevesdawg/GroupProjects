package guiBased;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ArrayBlockingQueue;

public class ChatClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		
		ArrayBlockingQueue<String> outQueue = new ArrayBlockingQueue<>(20);
		ArrayBlockingQueue<String> inQueue = new ArrayBlockingQueue<>(20);
		
		final String HOSTNAME = "localhost";
		final int PORT = 9090;
		
		Socket socket = new Socket(HOSTNAME, PORT);
		
		ChatFrame cf = new ChatFrame(outQueue, inQueue);
		Thread guiThread = new Thread(cf);
		OutputHandler out = new OutputHandler(socket, outQueue);
		InputHandler in = new InputHandler(socket, inQueue);
		
		guiThread.start();
		out.start();
		in.start();
		
	}

}
