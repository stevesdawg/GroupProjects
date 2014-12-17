package guiBased;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ChatFrame extends JFrame implements Runnable {
	
	private JTextArea incoming;
	private JTextField editor;
	private JButton sendButton;
	private JPanel panel;
	
	private final int FRAME_WIDTH = 700;
	private final int FRAME_HEIGHT = 500;
	
	private ArrayBlockingQueue<String> outQueue;
	private ArrayBlockingQueue<String> inQueue;
	
	public ChatFrame(ArrayBlockingQueue<String> out, ArrayBlockingQueue<String> in)
	{
		outQueue = out;
		inQueue = in;
	}
	
	public void run()
	{
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setupPanelLayout();
		this.setVisible(true);
		
		while(true)
		{
			updateTextArea();
		}
	}

	private void setupPanelLayout() {
		
		panel = new JPanel(new GridLayout(3, 1));
		incoming = new JTextArea();
		incoming.setEditable(false);
		incoming.setText("Messages Below:\n");
		JScrollPane scroll = new JScrollPane(incoming);
		panel.add(scroll);
		
		editor = new JTextField();
		editor.setText("Enter Text");
		editor.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
				
			}
			
			public void mousePressed(MouseEvent e) {
				editor.setText("");
			}
			
			public void mouseExited(MouseEvent e) {
				
			}
			
			public void mouseEntered(MouseEvent e) {
				
			}
			
			public void mouseClicked(MouseEvent e) {
				editor.setText("");
			}
		});
		
		editor.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					try {
						sendMessageToQueue(editor.getText());
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
			}
		});
		
		panel.add(editor);
		
		sendButton = new JButton();
		sendButton.setText("Send");
		sendButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				String sentText = editor.getText();
				if(!"Enter Text".equals(sentText) && !"".equals(sentText))
					try {
						sendMessageToQueue(sentText);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				
			}
		});
		panel.add(sendButton);
		
		this.add(panel);
		
	}
	
	private void sendMessageToQueue(String message) throws InterruptedException
	{
		editor.setText("Enter Text");
		outQueue.put(message);
		inQueue.put(message);
		System.out.println(message);
	}
	
	private void updateTextArea()
	{
		String incomingMessage = "";
		try {
			incomingMessage = inQueue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		incoming.append("USER: " + new Date().toString() + " > " + incomingMessage + "\n");
	}
	
}
