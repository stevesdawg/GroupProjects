package Chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientOutput implements Runnable{
	private Socket sock;
	private PrintWriter out;
	private Scanner scan;
	
	public ClientOutput() throws UnknownHostException, IOException{
		sock=ClientInput.getSocket();
		out=new PrintWriter(sock.getOutputStream());
		scan=new Scanner(System.in);
	}
	
	public void run(){
		while(true)
		getInput();
	}
	
	private void getInput(){
		while(scan.hasNextLine()){
			out.println(scan.nextLine());
			
			out.flush();
		}
	}
	
}
