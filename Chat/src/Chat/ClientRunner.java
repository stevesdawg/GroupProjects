package Chat;

import java.io.IOException;
import java.net.UnknownHostException;

public class ClientRunner {
	public static void main(String[] args) throws UnknownHostException, IOException{
		ClientInput in = new ClientInput();
		Thread clientInputThread = new Thread(new ClientInput());
		
		Thread clientOutputThread = new Thread(new ClientOutput(new Client(in.getSocket())));
		
		clientInputThread.start();
		clientOutputThread.start();
	}

}
