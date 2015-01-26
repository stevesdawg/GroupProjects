package distributedFactorer;

import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by steve on 1/24/15.
 */
public class Server {

    public static void main(String[] args) throws IOException, InterruptedException {
        ArrayBlockingQueue<Socket> factorerSockets = new ArrayBlockingQueue<Socket>(20);

        ServerSocket socket = new ServerSocket(1809);

        Scanner keyboard = new Scanner(System.in);
        boolean wait = true;
        System.out.println("Number to factor?");
        String numberToFactor = keyboard.nextLine();
        BigInteger numToFactor = new BigInteger(numberToFactor);

        while(wait)
        {
            System.out.println("Waiting for new factorers...");
            Socket incomingSocket = socket.accept();
            System.out.println("Socket connected from: " + incomingSocket.getInetAddress());
            Scanner incoming = new Scanner(incomingSocket.getInputStream());

            String message = incoming.nextLine();
            System.out.println("FIRST MESSAGE FROM FACTORER: " + message);
            if(message.startsWith("FACTORER"))
                factorerSockets.put(incomingSocket);
            System.out.println("Currently, there are " + factorerSockets.size() + " factorers connected.");

            System.out.println("Wait for more factorers to connect?");
            String input = keyboard.nextLine();
            wait = input.toLowerCase().startsWith("y");
        }

        new Thread(new ServerSideNetworkHandler(numToFactor, factorerSockets)).start();
    }
}