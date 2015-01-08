package Chat;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.ArrayBlockingQueue;

public class ClientRunner {
	public static void main(String[] args) throws UnknownHostException, IOException{
		
		ArrayBlockingQueue<String> inputMessages = new ArrayBlockingQueue<String>(20);
		ArrayBlockingQueue<String> outputMessages = new ArrayBlockingQueue<String>(20);
		
		Thread ClientInput=new Thread(new ClientInput(inputMessages));
		Thread ClientOutPut=new Thread(new ClientOutput(outputMessages));
		Thread guiThread = new Thread(new ChatFrame(outputMessages, inputMessages));
		
		ClientInput.start();
		ClientOutPut.start();
		guiThread.start();
	}

}
