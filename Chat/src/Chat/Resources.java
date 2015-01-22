package Chat;

<<<<<<< HEAD
import java.util.concurrent.ArrayBlockingQueue;

public class Resources {
	private static ArrayBlockingQueue<Client> server=new ArrayBlockingQueue<Client>(20);
	private static ArrayBlockingQueue<String> IO=new ArrayBlockingQueue<String>(100);
	
	public Resources(){
		server=new ArrayBlockingQueue<Client>(20);
		IO=new ArrayBlockingQueue<String>(100);
	}
	
	public static ArrayBlockingQueue<Client> getServerToClientHandler(){
=======
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
>>>>>>> branch 'master' of https://github.com/stevesdawg/PersonalProjects.git
		return server;
	}
	
	public static ArrayBlockingQueue<String> getInputToOutput(){
		return IO;
	}
	
}
