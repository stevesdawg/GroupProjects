package Chat;

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
	private static final int FRAME_WIDTH = 500;
	private static final int FRAME_HEIGHT = 300;
	private JPanel panel;
	private JPanel outputPanel;
	private JPanel titlePanel;
	private JPanel bottomPanel;
	private final JTextArea output = new JTextArea(10, 42);
	private final JTextArea users = new JTextArea(1, 40);
	private final JTextField input = new JTextField(35);
	private JButton send = new JButton("Send");
	final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private ArrayBlockingQueue<String> outputMessages;
	private ArrayBlockingQueue<String> inputMessages;
	
	public ChatFrame(ArrayBlockingQueue<String> out, ArrayBlockingQueue<String> in)
	{
		this.setTitle("Chat Window");
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		outputMessages = out;
		inputMessages = in;
		makePanels();
		makeComponents();
		listen();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		this.setVisible(true);
	}
	
	public void run()
	{
		ChatFrame frame = new ChatFrame(outputMessages, inputMessages);
		frame.setVisible(true);
		while(true)
		{
			try {
				System.out.println(readInputMessages());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
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
		String message = inputMessages.take();
		output.setText(message);
		return message;
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
	}
	
	public void listen()
	{
		input.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent e) 
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					if(!"".equals(input.getText()))
					{
						try {
							outputMessages.put("User " + "(" + dateFormat.format(new Date()) + ")>" + " " + input.getText() + "\n");
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
//						output.append("User " + "(" + dateFormat.format(new Date()) + ")>" + " " + input.getText() + "\n");
						input.setText("");
					}
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
				if(!"".equals(input.getText()))
				{
					try {
						outputMessages.put("User " + "(" + dateFormat.format(new Date()) + ")>" + " " + input.getText() + "\n");
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					output.append("User " + "(" + dateFormat.format(new Date()) + ")>" + " " + input.getText() + "\n");
					input.setText("");
				}
			}
		});
	}
}