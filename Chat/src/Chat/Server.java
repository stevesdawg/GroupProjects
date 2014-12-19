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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void handle() throws IOException{
		
		Resources.getServerToClientHandler().add(new Client(sock.accept()));
	}
	


}
