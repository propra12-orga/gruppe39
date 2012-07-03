package Gamestates;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import Engine.Bomberman;
import Engine.Gamescreen;
import Engine.Gamestate;
import Engine.Generator;
import Engine.InterfaceState;
import Engine.RenderWindow;


/***************************************************************************
* StateGameMode
* desc: 	main menu of the whole game.
*			for more information on details, see the corresponding interface
***************************************************************************/
public class StateMainMenu implements InterfaceState, ActionListener
{
	private Gamestate Cur_Gamestate;
	private Gamescreen Cur_Gamescreen;
	
	private JFrame Frame;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextPane textPane;
	
	private JButton btnRandom = new JButton("Random");
	private JButton btoenffnen = new JButton("Öffnen");
	private JButton btnStart = new JButton("Start");
	private JButton btnStartClient = new JButton("Starte Client");
	private JButton btnStartServer = new JButton("Starte Server");
	private JLabel lblLevelLaden = new JLabel("Level Laden");
	
	public StateMainMenu(Gamestate Cur_State, Gamescreen Cur_Screen)
	{
		// important: these are references
		this.Cur_Gamestate = Cur_State;
		this.Cur_Gamescreen = Cur_Screen;
	}
	
	@Override
	public void init() 
	{		
		RenderWindow.Frame = new JFrame("Bomberman");
		this.Frame = RenderWindow.Frame;
		this.Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.Frame.setBounds(100, 100, 480, 480);
		
		this.Frame.getContentPane();
		JPanel contentPane = (JPanel) this.Frame.getContentPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.Frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		

		
		btnRandom.addActionListener(this);
		
		btnRandom.setBounds(30, 390, 107, 25);
		contentPane.add(btnRandom);
		
		JLabel lblZuflligesLevel = new JLabel("Zufälliges Level");
		lblZuflligesLevel.setBounds(30, 366, 107, 15);
		contentPane.add(lblZuflligesLevel);
		
		
		btoenffnen.addActionListener(this);
		btoenffnen.setBounds(30, 269, 107, 25);
		contentPane.add(btoenffnen);
		
		
		btnStart.addActionListener(this);
		btnStart.setBounds(174, 269, 107, 25);
		contentPane.add(btnStart);
		
		
		btnStartClient.addActionListener(this);
		btnStartClient.setBounds(320, 306, 114, 25);
		contentPane.add(btnStartClient);
		
		
		btnStartServer.addActionListener(this);
		btnStartServer.setBounds(320, 269, 114, 25);
		
		contentPane.add(btnStartServer);
		
		
		lblLevelLaden.setBounds(30, 234, 101, 15);
		contentPane.add(lblLevelLaden);
		
		JLabel lblGeladenesLevel = new JLabel("Geladenes Level");
		lblGeladenesLevel.setBounds(30, 306, 140, 15);
		contentPane.add(lblGeladenesLevel);
		
		textPane = new JTextPane();
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
		
		
		RenderWindow.Frame.setVisible(true);
		


		
	}

	@Override
	public void main(long elapsed_time) 
	{
		
	}

	@Override
	public void shutdown() 
	{
		// clear screen
		this.Frame.removeAll();
	}
	
	private void openLevel()
	{
		JFileChooser Fc = new JFileChooser();
		Fc.showOpenDialog(this.Frame);
		File File = Fc.getSelectedFile();
		if (File == null)
			return;
		
		this.textPane.setText(File.getName());
		Bomberman.level = File.getPath();
		
	}
	
	private void startGameLocal()
	{		
		this.Frame.removeAll();
		this.Frame.dispose();
		this.Cur_Gamescreen.getRenderWindow().initGameRenderWindow();
		
		this.Cur_Gamestate.set(Gamestate.STATE.GAME_MODE);
		
	}
	
	private void startGameServer()
	{
		if (Bomberman.level == "map.ran")
		{
			JOptionPane.showMessageDialog(this.Frame, "Bitte ein Level laden, da Zufallslevel nicht unterstützt wird");
			return;
		}
		
		if (this.textField.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(this.Frame, "Keine Portnummer angegeben");
			return;
		}
		
		this.Frame.removeAll();
		this.Frame.dispose();
		this.Cur_Gamescreen.getRenderWindow().initGameRenderWindow();
		
		Bomberman.port = (new Integer(this.textField.getText())).intValue();
		this.Cur_Gamestate.set(Gamestate.STATE.GAME_MODE_SERVER);
		
	}
	
	private void startGameClient()
	{
		if (Bomberman.level == "map.ran")
		{
			JOptionPane.showMessageDialog(this.Frame, "Bitte ein Level laden, da Zufallslevel nicht unterstützt wird");
			return;
		}
		
		if (this.textField.getText().isEmpty() || this.textField_1.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(this.Frame, "Keine Portnummer oder Adresse angegeben");
			return;
		}
		
		this.Frame.removeAll();
		this.Frame.dispose();
		this.Cur_Gamescreen.getRenderWindow().initGameRenderWindow();
		
		Bomberman.port = (new Integer(this.textField.getText())).intValue();
		Bomberman.address = this.textField_1.getText();
		this.Cur_Gamestate.set(Gamestate.STATE.GAME_MODE_CLIENT);		
	}
	
	private void randomMap()
	{
		Bomberman.level = "map.ran";
		JOptionPane.showMessageDialog(this.Frame, "Zufälliges Level wird beim Spielstart erzeugt");
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object src = e.getSource();
		
		if (src == this.btoenffnen)
			this.openLevel();
		else if (src == this.btnStart)
			this.startGameLocal();
		else if (src == this.btnStartClient)
			this.startGameClient();
		else if (src == this.btnStartServer)
			this.startGameServer();
		else if (src == this.btnRandom)
			this.randomMap();
	
	}
}
