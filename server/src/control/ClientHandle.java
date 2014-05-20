package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandle implements Runnable {
	
	private Socket client;
	private BufferedReader in;
	private PrintWriter out;
	
	public Thread thread;
	
	
	public String userName;

	
	public ClientHandle(Socket client){
		this.client = client;
		//this.client.close();
	}
	
	public void startClientHandle(){
		if(thread == null){
			System.out.println("Starting client handle for (" + client.getRemoteSocketAddress() + ")...");
			thread = new Thread(this, "client handle");
			thread.start();
		}else{
			System.out.println("A client handle for this user already exists!");
		}
	}


	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(), true);
			userName = in.readLine();//in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Client handle created for user " + userName + " (" + client.getRemoteSocketAddress() + ")!");
		
		while(true){
			try {
				String msg = in.readLine();
				CobraConnect.instance.sendMessage(this, msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void send(String userName, String message){
		out.println(userName + ":\t" + message);
	}
	
	

}
