package Chat;

import java.net.Socket;

import javax.swing.JOptionPane;

public class Client {
	private Socket client;
	private String hostname;
	boolean gotName;
	
	public Client(Socket client){
		
		this.client=client;
		hostname=JOptionPane.showInputDialog("Please Enter the Name that You want to be Known As");
		
		System.out.println(hostname+" Joined");
	}
	
	public Socket getSocket(){
		return client;
	}
	
	public String getHostName(){
		return hostname;
	}
}
