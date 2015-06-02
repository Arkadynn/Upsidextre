package socketServer;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import upsidextre.comput.entryPoint.UpsiDextre;

public class Server {
	
	private ServerSocket serverSocket;
	private Socket tmpClientSocket;
	
	private UpsiDextre hardware;
	
	private ArrayList<Socket> clients;
	
	
	public Server(UpsiDextre hardware) {
		
		this.hardware = hardware;
		
		clients = new ArrayList<Socket>();
		
		try {
			serverSocket = new ServerSocket(42147);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void connect () {
		while (true) {
			try {
				
				tmpClientSocket = serverSocket.accept();
				clients.add(tmpClientSocket);
				
				new ClientHandler(tmpClientSocket, hardware).start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<Socket> getClients () {
		return this.clients;
	}
}
