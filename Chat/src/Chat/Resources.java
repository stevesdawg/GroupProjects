package Chat;

import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

public class Resources {
	private static ArrayBlockingQueue<Socket> server=new ArrayBlockingQueue<Socket>(20);
	private static ArrayBlockingQueue<String> IO=new ArrayBlockingQueue<String>(100);
	
	public Resources(){
		server=new ArrayBlockingQueue<Socket>(20);
		IO=new ArrayBlockingQueue<String>(100);
	}
	
	public static ArrayBlockingQueue<Socket> getServerToClientHandler(){
		return server;
	}
	
	public static ArrayBlockingQueue<String> getInputToOutput(){
		return IO;
	}
	
}
