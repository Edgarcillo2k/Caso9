package case9;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Window extends JFrame implements ActionListener
{
	private JButton[] buttons;
	private String strings[] = {"7Y", "45", "9F", "R3", "BC", "Y1", "63", "H5","D3"};
	private final int x = 110;
	private final int y = 110;
	private JLabel patternText = new JLabel("Pattern: ");
	private JLabel pattern = new JLabel("");
	private JButton sendText = new JButton("Send text");
	private JButton resetPattern = new JButton("Reset pattern");
	private JLabel text = new JLabel("Text: ");
	private JTextField textInput = new JTextField("");
	private AES aes = new AES();
	private RSAUtil rsa;
	public Window() 
    {
		try {
			rsa = new RSAUtil();
		} catch (NoSuchAlgorithmException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setBounds(0,0,1080,720);
        setVisible(true);
        setResizable(false);
        setLayout(null);
        patternText.setBounds(10,330,150,30);
        add(patternText);
        pattern.setBounds(60,330,150,30);
        add(pattern);
		resetPattern.setBounds(0,360,330,30);
        resetPattern.addActionListener(this);
		add(resetPattern);
		text.setBounds(10,400,50,20);
		add(text);
		textInput.setBounds(40,400,290,20);
        textInput.addActionListener(this);
		add(textInput);
		sendText.setBounds(0,430,330,30);
        sendText.addActionListener(this);
		add(sendText);
        
		buttons = new JButton[9];
		for(int row = 0;row<3;row++) {
			for(int column = 0;column<3;column++) {
				JButton button=new JButton(strings[row*3+column]);
		        button.setBounds(x*row,y*column,100,100);
		        button.addActionListener(this);
		        button.setForeground(Color.black);
		        buttons[row*3+column] = button;
				add(button);
			}
		}
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == resetPattern) {
			pattern.setText("");
			for(int i = 0;i<9;i++) {
				buttons[i].setForeground(Color.black);
			}
			return;
		}
		if(e.getSource() == sendText) {
			try {
				String encryptedString = Base64.getEncoder().encodeToString(rsa.encrypt(textInput.getText()));
				aes.encrypt(rsa.getPrivateKey(),pattern.getText());
				//enviar por el socket
			} catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException
					| NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		for(int i = 0;i<9;i++) {
			if(e.getSource()==buttons[i]) {
				if(buttons[i].getForeground() == Color.black){
					buttons[i].setForeground(Color.red);
					pattern.setText(pattern.getText() + buttons[i].getText());
				}
				return;
			}
		}
	}
}
