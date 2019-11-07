package test;

import java.io.IOException;

import client.SocketClient;
import common.Message;

public class ProgramServer {

	public static void main(String[] args) {
		MainWindow window = new MainWindow("Servidor de juego");
		
		try {
			System.in.read();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		SocketClient c1 = new SocketClient("127.0.0.1"); // conecto
		SocketClient c2 = new SocketClient("127.0.0.1");
		SocketClient c3 = new SocketClient("127.0.0.1");
		SocketClient c4 = new SocketClient("127.0.0.1");

		/*
			{
				"type" : "0", // selected for challenge
			}
			
			{
				"type": "1", // challenge
				"encrypted_msg" : "",
				"encrypted_priv": ""
			}

			{
				"type": "2", // challenge found
				"found_msg" : ""
			}

			{
				"type": "3", // checkin my team
				"teamname" : "",
				"nickname" : ""
			}

		*/
		
		
		Message msg = new Message(3);
		msg.addField("teamname", "Team A"); // me registro dentro de un team
		msg.addField("nickname", "Pedro");
		c1.sendMsg(msg);
		msg.addField("nickname", "Maria");
		c3.sendMsg(msg);
		
		msg = new Message(3);
		msg.addField("teamname","Team B");
		msg.addField("nickname", "Ana");
		c2.sendMsg(msg);
		msg.addField("nickname", "Jose");
		c4.sendMsg(msg);
		
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		msg = new Message(2); // aviso que resolvi el encrypted
		msg.addField("found_msg", "Hola hola lo logré");
		c3.sendMsg(msg); 
		
	}
}
