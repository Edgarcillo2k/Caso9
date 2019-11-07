package gameserver;

import client.SocketClient;

public class GameClient {
	private SocketClient client;
	private String nickname;
	private String team;
	
	public GameClient(SocketClient pClient, String pNickName, String pTeam) {
		client = pClient;
		nickname = pNickName;
		team = pTeam;
	}
	
	public SocketClient getClient() {
		return client;
	}
	
	public void setClient(SocketClient client) {
		this.client = client;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getTeam() {
		return team;
	}
	
	public void setTeam(String team) {
		this.team = team;
	}
}
