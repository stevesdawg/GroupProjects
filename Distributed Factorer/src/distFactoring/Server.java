package distFactoring;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server implements Runnable{
	private ServerSocket sock;
	private boolean User;
	private Socket UserClient;
	private static BigInteger factor;
	private static boolean gotFactor=false;
	
	public Server(ServerSocket sock){
		this.sock=sock;
		factor=null;
	}
	
	public void run(){
		while(true){
			try {
				Scanner scan=new Scanner(UserClient.getInputStream());
				while(scan.hasNext() && !gotFactor){
					BigInteger in=scan.nextBigInteger();
					FactorerHandler.pushIntegerToFactor(in);
					gotFactor=true;
				}
				
				pushFactorers();
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
			
			if(gotFactor && factor!=null)
			{
				try {
					PrintWriter print=new PrintWriter(UserClient.getOutputStream());
					print.print("FOUND"+ factor);
					
					
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public void pushFactorers() throws IOException{
		if(!User){
			UserClient=sock.accept();
			User=true;
		}
		
		else
		{
			FactorerHandler.add(new Factorer(sock.accept(), null, null, null));
		}
	}
	
	public static void found(BigInteger factor1){
		factor=factor1;
	}
	
	
}
