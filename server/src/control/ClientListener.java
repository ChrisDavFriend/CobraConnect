package control;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientListener implements Runnable {
	
	private ServerSocket server;
	
	private Thread thread;
	private String threadName = "Client Listener";
	
	public ClientListener(int port){
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Created client listener thread with port " + port);
	}
	
	public void startListener(){
		System.out.println("Starting listener...");
		if(thread == null){
			thread = new Thread(this, threadName);
			thread.start();
		}else{
			System.out.println("Listener thread has already been started!");
		}
	}
	
	
	public void run(){
		while(true){
			try {
				System.out.println("Waiting for a connection on port " + server.getLocalPort() + "...");
				Socket client = server.accept();//Waits for a clients connection
				System.out.println("Established connection to " + client.getRemoteSocketAddress() + ".");
				
				ClientHandle ch = new ClientHandle(client); 
				CobraConnect.instance.addClientHandle(ch);
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

}
