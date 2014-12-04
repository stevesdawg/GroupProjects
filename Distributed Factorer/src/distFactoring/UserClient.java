package distFactoring;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Random;

import javax.annotation.Generated;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class UserClient extends Thread {
	
	private final int WIDTH = 200;
	private final int HEIGHT = 200;
	private final int NUMBER_SIZE = 15;
	
	private Socket s;
	private BigInteger product;
	
	private JFrame mainFrame;
	private JButton generateNumber;
	private JButton sendNumber;
	private JLabel numbers; 
	
	public UserClient(Socket socket)
	{
		this.s = socket;
		product = null;
		setupGui();
	}
	
	public void start()
	{
		boolean notDone = true;
		InputStream in = null;
		try {
			in = s.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(notDone)
		{
			String incoming = "";
			try {
				incoming = this.getIncomingMessages(in);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(incoming.length() > 0)
				notDone = false;
		}
	}
	
	public void setupGui()
	{
		mainFrame = new JFrame();
		mainFrame.setSize(WIDTH, HEIGHT);
		generateNumber = new JButton("Generate Large Product of Primes");
		sendNumber = new JButton("Begin Factoring");
		numbers = new JLabel();
		
		setGenerateListener();
		setSendingListener();
		
		mainFrame.add(generateNumber);
		mainFrame.add(sendNumber);
		mainFrame.add(numbers);
	}
	
	public void setGenerateListener()
	{
		generateNumber.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Random rand1 = new Random();
				Random rand2 = new Random();
				BigInteger p1 = new BigInteger(NUMBER_SIZE, 1000000000, rand1);
				BigInteger p2 = new BigInteger(NUMBER_SIZE, 1000000000, rand2);
				product= p1.multiply(p2);
				numbers.setText("Number to Factor: " + product.toString());
				sendNumber.setEnabled(true);
			}
		});
	}
	
	public void setSendingListener()
	{
		sendNumber.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				numbers.setText("Factoring...");
				OutputStream out = null;
				try {
					out = s.getOutputStream();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				PrintWriter outWriter = new PrintWriter(out);
				outWriter.println("FACTOR: product.toString()");
				sendNumber.setEnabled(false);
			}
		});
	}
	
	public String getIncomingMessages(InputStream in) throws IOException
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line = "";
		String incoming = "";
		while((line = reader.readLine()) != null)
			incoming += line;
		
		return incoming;
	}

}
