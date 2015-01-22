package Chat;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientInput implements Runnable{
	private static Socket sock;
	private static Scanner scan;
<<<<<<< HEAD
=======
	private static final String hostName = "10.0.0.8";
>>>>>>> branch 'master' of https://github.com/stevesdawg/PersonalProjects.git
	
	public ClientInput() throws UnknownHostException, IOException{
<<<<<<< HEAD
		sock=new Socket("10.5.101.127",80);
=======
		sock=new Socket(hostName, 1809);
>>>>>>> branch 'master' of https://github.com/stevesdawg/PersonalProjects.git
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
