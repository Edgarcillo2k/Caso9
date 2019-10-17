package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import client.SocketClient;
import common.Message;

public class ServerService implements Runnable, Observer
{
	private boolean active;
	private ServerSocket server;
	private final int PORT = 4000;
	private Hashtable<String, ArrayList<SocketClient>> teams;
	private Hashtable<Integer, String> socketteams;
	private ArrayList<String> teamnames;
	
	public ServerService() {
		try 
		{
			active = true;
			server = new ServerSocket(PORT);
			teams = new Hashtable<String, ArrayList<SocketClient>>();
			teamnames = new ArrayList<String>();
			socketteams = new Hashtable<Integer, String>();
			Thread hiloserver = new Thread(this);
			hiloserver.start();
		} catch (Exception ex) 
		{
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
				client.addObserver(this);
				Thread.sleep(50);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}			
		}
	}
	
	public void sendToTeam(String pTeam, Message pMsg) {
		for(SocketClient client : teams.get(pTeam)) {
			client.sendMsg(pMsg);
		}
	}
	
	public ArrayList<String> getTeamNames() {
		return teamnames;
	}
	
	public void sendToOneRandom(String pTeam, Message pMsg) {
		Random rand = new Random();
		ArrayList<SocketClient> members = teams.get(pTeam);
		SocketClient client = members.get(rand.nextInt(members.size()));
		
		client.sendMsg(pMsg);
	}	

	@Override
	public void update(Observable caller, Object pMsg) {
		if (pMsg instanceof Message) {
			Message msg = (Message)pMsg;
			SocketClient client = (SocketClient)caller; 
			switch(msg.getType()) {
				case 1: 
				{
					String team = socketteams.get(client.hashCode());
					
					System.out.println("Enviando reto a teams ");
					for(String teamname : teamnames) {
						if (teamname.compareTo(team)!=0) {
							sendToTeam(teamname, msg);
							System.out.println(teamname);
						}
					}
					break;
				}
				case 2: 
				{
					System.out.println("Mensaje descifrado: "+msg.getValue("found_msg"));
					String team = socketteams.get(client.hashCode());
					System.out.println("GANA: "+team);
					break;
				}
				case 3: 
				{
					if (!teams.contains(msg.getValue("teamname"))) {
						teams.put(msg.getValue("teamname"), new ArrayList<SocketClient>());
					}
					ArrayList<SocketClient> members = teams.get(msg.getValue("teamname"));
					
					members.add(client);
					socketteams.put(client.hashCode(), msg.getValue("teamname"));
					teamnames.add(msg.getValue("teamname"));
					System.out.println("New member in "+msg.getValue("teamname"));
					break;
				}
				default: {
					System.out.println("Bad message");
					System.out.println(msg.getJSonString());
				}
			}
		}
	}
}
