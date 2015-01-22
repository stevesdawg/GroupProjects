package Chat;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientInput implements Runnable{
	private static Socket sock;
	private static Scanner scan;
	
	public ClientInput() throws UnknownHostException, IOException{
		sock=new Socket("10.5.101.127",80);
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
