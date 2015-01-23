package clientServer;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class ChatFrame extends JFrame implements Runnable
{
	private static final int FRAME_WIDTH = 600;
	private static final int FRAME_HEIGHT = 400;
	private JPanel panel;
	private JPanel outputPanel;
	private JPanel titlePanel;
	private JPanel bottomPanel;
	private JButton send = new JButton("Send");
	private JButton disconnect = new JButton("Disconnect");
	
	public static JTextArea output = new JTextArea(8, 42);
	public static JTextArea users = new JTextArea(1, 40);
	private final JTextField input = new JTextField(35);
	
	final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private String userName;

	private ArrayBlockingQueue<String> outgoingMessages;
	private ArrayBlockingQueue<String> incomingMessages;
	
	public ChatFrame(ArrayBlockingQueue<String> out, ArrayBlockingQueue<String> in, String userName)
	{
		this.setTitle("Chat Window");
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		outgoingMessages = out;
		incomingMessages = in;
		makePanels();
		makeComponents();
		listen();
		this.userName = userName;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		this.setVisible(true);
	}
	
	public void run()
	{
		ChatFrame frame = new ChatFrame(outgoingMessages, incomingMessages, userName);
		frame.setVisible(true);
		while(true)
		{
			try {
				readInputMessages();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void makePanels()
	{
		panel = new JPanel();
		this.add(panel);
		panel.setLayout(new BorderLayout());

		outputPanel = new JPanel();
		outputPanel.setBorder(new TitledBorder(new EtchedBorder(), "Messages"));
		panel.add(outputPanel, BorderLayout.CENTER);
		titlePanel = new JPanel();
		titlePanel.setBorder(new TitledBorder(new EtchedBorder(), "Users"));
		panel.add(titlePanel, BorderLayout.NORTH);
		bottomPanel = new JPanel();
		bottomPanel.setBorder(new EtchedBorder());
		panel.add(bottomPanel, BorderLayout.SOUTH);
	}
	
	private String readInputMessages() throws InterruptedException
	{
		String message = incomingMessages.take();
		output.append(message);
		return message;
	}
	
	private String messageHeader()
	{
		return userName + "(" + dateFormat.format(new Date()) + ")>" + " ";
	}
	
	public void makeComponents()
	{
		users.setEditable(false);
		titlePanel.add(users);

		output.setEditable(false);
		JScrollPane scroll = new JScrollPane(output);
		outputPanel.add(scroll);

		bottomPanel.add(input);
		bottomPanel.add(send);
		bottomPanel.add(disconnect);
	}
	
	private void sendMessageToQueue()
	{
		if(!"".equals(input.getText()))
		{
			String inputMessage = input.getText();
			try {
				outgoingMessages.put(messageHeader() + inputMessage);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
//			output.append(messageHeader() + inputMessage + "\n");
			input.setText("");
		}
	}
	
	public void listen()
	{
		input.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent e) 
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					sendMessageToQueue();
				}
			}
			public void keyReleased(KeyEvent arg0) 
			{
				//Not needed
			}
			public void keyTyped(KeyEvent arg0) 
			{
				//Not needed
			}
		});

		send.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				sendMessageToQueue();
			}
		});
		
		disconnect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try {
					outgoingMessages.put("#$! " + userName + " DISCONNECTED");
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});
	}
}