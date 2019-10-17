package test;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import common.Message;
import server.ServerService;

public class MainWindow  extends JFrame implements IConstants, ActionListener {
	private ServerService server;
	
	public MainWindow() {
		this("No Title");
	}
	
	public MainWindow(String pTitle) {
		super(pTitle);
		
		this.setLayout(null);
		this.setSize(PANTALLA_WIDTH, PANTALLA_HEIGHT);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setExtendedState(Frame.NORMAL);
        
        initComponents();
        
        this.setVisible(true);
	}
		
	private void initComponents() {
		JButton iniciar = new JButton("Iniciar");
		iniciar.setBounds(20, 20, DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT);
		iniciar.addActionListener(this);
		iniciar.setName("iniciar");
		this.add(iniciar);
		
		JButton next = new JButton("Next Round");
		next.setBounds(20, 100, DEFAULT_BUTTON_WIDTH+40, DEFAULT_BUTTON_HEIGHT);
		next.addActionListener(this);
		next.setName("next");
		this.add(next);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		JButton boton = (JButton)evt.getSource();
		
		if (boton.getName().compareTo("iniciar")==0) {
			server = new ServerService();
		}
		
		if (boton.getName().compareTo("next")==0) {
			for(String teamname: server.getTeamNames()) 
			{
				System.out.println("Enviando request de reto a team "+teamname);
				server.sendToOneRandom(teamname, new Message(0));
			}
		}		
	}
}
