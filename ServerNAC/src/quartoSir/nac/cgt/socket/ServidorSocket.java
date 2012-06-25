package quartoSir.nac.cgt.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ServidorSocket {

		
	private ServerSocket socket;
	private final Integer PORT = 9090;
	
	
	/**
	 * @author Felipe
	 * Abre um socket de servidor.
	 */
	
	public void openSocket(){
		try{
			this.socket = new ServerSocket(PORT);
			
		}catch(IOException ex){
			System.out.println("Falha ao iniciar o socket... ");
			ex.printStackTrace();			
		}		
	}

	
	/**
	 * @author Felipe
	 * Coloca o socket em accept e trata as requisições do cliente
	 */
	public void handleRequest(){
		if ((socket != null) && (socket.isBound())) {
			try {
				
				while(true){
					Socket clientSocket;
					clientSocket = socket.accept();
					if (clientSocket != null) {
						//Em thread pra poder usar o sleep e não usar 100% de cpu quando estiver esperando comando.
						System.out.println("Cliente conectado:" + clientSocket.getInetAddress().getHostAddress() + ":" + clientSocket.getPort());
						ThreadSocket job = new ThreadSocket(clientSocket);
						Thread thread = new Thread(job);
						thread.start();
					}
				}
				
			} catch (SocketTimeoutException timeoutException) {
				System.out.println("Terminando por time-out");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

}

	
