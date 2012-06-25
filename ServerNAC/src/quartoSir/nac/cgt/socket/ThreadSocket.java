package quartoSir.nac.cgt.socket;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

import quartoSir.nac.domain.Cliente;
import quartoSir.nac.domain.util.TransferObject;

public class ThreadSocket implements Runnable {
	
	private Socket clientSocket;
	
	public ThreadSocket(Socket clientSocket){
		this.clientSocket = clientSocket;
	}
	
	@Override
	public void run() {
		this.handleCommands();	
	}
	
	/**
	 * @author Felipe 
	 * Responsável por receber e tratar comandos do cliente
	 */
	private void handleCommands() {
		ObjectInputStream in;
		PrintWriter out;
		Object obj = null;
				
		try {
			in = new ObjectInputStream(this.clientSocket.getInputStream());
			out = new PrintWriter(this.clientSocket.getOutputStream(),true);
			
			if(this.clientSocket != null && this.clientSocket.isBound()){
				
				try{

					while((obj = in.readObject()) == null){
						Thread.sleep(100);
					}
					
					if(obj instanceof TransferObject){
						this.trataObjetoRecebido((TransferObject) obj);
					}
					
					out.println("tudocerto");
					
					in.close();
					
				}catch (EOFException ex) {
					System.out.println("Leitura encerrada");
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		} catch (InterruptedException e){
			e.printStackTrace();
		}
		
		
	}
	
	
	public void trataObjetoRecebido(TransferObject to){
		System.out.println("Action: "+ to.getAction());
		
		if(to.getValue() instanceof Cliente){
			Cliente c = (Cliente) to.getValue();
			System.out.println(c.getNome());
		}
		
	}
	
	public void closeSocket(){
		try{
			this.clientSocket.close();
		}catch(IOException ex){
			System.out.println("Erro ao terminar a conexão...");
			ex.printStackTrace();
		}
	}
	
}
