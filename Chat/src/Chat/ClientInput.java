package Chat;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

public class ClientInput implements Runnable{
	private static Socket sock;
	private static Scanner scan;
	private ArrayBlockingQueue<String> incomingMessages;
	
	public ClientInput(ArrayBlockingQueue<String> in) throws UnknownHostException, IOException{
		sock=new Socket("10.5.101.127",80);
		scan=new Scanner(sock.getInputStream());
		incomingMessages = in;
	}
	
	public void run(){
		while(true)
			try {
				printOut();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	
	public static Socket getSocket(){
		return sock;
	}
	
	public void printOut() throws InterruptedException{
		while(scan.hasNextLine()){
			String message = scan.nextLine();
			System.out.println(message);
			incomingMessages.put(message);
		}
	}

}
