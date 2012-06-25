package quartoSir.nac.bootstrap;

import quartoSir.nac.cgt.socket.ServidorSocket;



public class Run {
	public static void main(String[] args) {
		
		System.out.println("Servidor iniciado, aguardando conexões.");
		ServidorSocket socket = new ServidorSocket();
		socket.openSocket();
			
		while(true){
			socket.handleRequest();
		}		
		
	}
}
