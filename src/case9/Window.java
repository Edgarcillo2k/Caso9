package case9;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

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
	private JLabel keyEncrypted = new JLabel("");
	private JTextField textInput = new JTextField("");
	private JButton decryptText = new JButton("Decrypt text");
	private JLabel decryptedText = new JLabel("Decrypted text: ");
	private JButton changeModeButton = new JButton("Change mode");
	private boolean mode = false;
	private String encryptedMessage = null;
	private AES aes;
	private RSAUtil rsa;
	public Window() 
    {
		try {
			aes = new AES();
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
		decryptText.setBounds(0,430,330,30);
        decryptText.addActionListener(this);
		add(decryptText);
		decryptedText.setBounds(10,470,290,20);
		add(decryptedText);
		keyEncrypted.setBounds(40,400,290,20);
		add(keyEncrypted);
		decryptedText.setVisible(false);
		changeModeButton.setBounds(0,500,330,30);
        changeModeButton.addActionListener(this);
		add(changeModeButton);
        
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
	public void changeMode()
	{
		mode = !mode;
		sendText.setVisible(mode);
		textInput.setVisible(mode);
		decryptedText.setVisible(!mode);
		decryptedText.setText("Decrypted text: ");
		keyEncrypted.setVisible(!mode);
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
				String encryptedText = rsa.encrypt(textInput.getText());
				String encryptedKey = aes.encrypt(rsa.getPrivateKey(),pattern.getText());				
				//enviar por el socket
			} catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException
					| NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return;
		}
		if(e.getSource() == decryptText) {
			try {
				String decryptedKey = aes.decrypt(keyEncrypted.getText(), pattern.getText());
				rsa.setPrivateKey(decryptedKey);
				String decrypted = rsa.decrypt(encryptedMessage);
				decryptedText.setText("Decrypted text: " + decrypted);
			}
			catch(Exception e1) {
				// TODO Auto-generated catch block
				decryptedText.setText("Decrypted text: null");
			}
		}
		if(e.getSource() == changeModeButton) {
			changeMode();
			return;
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
