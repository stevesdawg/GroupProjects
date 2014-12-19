import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.util.concurrent.ArrayBlockingQueue;


public class Server {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		final int PORT = 9090;
		final int CAPACITY = 20;
		ArrayBlockingQueue<OutputStream> outStreams = new ArrayBlockingQueue<OutputStream>(CAPACITY);
		
		ServerSocket server = new ServerSocket(PORT);
		
		try
		{
			while(true)
			{
				ClientHandler handler = new ClientHandler(server.accept(), outStreams);
				handler.start();
			}
		}
		finally
		{
			server.close();
		}
		
	}

}
