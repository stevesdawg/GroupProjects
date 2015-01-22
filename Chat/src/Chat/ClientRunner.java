package Chat;

import java.io.IOException;
import java.net.UnknownHostException;

public class ClientRunner {
	public static void main(String[] args) throws UnknownHostException, IOException{
<<<<<<< HEAD
		Thread ClientInput=new Thread(new ClientInput());
		Thread ClientOutPut=new Thread(new ClientOutput());
		
		ClientInput.start();
		ClientOutPut.start();
=======
		ClientInput in = new ClientInput();
		Thread clientInputThread = new Thread(new ClientInput());
		
		Thread clientOutputThread = new Thread(new ClientOutput(new Client(in.getSocket())));
		
		clientInputThread.start();
		clientOutputThread.start();
>>>>>>> branch 'master' of https://github.com/stevesdawg/PersonalProjects.git
	}

}
