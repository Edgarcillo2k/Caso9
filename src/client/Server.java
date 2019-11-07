package client;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observer;
import client.SocketClient;

public class Server implements Runnable {
	private boolean active;
	private ServerSocket server;
	private final int PORT = 4000;
	private Observer connObserver;
	
	public Server(Observer connObserver) {
		try 
		{
			this.connObserver = connObserver;
			active = true;
			server = new ServerSocket(PORT);

			Thread hiloserver = new Thread(this);
			hiloserver.start();

		} catch (Exception ex) 
		{
			active = false;
			ex.printStackTrace();
		}
	}

	public void run() {
		System.out.println("Listening on port "+PORT);
		
		while (active) 
		{
			try 	
			{
				Socket socket = server.accept();
				SocketClient client = new SocketClient(socket);
				client.addObserver(connObserver);
				Thread.sleep(25);
			}
			catch (Exception ex)
			{
				active = false;
				ex.printStackTrace();
			}			
		}
		System.out.println("Closing server");
	}
}
