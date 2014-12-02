import java.io.IOException;
import java.net.Socket;


public class ClientRunner {

	public static void main(String[] args) {
		
		final int PORT = 9090;
		final String HOSTNAME = "localhost";
		
		Socket socket = null;
		try {
			socket = new Socket(HOSTNAME, PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ClientThread client = new ClientThread(socket);
		
		client.start();
		
	}

}
