package Chat;

import java.io.IOException;
import java.net.UnknownHostException;

public class ClientRunner {
	public static void main(String[] args) throws UnknownHostException, IOException{
		Thread ClientInput=new Thread(new ClientInput());
		Thread ClientOutPut=new Thread(new ClientOutput());
		
		ClientInput.start();
		ClientOutPut.start();
	}

}
