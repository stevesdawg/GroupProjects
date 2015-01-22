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
<<<<<<< HEAD
=======
	private Client c;
>>>>>>> branch 'master' of https://github.com/stevesdawg/PersonalProjects.git
	
<<<<<<< HEAD
	public ClientOutput() throws UnknownHostException, IOException{
=======
	public ClientOutput(Client c) throws UnknownHostException, IOException{
>>>>>>> branch 'master' of https://github.com/stevesdawg/PersonalProjects.git
		sock=ClientInput.getSocket();
		out=new PrintWriter(sock.getOutputStream());
		scan=new Scanner(System.in);
<<<<<<< HEAD
=======
		this.c = c;
		out.println(c.getHostName() + " joined.");
		out.flush();
>>>>>>> branch 'master' of https://github.com/stevesdawg/PersonalProjects.git
	}
	
	public void run(){
		while(true)
		getInput();
	}
	
	private void getInput(){
		while(scan.hasNextLine()){
<<<<<<< HEAD
			out.println(scan.nextLine());
=======
			out.println(c.getHostName() + ": " + scan.nextLine());
>>>>>>> branch 'master' of https://github.com/stevesdawg/PersonalProjects.git
			
			out.flush();
		}
	}
	
}
