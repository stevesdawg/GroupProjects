package Chat;

import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class Resources {
	private static Map<Socket, String> server=new ConcurrentHashMap<Socket, String>(20);
	private static ArrayBlockingQueue<String> IO=new ArrayBlockingQueue<String>(100);	
	public Resources(){
		server=new ConcurrentHashMap<Socket, String>(20);
		IO=new ArrayBlockingQueue<String>(100);
	}
	
	public static Map<Socket, String> getServerToClientHandler(){
		return server;
	}
	
	public static ArrayBlockingQueue<String> getInputToOutput(){
		return IO;
	}
	
}
