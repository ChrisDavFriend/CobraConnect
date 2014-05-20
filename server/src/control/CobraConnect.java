package control;

import java.util.LinkedList;
import java.util.List;


public class CobraConnect {
	
	public static CobraConnect instance;
	
	ClientListener cl;
	
	List<ClientHandle> connectedClients;
	
	public CobraConnect(int port){
		connectedClients = new LinkedList<ClientHandle>();
		cl = new ClientListener(port);
	}
	
	public void start(){
		cl.startListener();
	}
	
	public void sendMessage(ClientHandle sendingClient, String message){
		for(ClientHandle ch : connectedClients){
			if(ch != sendingClient)ch.send(sendingClient.userName, message);
		}
		System.out.println(sendingClient.userName + " sent message " + message);
	}
	
	public void addClientHandle(ClientHandle ch){
		ch.startClientHandle();
		connectedClients.add(ch);
	}

	public static void main(String[] args) {
		instance = new CobraConnect(2222);
		instance.start();
	}

}
