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
public class ServerSideNetworkHandler implements Runnable {

    private ArrayBlockingQueue<Socket> factorerSockets;
    private BigInteger numberToFactor;
    private ArrayList<String> factors;

    public ServerSideNetworkHandler(BigInteger num, ArrayBlockingQueue<Socket> factorSockets)
    {
        this.factorerSockets = factorSockets;
        this.numberToFactor = num;
        factors = new ArrayList<String>();
    }

    public void run()
    {
        try {
            startFactorers();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            readMessagesFromFactorers();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            stopFactorers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readMessagesFromFactorers() throws IOException, InterruptedException {
        while(factors.size() < 2)
        {
            for (Socket s : factorerSockets)
            {
                String incoming = new String();
                BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
                if (reader.ready())
                {
                    incoming = reader.readLine();
                    if (incoming.startsWith("DONE:"))
                    {
                        String[] receivedFactors = incoming.substring(5).split(" ");
                        factors.add(receivedFactors[0]);
                        factors.add(receivedFactors[1]);
                        System.out.println(incoming);
                    }
                    else continue;
                }
            }
        }
    }

    public void startFactorers() throws IOException {
        int numFactorers = factorerSockets.size() * 4;
        int jump = numFactorers * 2;
        int start = 3;
        for(Socket s : factorerSockets)
        {
            PrintWriter factorerWriter = new PrintWriter(s.getOutputStream());
            for(int i = 0; i < 4; i++)
            {
                factorerWriter.println("START " + start + " " + jump + " " + numberToFactor.toString());
                start += 2;
            }
            factorerWriter.flush();
        }
    }

    public void stopFactorers() throws IOException {
        for(Socket s : factorerSockets)
        {
            PrintWriter writer = new PrintWriter(s.getOutputStream());
            writer.println("STOP. Factors are: " + factors.toString());
            writer.flush();
        }
    }
}
