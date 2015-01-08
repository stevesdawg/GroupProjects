package Chat;

import java.util.concurrent.ArrayBlockingQueue;

public class ServerResources {
	private static ArrayBlockingQueue<Client> server=new ArrayBlockingQueue<Client>(20);
	private static ArrayBlockingQueue<String> IO=new ArrayBlockingQueue<String>(100);
	
	public ServerResources(){
		server=new ArrayBlockingQueue<Client>(20);
		IO=new ArrayBlockingQueue<String>(100);
	}
	
	public static ArrayBlockingQueue<Client> getServerToClientHandler(){
		return server;
	}
	
	public static ArrayBlockingQueue<String> getInputToOutput(){
		return IO;
	}
	
}
