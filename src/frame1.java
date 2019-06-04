import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;

import java.awt.TextArea;
import javax.swing.JToggleButton;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class frame1 {

	private JFrame frmAib;
	public StimulusSender sender1 = new StimulusSender();
	public StimulusSender sender2 = new StimulusSender();
	public String client1 = "127.0.0.1";
	public String client2 = "192.168.137.98";
	public Long startMarker = 1111L;
	public Long endMarker = 1234L;
	private JTextField firstAddress;
	private JTextField secondAddress;
	private boolean isConnected;
	//add port address

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame1 window = new frame1();
					window.frmAib.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 * @throws UnsupportedAudioFileException 
	 * @throws MalformedURLException 
	 * @throws LineUnavailableException 
	 */
	public frame1() throws MalformedURLException, UnsupportedAudioFileException, IOException, LineUnavailableException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 * @throws UnsupportedAudioFileException 
	 * @throws MalformedURLException 
	 * @throws LineUnavailableException 
	 */
	private void initialize() throws MalformedURLException, UnsupportedAudioFileException, IOException, LineUnavailableException {
		frmAib = new JFrame();
		frmAib.setTitle("Empathy Computing Lab");
		frmAib.setBounds(100, 100, 450, 300);
		frmAib.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel status = new JLabel("Status");
		status.setBounds(12, 224, 374, 16);
		frmAib.getContentPane().add(status);
		
		firstAddress = new JTextField();
		firstAddress.setText("172.24.3.162");
		firstAddress.setBounds(12, 34, 116, 22);
		frmAib.getContentPane().add(firstAddress);
		firstAddress.setColumns(10);
		
		secondAddress = new JTextField();
		secondAddress.setEnabled(false);
		secondAddress.setText("192.168.1.1");
		secondAddress.setBounds(283, 34, 116, 22);
		frmAib.getContentPane().add(secondAddress);
		secondAddress.setColumns(10);	
		

		
		JCheckBox chckbxAutoEnd = new JCheckBox("Auto End");
		chckbxAutoEnd.setBounds(39, 146, 113, 25);
		frmAib.getContentPane().add(chckbxAutoEnd);
		
		JCheckBox chckbxEnable = new JCheckBox("Enable");
		chckbxEnable.setBounds(283, 9, 113, 25);
		chckbxEnable.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		        if(chckbxEnable.isSelected()) {
		        	secondAddress.setEnabled(true);
		        }
		        else
		        {
		        	secondAddress.setEnabled(false);
		        }
		      }
		    });
		frmAib.getContentPane().add(chckbxEnable);
				
		JButton btnNewButton = new JButton("Connect");
		btnNewButton.setBounds(157, 33, 101, 25);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(firstAddress.getText()!=null) {
					client1 = firstAddress.getText();
				}
				if(chckbxEnable.isSelected() && secondAddress.getText()!=null) {
					client2 =secondAddress.getText();
				}
				try {
					 	System.out.print("\nConnecting");
					 	status.setText("Connecting");
					 	//status.repaint();
					 	sender1.open(client1, 15361);
					 	if(chckbxEnable.isSelected()) {
					 		sender2.open(client2, 15361);
						}
					 	System.out.print("\nConnected");
					 	status.setText("Connected");
					 	isConnected = true;
					 	ToneGenerate.generateTone(500, 500, 100, false);
					 	//status.repaint();
				} catch (Exception e1) {
					System.out.print("\nConnection failed");
				 	status.setText("Connection failed");
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		frmAib.getContentPane().setLayout(null);
		frmAib.getContentPane().add(btnNewButton);
		
		JButton btnEndTrigger = new JButton("End Trigger");
		btnEndTrigger.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				try {
					if(!isConnected) {
						status.setText("Connect First!!");
						return;
					}
					System.out.print("\nSending End marker..\n ");	
					status.setText("Sending End Marker");
					sender1.send(endMarker, 0L);
					if(chckbxEnable.isSelected()) {
						sender2.send(endMarker, 0L);
					}
					status.setText("End Marker Done");
				 	status.repaint();
				 	//ToneGenerate.generateTone(1400, 500, 100, true);

				 	AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("C:\\Users\\psk\\eclipse-workspace\\OpenBciTrigger\\src\\stop.wav"));
					Clip clip = AudioSystem.getClip();
					clip.open(audioIn);
					clip.start();
				} catch (Exception e1) {
					System.out.print("\nOperation failed");
				 	status.setText("Operation failed");
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnEndTrigger.setBounds(240, 118, 159, 25);
		frmAib.getContentPane().add(btnEndTrigger);
		
		JButton btnStartTrigger = new JButton("Start Trigger");
		btnStartTrigger.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				try {
					if(!isConnected) {
						status.setText("Connect First!!");
						return;
					}
					status.repaint();
				 	AudioInputStream audioInReady = AudioSystem.getAudioInputStream(new File("C:\\Users\\psk\\eclipse-workspace\\OpenBciTrigger\\src\\ready.wav"));
					Clip clipReady = AudioSystem.getClip();
					clipReady.open(audioInReady);
					clipReady.start();
					Thread.sleep(7000);
					
					System.out.print("\nSending Start marker..\n ");	
					status.setText("Sending Start");
				 	status.repaint();
					sender1.send(startMarker, 0L);
					if(chckbxEnable.isSelected()) {
						sender2.send(startMarker, 0L);
					}
					status.setText("Start Marker Done");
				 	
				 	
				 	//ToneGenerate.generateTone(2000, 500, 100, true);
				 	AudioInputStream audioIn2 = AudioSystem.getAudioInputStream(new File("C:\\Users\\psk\\eclipse-workspace\\OpenBciTrigger\\src\\start.wav"));
					Clip clip2 = AudioSystem.getClip();
					clip2.open(audioIn2);
					clip2.start();
				 	if(chckbxAutoEnd.isSelected()) {
				 		Thread.sleep(60000);
				 		AudioInputStream audioIn4 = AudioSystem.getAudioInputStream(new File("C:\\Users\\psk\\eclipse-workspace\\OpenBciTrigger\\src\\stop.wav"));
						Clip clip4 = AudioSystem.getClip();
				 		System.out.print("sending end trigger!");
				 		//ToneGenerate.generateTone(1400, 500, 100, true);				 		
				 		sender1.send(endMarker, 0L);//very dirty fix
				 		if(chckbxEnable.isSelected()) {
							sender2.send(endMarker, 0L);
						}
				 		
						clip4.open(audioIn4);
						clip4.start();				 		
				 	}
				} catch (Exception e1) {
					System.out.print("\nOperation failed");
				 	status.setText("Operation failed");
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnStartTrigger.setBounds(29, 118, 147, 25);
		frmAib.getContentPane().add(btnStartTrigger);
		
		
		
		JButton btnDisconnect = new JButton("Disconnect");
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
						if(!isConnected) {
							status.setText("Connect First!!");
							return;
						}
					 	System.out.print("\nDisconnecting");
					 	status.setText("Disconnecting");
					 	status.repaint();
					 	sender1.close();
					 	if(chckbxEnable.isSelected()) {
					 		sender2.close();
						}
					 	status.setText("Disconnected");
					 	status.repaint();
					 	ToneGenerate.generateTone(500, 500, 100, true);
				} catch (Exception e1) {
					System.out.print("\nOperation failed");
				 	status.setText("Operation failed");
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnDisconnect.setBounds(146, 180, 130, 25);
		frmAib.getContentPane().add(btnDisconnect);				
		
		JToggleButton tglbtnAudio = new JToggleButton("Audio");
		tglbtnAudio.setSelected(true);
		tglbtnAudio.setBounds(355, 220, 65, 25);
		frmAib.getContentPane().add(tglbtnAudio);
		
	}
}
