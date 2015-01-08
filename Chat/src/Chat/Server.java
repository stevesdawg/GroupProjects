package Chat;

import java.io.IOException;
import java.net.ServerSocket;


public class Server implements Runnable{
	private ServerSocket sock;
	
	public Server() throws IOException {
		sock=new ServerSocket(1809);
		
	}
	
	public void run(){
		while(true)
		try {
			
			handle();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void handle() throws IOException{
		
		ServerResources.getServerToClientHandler().add(new Client(sock.accept()));
	}
	


}
