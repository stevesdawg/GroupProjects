package Chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

public class ClientOutput implements Runnable{
	private Socket sock;
	private PrintWriter out;
	private Scanner scan;
	private ArrayBlockingQueue<String> outputMessages;
	
	public ClientOutput(ArrayBlockingQueue<String> outQueue) throws UnknownHostException, IOException{
		sock=ClientInput.getSocket();
		out=new PrintWriter(sock.getOutputStream());
		scan=new Scanner(System.in);
		outputMessages = outQueue;
	}
	
	public void run(){
		while(true)
			try {
				getInput();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	
	private void getInput() throws InterruptedException{
//		while(scan.hasNextLine()){
//			String message = scan.nextLine();
//			outputMessages.put(message);
//			out.println(outputMessages.take());
//			
//			out.flush();
//		}
		
		String message = outputMessages.take();
		out.println(message);
		out.flush();
	}
	
}
