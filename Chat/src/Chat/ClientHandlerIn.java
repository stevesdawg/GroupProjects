package Chat;


import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Map;
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
		Map<Socket, String> serverMap = Resources.getServerToClientHandler();
		for(Map.Entry<Socket,String> entry : serverMap.entrySet()){
			Socket s = entry.getKey();
			String name = entry.getValue();
			 read=new Scanner(s.getInputStream());
			 
			 while(new InputStreamReader(s.getInputStream()).ready()){
				 Resources.getInputToOutput().add(name +": " +read.nextLine());
				
			 }
		}
	}
	
	

}
