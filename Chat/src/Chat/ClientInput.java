package Chat;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientInput implements Runnable{
	private static Socket sock;
	private static Scanner scan;
	private static final String hostName = "10.0.0.8";
	
	public ClientInput() throws UnknownHostException, IOException{
		sock=new Socket(hostName, 1809);
		scan=new Scanner(sock.getInputStream());
	}
	
	public void run(){
		while(true)
		printOut();
	}
	
	public static Socket getSocket(){
		return sock;
	}
	
	public void printOut(){
		while(scan.hasNextLine()){
			System.out.println(scan.nextLine());
		}
	}

}
