package quartoSir.nac.bootstrap;

import quartoSir.nac.cgt.socket.ServidorSocket;

public class Run {
	
	public static String enderecoBD;
	
	
	public static void main(String[] args) {
		if(args != null && args.length >0){
			if(args[0] == null || !args[0].equals("")){
				System.out.println("Endereço do banco de dados não especificado, usando 127.0.0.1.");
				enderecoBD = "127.0.0.1";
			}else{
				enderecoBD = args[0];
			}
		}else{
			System.out.println("Endereço do banco de dados não especificado, usando 127.0.0.1.");
			enderecoBD = "127.0.0.1";
		}
		
		
		System.out.println("Servidor iniciado, aguardando conexões.");
		ServidorSocket socket = new ServidorSocket();
		socket.openSocket();
			
		while(true){
			socket.handleRequest();
		}		
		
	}
}
