package quartoSir.nac.cih;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import quartoSir.nac.domain.CPF;
import quartoSir.nac.domain.Cliente;
import quartoSir.nac.domain.ItemPedido;
import quartoSir.nac.domain.Pedido;
import quartoSir.nac.domain.Produto;

public class CadPedido {

public void executar(){
		
		String comando = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		//While que faz o formul�rio aparecer cada vez que uma execu��o terminar
		
		while(!comando.equals("5")){
			
			System.out.println("");
		
			System.out.println("> Selecione uma op��o: ");
			System.out.println("> 1 - Cadastrar novo pedido");
			System.out.println("> 2 - Listar pedido(s)");
			System.out.println("> 3 - Alterar pedido");
			System.out.println("> 4 - Excluir pedido");
			System.out.println("> 5 - Voltar");
	
			System.out.print("> ");
			
			try {
			    comando = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//Chamada de cada um dos m�todos
			
			if(comando.equals("1")){
				cadastrarPedido();
			}else if(comando.equals("2")){
				listarPedido();
			}else if(comando.equals("3")){
				alterarPedido();
			}else if(comando.equals("4")){
				excluirPedido();
				
			//Tratamento para op��o incorreta
				
			}else if(!comando.equals("5")){
				System.out.println("");
				System.out.println("> Mensagem inv�lida! Insira um valor de 1 a 5");
			}
		}
	}
				
			
		
		
	public Pedido cadastrarPedido(){
		Pedido ped = new Pedido();
		Cliente cli = new Cliente();
		Produto prod = new Produto();
		ItemPedido item = new ItemPedido();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));;
		String comando = "";
		
		//Solicita ao usu�rio o tipo de inser��o a ser executada
		
		System.out.println("> Selecione uma op��o: ");
		System.out.println("> 1 - Cadastrar novo pedido");
		System.out.println("> 2 - Cadastrar novo item em pedido existente");
		System.out.print("> ");
		
