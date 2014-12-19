package Chat;

import java.io.IOException;



public class ServerRunner {
	public static void main(String[] args) throws IOException{
		Thread ClientHandlerIn=new Thread(new ClientHandlerIn());
		Thread ClientHandlerOut=new Thread(new ClientHandlerOut());
		Thread Server =new Thread(new Server());
		
		
		Server.start();
		ClientHandlerIn.start();
		ClientHandlerOut.start();
		
	}

}
