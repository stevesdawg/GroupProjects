package Chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandlerOut implements Runnable{
	private static PrintWriter write;
	
	public void run(){
		while(true)
		try {
			giveOutPut();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void giveOutPut() throws IOException{
		if(Resources.getInputToOutput().size()!=0){
			for(Socket c: Resources.getServerToClientHandler()){
				for(int i=0;i<Resources.getInputToOutput().size();i++){
					if(true/*!c.getHostName().equals(Resources.getInputToOutput().element().substring(0,c.getHostName().length()))*/){
						write=new PrintWriter(c.getOutputStream());
						write.println((Resources.getInputToOutput().element()));
						write.flush();
					}
				}
			}
			
			Resources.getInputToOutput().poll();
		}
	}
}
