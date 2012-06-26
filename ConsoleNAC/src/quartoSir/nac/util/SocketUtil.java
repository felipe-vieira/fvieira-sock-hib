package quartoSir.nac.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import quartoSir.nac.domain.util.ReturnObject;

public class SocketUtil {
	
	private Socket socket;
	private ObjectInputStream objIn;
	private ObjectOutputStream objOut;
	
	
	/**
	 * Abre o socket e inicia os streams
	 * @param ip
	 */
	
	public void conectaSocket(String ip){
		try{
			socket = new Socket(ip,9090);
		}catch(IOException ex){
			System.out.println("Erro de conexão com o servidor");
			ex.printStackTrace();
		}	
	}
	
	public void enviaObjeto(Serializable obj){
		try{
			if(socket != null && socket.isBound()){
				objOut = new ObjectOutputStream(socket.getOutputStream());
				objOut.writeObject(obj);
				objOut.flush();
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ReturnObject recebeObjeto(){
		
		Object obj = null;
		ReturnObject ro = null;
		
		try{
			if(this.socket != null && this.socket.isBound()){
				objIn = new ObjectInputStream(socket.getInputStream());
				while((obj = objIn.readObject()) == null){}
				ro = (ReturnObject) obj;
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}catch(ClassNotFoundException ex){
			ex.printStackTrace();
		}
		
		return ro;
	}
	
}
