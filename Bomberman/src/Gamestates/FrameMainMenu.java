package Gamestates;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class FrameMainMenu extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameMainMenu frame = new FrameMainMenu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrameMainMenu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 480, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnffnen = new JButton("Öffnen");
		btnffnen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnffnen.setBounds(30, 269, 107, 25);
		contentPane.add(btnffnen);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnStart.setBounds(174, 269, 107, 25);
		contentPane.add(btnStart);
		
		JButton btnStartClient = new JButton("Start Client");
		btnStartClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnStartClient.setBounds(320, 306, 114, 25);
		contentPane.add(btnStartClient);
		
		JButton btnStartServer = new JButton("Start Server");
		btnStartServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnStartServer.setBounds(320, 269, 114, 25);
		contentPane.add(btnStartServer);
		
		JLabel lblLevelLaden = new JLabel("Level Laden");
		lblLevelLaden.setBounds(30, 234, 101, 15);
		contentPane.add(lblLevelLaden);
		
		JLabel lblGeladenesLevel = new JLabel("Geladenes Level");
		lblGeladenesLevel.setBounds(30, 306, 140, 15);
		contentPane.add(lblGeladenesLevel);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(30, 333, 107, 21);
		contentPane.add(textPane);
		
		JLabel lblStarteSpieler = new JLabel("2 Spieler (lokal)");
		lblStarteSpieler.setBounds(174, 226, 107, 31);
		contentPane.add(lblStarteSpieler);
		
		JLabel lblSpielernetzwerk = new JLabel("2 Spieler (Netzwerk)");
		lblSpielernetzwerk.setBounds(320, 229, 128, 25);
		contentPane.add(lblSpielernetzwerk);
		
		JLabel lblNewLabel = new JLabel("Programmierpraktikum 2012");
		lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
		lblNewLabel.setBounds(97, 12, 269, 45);
		contentPane.add(lblNewLabel);
		
		JLabel lblBomberman = new JLabel("Bomberman");
		lblBomberman.setFont(new Font("Dialog", Font.PLAIN, 18));
		lblBomberman.setBounds(176, 69, 122, 45);
		contentPane.add(lblBomberman);
		
		textField = new JTextField();
		textField.setBounds(320, 361, 114, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(320, 402, 114, 19);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblPort = new JLabel("Port");
		lblPort.setBounds(320, 343, 61, 15);
		contentPane.add(lblPort);
		
		JLabel lblAdresse = new JLabel("Adresse");
		lblAdresse.setBounds(320, 387, 75, 15);
		contentPane.add(lblAdresse);
		
		JButton btnRandom = new JButton("Random");
		btnRandom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnRandom.setBounds(30, 390, 107, 25);
		contentPane.add(btnRandom);
		
		JLabel lblZuflligesLevel = new JLabel("Zufälliges Level");
		lblZuflligesLevel.setBounds(30, 366, 107, 15);
		contentPane.add(lblZuflligesLevel);
	}
}
