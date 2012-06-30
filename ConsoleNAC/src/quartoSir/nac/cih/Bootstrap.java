package quartoSir.nac.cih;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Bootstrap {
	
	public static String enderecoServidor;
	
	public static void main(String args[]){
		
		if(args != null && args.length >0 && args[0] != null && !args[0].equals("")){
			enderecoServidor = args[0];
		}else{
			System.out.println("IP do servidor não definido. Usando 127.0.0.1");
			enderecoServidor = "127.0.0.1";
		}
		

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		//While que faz o formulário aparecer toda vez que uma execução terminar
		
		String comando = "";
		
		while(!comando.equals("4")){
			
			System.out.println("");
					
			//Cria o menu, por onde o programa irá iniciar e por onde será feita a seleção do tipo de cadastro a ser feito
			
			System.out.println("> Selecione uma opção: ");
			System.out.println("> 1 - Cadastro de Clientes");
			System.out.println("> 2 - Cadastro de Produtos");
			System.out.println("> 3 - Cadastro de Pedidos");
			System.out.println("> 4 - Sair");
			
			try {
			    comando = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
	
			//Chamada de cada um dos cadastros
			
			if(comando.equals("1")){
				CadCliente cli = new CadCliente();
				cli.executar();
			}
			else if(comando.equals("2")){
				CadProduto prod = new CadProduto();
				prod.executar();
			}
			else if(comando.equals("3")){
				CadPedido ped = new CadPedido();
				ped.executar();
			}
			
			//Tratamento para opção incorreta
			
			else if(!comando.equals("4")){
				System.out.println("");
				System.out.println("> Mensagem inválida! Insira um valor de 1 a 4");
			}else{
				System.out.println("");
				System.out.println("Execução finalizada");
			}
		}
	}
}
