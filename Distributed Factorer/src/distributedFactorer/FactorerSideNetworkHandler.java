package distributedFactorer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by steve on 1/24/15.
 */
public class FactorerSideNetworkHandler implements Runnable {

    private Socket socket;
    private ArrayBlockingQueue<Thread> factorers;
    private ArrayBlockingQueue<BigInteger> factors;
    public static boolean stopped;
    private BigInteger numberToFactor = null;

    public FactorerSideNetworkHandler(Socket socket) throws IOException
    {
        this.socket = socket;
        factorers = new ArrayBlockingQueue<Thread>(100);
        factors = new ArrayBlockingQueue<BigInteger>(100);
        stopped = false;
        PrintWriter writer = new PrintWriter(this.socket.getOutputStream());
        writer.println("FACTORER");
        writer.flush();
    }

    public void run()
    {
        try {
            readIncomingStartCommands();
        } catch (IOException e) {
            e.printStackTrace();
        }

        startThreads();

        while(!stopped)
        {
            //Checking if server sends stop command.
            try {
                checkForStop();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Checking if local factorers have finished.
        if(factors.size() != 0)
        {
            BigInteger factor = null;
            BigInteger otherFactor = null;
            try {
                factor = factors.take();
                otherFactor = numberToFactor.divide(factor);
                sendToServer("DONE:" + factor.toString() + " " + otherFactor.toString());
                System.out.println("Factors: " + factor.toString() + " " + otherFactor.toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            checkForStop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readIncomingStartCommands() throws IOException {
        Scanner serverScanner = new Scanner(socket.getInputStream());
        while(factorers.size() < 4) {
            while(!serverScanner.hasNext())
            {}
            String incoming = serverScanner.nextLine();
            if (incoming.startsWith("START ")) {
            	System.out.println("SERVER SAYS: " + incoming);
                String factorerParameters = incoming.substring(6);
                String[] params = factorerParameters.split(" ");
                
                int start = Integer.parseInt(params[0]);
                int jump = Integer.parseInt(params[1]);
                BigInteger numberToFactor = new BigInteger(params[2]);
                if(this.numberToFactor == null)
                	this.numberToFactor = numberToFactor;

                factorers.add(new Thread(new ThreadedFactorer(numberToFactor, start, jump, factors)));
            }
        }
    }

    public void startThreads()
    {
        for(Thread t : factorers)
            t.start();
    }

    public void sendToServer(String message) throws IOException {
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        writer.println(message);
        writer.flush();
    }

    public void checkForStop() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String message = "";
        if(reader.ready())
            message = reader.readLine();
       	if(message.startsWith("STOP"))
        {
            stopped = true;
            System.out.println("SERVER SAYS: " + message);
        }
    }

}
