package quartoSir.nac.cgt.socket;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import quartoSir.nac.cgt.bo.ClienteBO;
import quartoSir.nac.domain.Cliente;
import quartoSir.nac.domain.util.ReturnObject;
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
		ObjectOutputStream out;
		Object obj = null;
				
		try {
			in = new ObjectInputStream(this.clientSocket.getInputStream());
			
			
			ReturnObject retorno = null;
			
			if(this.clientSocket != null && this.clientSocket.isBound()){
				
				try{

					while((obj = in.readObject()) == null){
						Thread.sleep(100);
					}
					
					if(obj instanceof TransferObject){
						retorno = this.trataObjetoRecebido((TransferObject) obj);
					}
					
					out = new ObjectOutputStream(this.clientSocket.getOutputStream());
					out.writeObject(retorno);
					out.flush();
					
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
	
	
	public ReturnObject trataObjetoRecebido(TransferObject to){
		System.out.println("Action: "+ to.getAction());
		
		//Chama os métodos do BO do cliente
		if(to.getValue() instanceof Cliente){
			ClienteBO bo = new ClienteBO();
			
			Cliente cli = (Cliente) to.getValue();
			
			if(to.getAction().equals("cadastro")){
				return bo.cadastraCliente(cli);
			}
			
		}
		
		return null;
		
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
