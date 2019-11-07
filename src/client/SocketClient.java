package client;

import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Observable;

import common.Message;

public class SocketClient extends Observable implements Runnable {
	private Socket socket;
	private DataInputStream input;
	private DataOutputStream output;
	private boolean active;
	private final int PORT = 4000;
	private Message msg;
	
	public SocketClient(Socket pSocket) {
		this.socket = pSocket;
		try {
			this.input = new DataInputStream(pSocket.getInputStream());
			this.output = new DataOutputStream(pSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		active = true;
		start();
	}

	public SocketClient(String pIpAddress) {
		try 
		{
			this.socket = new Socket(pIpAddress, PORT);
			this.input = new DataInputStream(socket.getInputStream());
			this.output = new DataOutputStream(socket.getOutputStream());
			active = true;
			start();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}
	}
	
	/**
	 * Envia un mensaje al destino una vez conectado
	 * @param pData String a ser enviada
	 * @return true si logró enviar el mensaje
	 */
	public boolean sendMsg(Message pData) {
		boolean result = false;
		try 
		{
			output.writeUTF(pData.getJSonString());
			output.flush();
			result = true;
		} catch (Exception ex) 
		{
			System.out.println("Closing connection");
			close();
		}
		return result;
	}
	
	/**
	 * Cierra la conexion actual
	 */
	public void close() {
		try 
		{
			active = false;
			output.close();
			input.close();
			socket.close();		
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	private void start() {
		Thread hilo = new Thread(this);
		hilo.start();
	}

	@Override
	public void run() {
		try 
		{
			while (active) 
			{
				String read = input.readUTF();
				Message msg = new Message(read);
				this.setChanged();
				this.notifyObservers(msg);
				Thread.sleep(50);
			}
		} 
		catch (Exception ex)
		{
			this.notifyObservers(this);
		}
	}
	
	public Message getMsg()
	{
		return this.msg;
	}
	public void setMsg(Message msg)
	{
		this.msg = msg;
	}
}