		try {
		    comando = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(comando.equals("1")){
			
			//Solicita ao usu�rio o tipo de pesquisa de cliente a ser executada
			
			System.out.println("");
			System.out.println("> Para cadastrar um novo pedido, � necess�rio selecionar um cliente. Voc� quer pesquis�-lo por: ");
			System.out.println("> 1 - ID");
			System.out.println("> 2 - Nome");
			System.out.print("> ");
			
			try {
			    comando = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(comando.equals("1")){
				
				//Solicita ao usu�rio o ID do cliente
				
				System.out.println("");
				System.out.println("> Digite o ID do cliente: ");
				System.out.print("> ");
				
				try {
				    comando = reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				cli.setId(Long.valueOf(comando));
			}else if(comando.equals("2")){
				
				//Solicita ao usu�rio o nome do cliente
				
				System.out.println("");
				System.out.println("> Digite o nome, ou a parte inicial do nome do cliente: ");
				System.out.print("> ");
				
				try {
				    comando = reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				cli.setNome(comando);
				
			//Tratamento para op��o incorreta
				
			}else{
				System.out.println("");
				System.out.println("> Mensagem inv�lida! Insira um valor de 1 a 2");
				return null;
			}
			
			//Solicita ao usu�rio o tipo de pesquisa de produto a ser feita
			
			System.out.println("");
			System.out.println("> Para cadastrar um novo pedido, � necess�rio selecionar um produto inicial. Voc� quer pesquis�-lo por: ");
			System.out.println("> 1 - ID");
			System.out.println("> 2 - Descri��o");
			System.out.print("> ");
			
			try {
			    comando = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(comando.equals("1")){
				
				//Solicita ao usu�rio o ID do produto
				
				System.out.println("");
				System.out.println("> Digite o ID do produto: ");
				System.out.print("> ");
				
				try {
				    comando = reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				prod.setId(Long.valueOf(comando));
			}else if(comando.equals("2")){
				
				//Solicita ao usu�rio a descri��o do produto
				
				System.out.println("");
				System.out.println("> Digite a descri��o, ou a parte inicial da descri��o do produto: ");
				System.out.print("> ");
				
				try {
				    comando = reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				prod.setDescricao(comando);
				
			//Tratamento para op��o incorreta
				
			}else{
				System.out.println("");
				System.out.println("> Mensagem inv�lida! Insira um valor de 1 a 2");
				return null;
			}
			
			//Solicita ao usu�rio a quantidade do produto
			
			System.out.println("");
			System.out.println("> Digite a quantidade desejada do produto que voc� escolheu: ");
			System.out.print("> ");
			
			try {
			    comando = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//Valida��o de quantidade
			
			if(Integer.parseInt(comando)<=0){
				System.out.println("");
				System.out.println("> Quantidade n�o pode ser zero ou menor que zero.");
				return null;
			}
	
			
			item.setQuantidade(Integer.parseInt(comando));
			
			System.out.println("");
			System.out.println("> Pedido " +ped.getId()+" e Item " + item.getId() + " cadastrados com sucesso!");
						
		}else if(comando.equals("2")){
					
			//Solicita ao usu�rio o ID do pedido
			
			System.out.println("");
			System.out.println("> Digite o ID do pedido: ");
			System.out.print("> ");
			
			reader = new BufferedReader(new InputStreamReader(System.in));
			
			try{
				comando = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			ped.setId(Long.valueOf(comando));
			
			//Solicita ao usu�rio o tipo de pesquisa de produto a ser feita
			
			System.out.println("");
			System.out.println("> � necess�rio escolher o produto a ser inserido no pedido. Voc� quer pesquis�-lo por: ");
			System.out.println("> 1 - ID");
			System.out.println("> 2 - Descri��o");
			System.out.print("> ");
			
			try {
			    comando = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(comando.equals("1")){
				
				//Solicita ao usu�rio o ID do produto
				
				System.out.println("");
				System.out.println("> Digite o ID do produto: ");
				System.out.print("> ");
				
				try {
				    comando = reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				prod.setId(Long.valueOf(comando));
			}else if(comando.equals("2")){
				
				//Solicita ao usu�rio a descri��o do produto
				
				System.out.println("");
				System.out.println("> Digite a descri��o, ou a parte inicial da descri��o do produto: ");
				System.out.print("> ");
				
				try {
				    comando = reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				prod.setDescricao(comando);
				
			//Tratamento para op��o incorreta
				
			}else{
				System.out.println("");
				System.out.println("> Mensagem inv�lida! Insira um valor de 1 a 2");
				return null;
			}
			
			//Solicita ao usu�rio a quantidade do produto
			
			System.out.println("");
			System.out.println("> Digite a quantidade desejada do produto que voc� escolheu: ");
			System.out.print("> ");
			
			try {
			    comando = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//Valida��o da quantidade
			
			if(Integer.parseInt(comando)<=0){
				System.out.println("");
				System.out.println("> Quantidade n�o pode ser zero ou menor que zero.");
				return null;
			}
						
			item.setQuantidade(Integer.parseInt(comando));
			
			System.out.println("");
			System.out.println("> Item " + item.getId() + " cadastrado com sucesso!");
			
		//Tratamento para op��o incorreta
			
		}else{
			System.out.println("");
			System.out.println("> Mensagem inv�lida. Escolha um n�mero de 1 a 2");
			return null;
		}
		return ped;
	}
	
	public Pedido listarPedido(){
		Pedido ped = new Pedido();
		Cliente cli = new Cliente();
		BufferedReader reader;
		String comando = "";
		CPF cpf;
		
		//Solicita ao usu�rio o tipo de pesquisa a ser feita
		
		System.out.println("");
		System.out.println("> Selecione o tipo de pesquisa:");
		System.out.println("> 1) Por ID do pedido");
		System.out.println("> 2) Por cliente");
		
		System.out.print("> ");
				
		reader = new BufferedReader(new InputStreamReader(System.in));
		
		try{
			comando = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(comando.equals("1")){
			
			//Solicita ao usu�rio o ID do pedido
			
			System.out.println("");
			System.out.println("> Digite o ID do pedido que deseja pesquisar:");
			System.out.print("> ");
			
			reader = new BufferedReader(new InputStreamReader(System.in));
			
			try{
				comando = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			ped.setId(Long.valueOf(comando));
			
		}else if(comando.equals("2")){	
			
			//Solicita ao usu�rio o tipo de pesquisa de cliente a ser feita
			
			System.out.println("");
			System.out.println("> Selecione o tipo de pesquisa:");
			System.out.println("> 1) Por ID");
			System.out.println("> 2) Por nome do cliente");
			System.out.println("> 3) Por CPF do cliente");
			
			System.out.print("> ");
					
			reader = new BufferedReader(new InputStreamReader(System.in));
			
			try{
				comando = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(comando.equals("1")){
				
				//Solicita ao usu�rio o ID do cliente
				
				System.out.println("");
				System.out.println("> Digite o ID do cliente que deseja pesquisar:");
				System.out.print("> ");
				
				reader = new BufferedReader(new InputStreamReader(System.in));
				
				try{
					comando = reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				cli.setId(Long.valueOf(comando));
				
				
			}else if(comando.equals("2")){
				
				//Solicita ao usu�rio o nome do cliente
				
				System.out.println("");
				System.out.println("> Digite o nome, ou in�cio do nome, do cliente que deseja pesquisar:");
				System.out.print("> ");
				
				reader = new BufferedReader(new InputStreamReader(System.in));
				
				try{
					comando = reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				cli.setNome(comando);
			}else if(comando.equals("3")){
				
				//Solicita ao usu�rio o CPF do cliente
				
				System.out.println("");
				System.out.println("> Digite o CPF do cliente que deseja pesquisar:");
				System.out.print("> ");
				
				reader = new BufferedReader(new InputStreamReader(System.in));
				
				try{
					comando = reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				//Valida��o de CPF
				
				try{
					cpf = new CPF(comando);
				}catch (Exception e) {
					System.out.println("");
					System.out.println("> CPF inv�lido!");
					return null;
				}
				
				cli.setCpf(cpf);
				
			//Tratamento para op��o incorreta
				
			}else{
				System.out.println("");
				System.out.println("> Mensagem inv�lida! Insira um valor de 1 a 3");
				return null;
			}
			
		//Tratamento para op��o incorreta
		
		}else{
			System.out.println("");
			System.out.println("> Mensagem inv�lida! Insira um valor de 1 a 2");
			return null;
		}
		return ped;
	}
	
	public Pedido alterarPedido(){

		Pedido ped = new Pedido();
		BufferedReader reader;
		String comando = "";
		Produto prod = new Produto();
		ItemPedido item = new ItemPedido();
		
		//Solicita ao usu�rio o Id do pedido
		
		System.out.println("");
		System.out.println("> Digite o ID do pedido que deseja alterar:");
		System.out.print("> ");
		
		reader = new BufferedReader(new InputStreamReader(System.in));
		
		try{
			comando = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Solicita ao usu�rio o tipo de pesquisa de produto a ser feita
		
		System.out.println("");
		System.out.println("> � necess�rio escolher o produto que deseja alterar. Voc� quer pesquis�-lo por: ");
		System.out.println("> 1 - ID");
		System.out.println("> 2 - Descri��o");
		System.out.print("> ");
		
		try {
		    comando = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(comando.equals("1")){
			
			//Solicita ao usu�rio o ID do produto
			
			System.out.println("");
			System.out.println("> Digite o ID do produto: ");
			System.out.print("> ");
			
			try {
			    comando = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			prod.setId(Long.valueOf(comando));
		}else if(comando.equals("2")){
			
			//Solicita ao usu�rio a descri��o do produto
			
			System.out.println("");
			System.out.println("> Digite a descri��o, ou a parte inicial da descri��o do produto: ");
			System.out.print("> ");
			
			try {
			    comando = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			prod.setDescricao(comando);
			
		//Tratamento para op��o incorreta
			
		}else{
			System.out.println("");
			System.out.println("> Mensagem inv�lida! Insira um valor de 1 a 2");
			return null;
		}
		
		//Solicita ao usu�rio a nova quantidade do produto no pedido
		
		System.out.println("");
		System.out.println("> Digite a nova quantidade do produto no pedido escolhido: ");
		System.out.print("> ");
		
		try {
		    comando = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Valida��o de quantidade
		
		if(Integer.parseInt(comando)<=0){
			System.out.println("");
			System.out.println("> Quantidade n�o pode ser zero ou menor que zero.");
			return null;
		}
					
		item.setQuantidade(Integer.parseInt(comando));
		
		System.out.println("");
		System.out.println("> Pedido " + ped.getId() + " atualizado com sucesso!");
		
		return ped;

	}

	public Pedido excluirPedido(){
		
		Pedido ped = new Pedido();
		Produto prod = new Produto();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));;
		String comando = "";
		
		//Solicita ao usu�rio o tipo de exclus�o a ser feita
		
		System.out.println("> Selecione uma op��o: ");
		System.out.println("> 1 - Excluir pedido");
		System.out.println("> 2 - Exluir item");
		System.out.print("> ");
		
		try {
		    comando = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(comando.equals("1")){
			
			//Solicita ao usu�rio o ID do pedido a ser excluido
			
			System.out.println("");
			System.out.println("> Digite o ID do pedido que deseja excluir:");
			System.out.print("> ");
			
			reader = new BufferedReader(new InputStreamReader(System.in));
			
			try{
				comando = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			ped.setId(Long.valueOf(comando));
			
		}else if(comando.equals("2")){
			
			//Solicita ao usu�rio o ID do pedido a ser exclu�do
			
			System.out.println("");
			System.out.println("> Digite o ID do pedido que deseja excluir:");
			System.out.print("> ");
			
			reader = new BufferedReader(new InputStreamReader(System.in));
			
			try{
				comando = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			ped.setId(Long.valueOf(comando));
			
			//Solicita ao usu�rio qual par�metro utilizar� na exclus�o
			
			System.out.println("");
			System.out.println("> � necess�rio escolher o produto do pedido que deseja excluir. Voc� quer pesquis�-lo por: ");
			System.out.println("> 1 - ID");
			System.out.println("> 2 - Descri��o");
			System.out.print("> ");
			
			try {
			    comando = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(comando.equals("1")){
				
				//Solicita ao usu�rio o ID do produto para exclus�o
				
				System.out.println("");
				System.out.println("> Digite o ID do produto: ");
				System.out.print("> ");
				
				try {
				    comando = reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				prod.setId(Long.valueOf(comando));
			}else if(comando.equals("2")){
				
				//Solicita ao usu�rio a descri��o do produto para exclus�o
				
				System.out.println("");
				System.out.println("> Digite a descri��o, ou a parte inicial da descri��o do produto: ");
				System.out.print("> ");
				
				try {
				    comando = reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				prod.setDescricao(comando);
				
			//Tratamento para op��o incorreta
				
			}else{
				System.out.println("");
				System.out.println("> Mensagem inv�lida! Insira um valor de 1 a 2");
				return null;
			}
			
		//Tratamento para op��o incorreta
			
		}else{
			System.out.println("");
			System.out.println("> Mensagem inv�lida! Insira um valor de 1 a 2");
			return null;
		}
		
		//PARTE DO FELIPE
		
		System.out.println("");
		System.out.println("> Pedido " +ped.getId()+" exclu�do com sucesso!");
		
		return ped;
		
	}
}
