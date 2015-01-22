package Chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

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
			Map<Socket, String> serverMap = Resources.getServerToClientHandler();
			for(Map.Entry<Socket, String> entry : serverMap.entrySet()){
				for(int i=0;i<Resources.getInputToOutput().size();i++){
					if(true){
						write=new PrintWriter(entry.getKey().getOutputStream());
						write.println((Resources.getInputToOutput().element()));
						write.flush();
					}
				}
			}
			
			String message = Resources.getInputToOutput().poll();
			System.out.println(message);
		}
	}
}
