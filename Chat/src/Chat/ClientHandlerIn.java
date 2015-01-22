package Chat;


import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandlerIn implements Runnable{
	private static Scanner read;
	
	
	public void run(){
		while(true)
		try {
			
			checkInput();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void checkInput() throws IOException{
		
		for(Socket c: Resources.getServerToClientHandler()){
			 read=new Scanner(c.getInputStream());
			 
			 while(new InputStreamReader(c.getInputStream()).ready()){
				 Resources.getInputToOutput().add(c +" " +read.nextLine());
				
			 }
		}
	}
	
	

}
