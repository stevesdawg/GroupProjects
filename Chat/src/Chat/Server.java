package Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


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
		
		Socket incoming = sock.accept();
		String name = new Scanner(incoming.getInputStream()).nextLine().substring(8);
		Resources.getServerToClientHandler().put(incoming, name);
	}
	


}
