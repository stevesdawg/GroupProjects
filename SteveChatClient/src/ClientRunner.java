import java.io.IOException;
import java.net.Socket;


public class ClientRunner {

	public static void main(String[] args) throws IOException {
		
		final int PORT = 9090;
		final String HOSTNAME = "10.5.100.167";
		
		Socket socket = null;
		try {
			socket = new Socket(HOSTNAME, PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ClientThread client = new ClientThread(socket);
		System.out.println(socket.getLocalPort());
		
		client.start();
		
	}

}
