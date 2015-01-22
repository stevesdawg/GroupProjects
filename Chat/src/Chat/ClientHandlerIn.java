package Chat;


import java.io.IOException;
import java.io.InputStreamReader;
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
		
		for(Client c: Resources.getServerToClientHandler()){
			 read=new Scanner(c.getSocket().getInputStream());
			 
			 while(new InputStreamReader(c.getSocket().getInputStream()).ready()){
				 Resources.getInputToOutput().add(c.getHostName()+" " +read.nextLine());
				
			 }
		}
	}
	
	

}
