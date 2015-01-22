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
	private Client c;
	
	public ClientOutput(Client c) throws UnknownHostException, IOException{
		sock=ClientInput.getSocket();
		out=new PrintWriter(sock.getOutputStream());
		scan=new Scanner(System.in);
		this.c = c;
		out.println("$NAMEIS " + c.getHostName());
		out.flush();
	}
	
	public void run(){
		while(true)
		getInput();
	}
	
	private void getInput(){
		while(scan.hasNextLine()){
			out.println(c.getHostName() + ": " + scan.nextLine());
			
			out.flush();
		}
	}
	
}
