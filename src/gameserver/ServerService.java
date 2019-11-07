package gameserver;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import client.SocketClient;
import client.Server;
import common.Message;

public class ServerService implements Observer
{
	private ArrayList<GameClient> clients;
	private Hashtable<SocketClient, GameClient> socketclients;
	private Hashtable<String, ArrayList<GameClient>> teams;
	private Server server = null;
	
	public ServerService() {
		try 
		{
			server = new Server(this);
			clients = new ArrayList<GameClient>();
			socketclients = new Hashtable<SocketClient, GameClient>();
			teams = new Hashtable<String, ArrayList<GameClient>>();
			
		} catch (Exception ex) 
		{
			ex.printStackTrace();
		}
	}
	
	@Override
	public void update(Observable caller, Object pMsg) {
		SocketClient client = (SocketClient)caller; 

		if (pMsg instanceof Message) {
			Message msg = (Message)pMsg;
			switch(msg.getType()) {
				case 1: 
				{
					GameClient gclient = socketclients.get(client);
					
					System.out.println("Enviando reto a teams ");
					System.out.println("Texto Cifrado "+msg.getValue("encrypted_msg"));
					System.out.println("Llave asimetrica cifrada "+msg.getValue("encrypted_priv"));
					for(GameClient nclient : clients) {
						if (nclient.getTeam().compareTo(gclient.getTeam())!=0) {
							nclient.getClient().sendMsg(msg);
						}
					}
					break;
				}
				case 2: 
				{
					System.out.println("Mensaje descifrado: "+msg.getValue("found_msg"));
					GameClient gclient = socketclients.get(client);					
					System.out.println("Jugador "+gclient.getNickname() +" del equipo "+gclient.getTeam());
					break;
				}
				case 3: 
				{
					GameClient gclient = new GameClient(client, msg.getValue("nickname"), msg.getValue("teamname"));
					clients.add(gclient);
					socketclients.put(client, gclient);

					ArrayList<GameClient> gameclients = null;
					if (teams.containsKey(gclient.getTeam())) {
						gameclients = teams.get(gclient.getTeam());
					} else {
						gameclients = new ArrayList<GameClient>();
						teams.put(gclient.getTeam(), gameclients);
					}
					
					gameclients.add(gclient);
					System.out.println(gclient.getNickname()+" joined team "+gclient.getTeam());
					break;
				}
				default: {
					System.out.println("Bad message");
					System.out.println(msg.getJSonString());
				}
			}	
		}
		
		if (pMsg instanceof SocketClient) {
			GameClient gclient = socketclients.get(pMsg);
			socketclients.remove(pMsg);
			socketclients.remove(gclient);
		}
	}

	public void sendToOneRandom(Message pMsg) {
		Random rand = new Random();
		for(String teamname : teams.keySet()) {
			ArrayList<GameClient> gameclients = teams.get(teamname);
			gameclients.get(rand.nextInt(gameclients.size())).getClient().sendMsg(pMsg);
		}
	}	

}
