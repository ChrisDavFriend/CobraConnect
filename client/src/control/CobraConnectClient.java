package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class CobraConnectClient {
	
	public static final int COMMUNE_PORT = 2222;
	
	public static void main(String[] args) throws InterruptedException {
		try {
			String ip = "174.51.118.196";
			System.out.print("Enter User Name:\t");

			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String userName = br.readLine();
			
			System.out.println("Trying to connect to " + ip + ":" + COMMUNE_PORT);
			
			Socket server = new Socket("localhost", COMMUNE_PORT);
			
			System.out.println("Connected to " + server.getRemoteSocketAddress() + " with user name " + userName + "!");
			
			BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
			PrintWriter out = new PrintWriter(server.getOutputStream());
			
			out.println(userName);
			out.flush();
			
			OutStream os = new OutStream(out, br);
			os.startOutStream();
			
			while(true){
				System.out.println(in.readLine());
			}
			
			

			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}
	
	public static class OutStream implements Runnable {
		
		PrintWriter outputStream;
		BufferedReader in;
		Thread thread;
		
		public OutStream(PrintWriter outputStream, BufferedReader br){
			this.outputStream = outputStream;
			this.in = br;
		}
		
		public void startOutStream(){
			if(thread == null){
				thread = new Thread(this, "out");
				thread.start();
			}
		}
		
		public void run() {
			while(true){
				try {
					outputStream.println(in.readLine());
					outputStream.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}

}
