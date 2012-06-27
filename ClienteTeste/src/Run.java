import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

import quartoSir.nac.domain.Cliente;
import quartoSir.nac.domain.util.TransferObject;

public class Run {

	private static BufferedReader in;
	private static ObjectOutputStream out;
	private static  Socket socket;
	
	public static void main(String[] args) {
		openSocket("127.0.0.1");
		
/*		TransferObject to = new TransferObject();
		Cliente cli = new Cliente();
		cli.setNome("Jonas");
		
		to.setAction("Comer o cu do: ");
		to.setValue(cli);
		
		send(to);
		read();
*/
		
	}
	
	public static void openSocket(String ip){
		try{
			socket = new Socket(ip,9090);
			
			if(socket != null && socket.isBound()){
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				//out = new PrintWriter(socket.getOutputStream(),true);
			}
			
		}catch(IOException ex){
			ex.printStackTrace();
			System.out.println("Erro de conexão, flw!");
			System.exit(1);
		}	
	}
	
	public static void send(Object obj){
		try{
			out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(obj);
			out.flush();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void read(){
		try{
			System.out.println(in.readLine());
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	
	
}
