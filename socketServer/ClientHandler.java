package socketServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import upsidextre.comput.entryPoint.UpsiDextre;

public class ClientHandler extends Thread {
	
	@SuppressWarnings("unused")
	private Socket socket;
	private boolean connected = true;
	
	private PrintWriter out;
	private BufferedReader in;
	
	private CommProtocol commProtocol;

	public ClientHandler(Socket socket, UpsiDextre hardware) {
		this.socket = socket;
		
		commProtocol = new CommProtocol(hardware);
		
		try {
			
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		super.run();
		
		String inputLine;
		String outputLine;
		
		try {
			while (((inputLine = in.readLine()) != null) || connected) {
				outputLine = commProtocol.repondre(inputLine);
			    out.println(outputLine);
			    if (inputLine.equals("close"))
			        connected = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
