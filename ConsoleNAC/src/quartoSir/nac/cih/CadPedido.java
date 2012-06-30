package quartoSir.nac.cih;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.List;

import quartoSir.nac.domain.CPF;
import quartoSir.nac.domain.Cliente;
import quartoSir.nac.domain.ItemPedido;
import quartoSir.nac.domain.Pedido;
import quartoSir.nac.domain.Produto;
import quartoSir.nac.domain.util.ReturnObject;
import quartoSir.nac.domain.util.TransferObject;
import quartoSir.nac.util.ComunicacaoServidor;

public class CadPedido {

public void executar(){
		
		String comando = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		//While que faz o formul�rio aparecer cada vez que uma execu��o terminar
		
		while(!comando.equals("6")){
			
			System.out.println("");
		
			System.out.println("> Selecione uma op��o: ");
			System.out.println("> 1 - Cadastrar novo pedido");
			System.out.println("> 2 - Listar pedido(s)");
			System.out.println("> 3 - Alterar pedido");
			System.out.println("> 4 - Excluir pedido");
			System.out.println("> 5 - Processa pedido");
			System.out.println("> 6 - Voltar");
	
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
			}else if(comando.equals("5")){
				processarPedido();
			}else if(!comando.equals("6")){
				System.out.println("");
				System.out.println("> Mensagem inv�lida! Insira um valor de 1 a 6");
			}
		}
	}
				
			
		
		
	private void processarPedido() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));;
		String comando = "";
		
		Pedido pedido = new Pedido();
		ReturnObject ro;
	
		System.out.println("");
		
		while(pedido.getId() == null){
			try {
				System.out.println("> Digite o ID do pedido: ");
			    comando = reader.readLine();
			    pedido.setId(Long.parseLong(comando));
			} catch (IOException e) {
				e.printStackTrace();
			} catch(NumberFormatException e){
				System.out.println("O ID deve ser númerico!");
			}
		}
		
		TransferObject toBusca = new TransferObject("pesquisaID",pedido);
		ro = ComunicacaoServidor.enviaDados(toBusca);
		
		if(ro.getObj() != null){
			pedido = (Pedido) ro.getObj();
			TransferObject to = new TransferObject("processa", pedido);
			ro = ComunicacaoServidor.enviaDados(to);
			if(ro != null){
				System.out.println(ro.getMensagem());
			}
		}

	}




	public void cadastrarPedido(){
		Pedido pedido = new Pedido();
		Cliente cli = new Cliente();
		Produto prod = new Produto();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));;
		String comando = "";
		
		ReturnObject ro;
	
		System.out.println("");
		
		while(cli.getId() == null){
			try {
				System.out.println("> Digite o ID do cliente: ");
			    comando = reader.readLine();
			    cli.setId(Long.parseLong(comando));
			} catch (IOException e) {
				e.printStackTrace();
			} catch(NumberFormatException e){
				System.out.println("O ID deve ser númerico!");
			}
		}
		
		TransferObject toBuscaCli = new TransferObject("pesquisaID", cli);
		ro = ComunicacaoServidor.enviaDados(toBuscaCli);
		
		if(ro.getObj() != null){
			cli = (Cliente) ro.getObj();
		}
		
		if(cli != null){
			pedido = new Pedido(cli);
			TransferObject toSalvaPedido = new TransferObject("cadastro",pedido);
			ro = ComunicacaoServidor.enviaDados(toSalvaPedido);
			
			if(ro.getObj() != null){
				pedido = (Pedido) ro.getObj();
			}
			
			this.adicionaItems(pedido);
			
			//Calcula o valor total parcial do pedido
			TransferObject toCalculo = new TransferObject("calcula",pedido);
			ComunicacaoServidor.enviaDadosSemRetorno(toCalculo);
			
		}else{
			System.out.println("Cliente inexistente.");
		}


	}
	
	public Pedido listarPedido(){
		Pedido ped = new Pedido();
		Cliente cli = new Cliente();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String comando = "";
		CPF cpf;
		ReturnObject ro;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
		
		//Solicita ao usu�rio o tipo de pesquisa a ser feita
		
		System.out.println("");
		System.out.println("> Selecione o tipo de pesquisa:");
		System.out.println("> 1) Por ID do pedido");
		System.out.println("> 2) Por cliente");
		
		System.out.print("> ");
				
		
		try{
			comando = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(comando.equals("1")){
			
			//Solicita ao usu�rio o ID do pedido
			ped = new Pedido();
			
			System.out.println("");
			System.out.println("> Digite o ID do pedido que deseja pesquisar:");
			System.out.print("> ");
				
			while(ped.getId() == null){
				try{
					comando = reader.readLine();
					ped.setId(Long.parseLong(comando));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			TransferObject to = new TransferObject("pesquisaID", ped);
			ro = ComunicacaoServidor.enviaDados(to);
			
			System.out.println(ro.getMensagem());
			
			if(ro.getObj()!= null){
				ped = (Pedido) ro.getObj();
				System.out.println("ID: " + ped.getId());
				System.out.println("Data de emissão: " + sdf.format(ped.getDataEmissao()));
				if(ped.getDataProcessamento() != null){
					System.out.println("Data de processamento: " + sdf.format(ped.getDataProcessamento()));
				}else{
					System.out.println("Data de processamento: Não processado" );
				}
				System.out.println("Total do Pedido: " + ped.getTotalPedido());
				System.out.println("Quantidade de produtos: " + ped.getQtdProdutos());
				
			}
			
			
			ped.setId(Long.valueOf(comando));
			
		}else if(comando.equals("2")){	
			
			//Solicita ao usu�rio o ID do pedido
			cli = new Cliente();
			
			System.out.println("");
			System.out.println("> Digite o ID do cliente que deseja pesquisar:");
			System.out.print("> ");
				
			while(cli.getId() == null){
				try{
					comando = reader.readLine();
					cli.setId(Long.parseLong(comando));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			ped.setCliente(cli);
			
			TransferObject to = new TransferObject("pesquisaCliente", ped);
			ro = ComunicacaoServidor.enviaDados(to);
			
			System.out.println(ro.getMensagem());
			
			if(ro.getLista() != null && ro.getLista().size() > 0){
				
				List<Pedido> pedidos = (List<Pedido>) ro.getLista();
				
				for(Pedido p:pedidos){
					System.out.println("ID: " + p.getId());
					System.out.println("Data de emissão: " + sdf.format(p.getDataEmissao()));
					if(p.getDataProcessamento() != null){
						System.out.println("Data de processamento: " + sdf.format(p.getDataProcessamento()));
					}else{
						System.out.println("Data de processamento: Não processado" );
					}
					System.out.println("Total do Pedido: " + p.getTotalPedido());
					System.out.println("Quantidade de produtos: " + p.getQtdProdutos());
					System.out.println("***");
				}
			}
			
			
			ped.setId(Long.valueOf(comando));
			
		}else{
			System.out.println("");
			System.out.println("> Mensagem inv�lida! Insira um valor de 1 a 2");
			return null;
		}
		return ped;
	}
	
	public void alterarPedido(){

	}

	public Pedido excluirPedido(){
		
		Pedido ped = new Pedido();
		Produto prod = new Produto();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
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
	
	public void adicionaItems(Pedido pedido){
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		Boolean maisProdutos = true;
		Produto prod;
		String  comando = "";
		ReturnObject ro;
		
			while(maisProdutos){
				prod = new Produto();
						
				while(prod.getId() == null){
					try {
						System.out.println("> Digite o ID do produto: ");
					    comando = reader.readLine();
					    prod.setId(Long.parseLong(comando));
					} catch (IOException e) {
						e.printStackTrace();
					} catch (NumberFormatException e){
						System.out.println("ID deve ser númerico");
					}
				}			
				
				TransferObject to = new TransferObject("pesquisaID", prod);
				ro = ComunicacaoServidor.enviaDados(to);
				
				if(ro.getObj() != null){
					prod = (Produto) ro.getObj();
				}else{
					System.out.println("Nenhum produto encontrado com essa ID");
					continue;
				}
				
				ItemPedido item = new ItemPedido(pedido,prod);
							
	
				
				while(item.getQuantidade() == null){
					try {
						//Solicita ao usu�rio a quantidade do produto
						System.out.println("");
						System.out.println("> Digite a quantidade desejada do produto que voc� escolheu: ");
					    comando = reader.readLine();
					    item.setQuantidade(Integer.parseInt(comando));					    
					} catch (IOException e) {
						e.printStackTrace();
					} catch(NumberFormatException e){
						System.out.println("QUANTIDADE precisa ser numerico");
					}
				}
				
				TransferObject toSalvaItem = new TransferObject("cadastro", item);
				ro = ComunicacaoServidor.enviaDados(toSalvaItem);
				
				System.out.println(ro.getMensagem());
				
				System.out.println("Adicionar mais produtos? S/N");
				
				try{
					comando = reader.readLine();
					if(!comando.equalsIgnoreCase("s")){
						maisProdutos = false;
					}					
				}catch(IOException e){
					e.printStackTrace();			
				}

		}
	}
}
