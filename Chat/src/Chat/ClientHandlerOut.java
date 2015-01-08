package Chat;

import java.io.IOException;
import java.io.PrintWriter;

public class ClientHandlerOut implements Runnable{
	private static PrintWriter write;
	
	public void run(){
		while(true)
		try {
			giveOutPut();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void giveOutPut() throws IOException{
		if(ServerResources.getInputToOutput().size()!=0){
			for(Client c: ServerResources.getServerToClientHandler()){
				for(int i=0;i<ServerResources.getInputToOutput().size();i++){
					if(!c.getHostName().equals(ServerResources.getInputToOutput().element().substring(0,c.getHostName().length()))){
						write=new PrintWriter(c.getSocket().getOutputStream());
						write.println((ServerResources.getInputToOutput().element()));
						write.flush();
					}
				}
			}
			
			ServerResources.getInputToOutput().poll();
		}
	}
}
