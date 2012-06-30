package quartoSir.nac.cih;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import quartoSir.nac.domain.CPF;
import quartoSir.nac.domain.Cliente;
import quartoSir.nac.domain.util.ReturnObject;
import quartoSir.nac.domain.util.TransferObject;
import quartoSir.nac.util.ComunicacaoServidor;

public class CadCliente {
	
	private BufferedReader reader = null;
	
	public CadCliente(){
		this.reader = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public void executar(){
			
			String comando = "";
			//While que faz o formulário aparecer cada vez que uma execução terminar
			
			while(!comando.equals("5")){
				
				System.out.println("");
			
				System.out.println("> Selecione uma opção: ");
				System.out.println("> 1 - Cadastrar novo cliente");
				System.out.println("> 2 - Listar cliente(s)");
				System.out.println("> 3 - Alterar cliente");
				System.out.println("> 4 - Excluir cliente");
				System.out.println("> 5 - Voltar");
		
				System.out.print("> ");
				
				try {
				    comando = reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				//Chamada de cada um dos métodos
				
				if(comando.equals("1")){
					cadastrarCliente();
				}else if(comando.equals("2")){
					listarCliente();
				}else if(comando.equals("3")){
					alterarCliente();
				}else if(comando.equals("4")){
					excluirCliente();
				}else if(!comando.equals("5")){
					System.out.println("");
					System.out.println("> Opção inválida! Insira um valor de 1 a 5");
				}
			}
		}
					
				
			
			
		public void cadastrarCliente(){

			Boolean sucesso = false;
			
			while(!sucesso){
				
				Cliente cli = new Cliente();
				String comando = "";
				CPF cpf = null;
				
				
				
				//Solicita ao usu�rio o nome do cliente
				System.out.println("");
				System.out.println("> Digite o nome do cliente:");
				
				try{
					comando = reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				cli.setNome(comando);
				
				//Solicita ao usu�rio o CPF do cliente
				
				System.out.println("");
				System.out.println("> Digite o CPF do cliente:");
							
				while(cpf == null){
					try{
						comando = reader.readLine();
						cpf = new CPF(comando);
					}catch (Exception e) {
						System.out.println("> CPF inválido!, digite novamente: ");
					}
				}
				
				cli.setCpf(cpf);
				
				//Envia ao servidor
				TransferObject to = new TransferObject("cadastro",cli);				
				ReturnObject ro = ComunicacaoServidor.enviaDados(to);
				
				if(ro!=null){
					sucesso = ro.getSucesso();
					System.out.println(ro.getMensagem());
				}
				
			}
		}
		
		public void listarCliente(){
			String comando = "";
			
			while(!comando.equals("4")){
				Cliente cli = new Cliente();
				CPF cpf = null;
				
				//Solicita ao usu�rio o par�metro para consulta de cliente
				
				System.out.println("");
				System.out.println("> Selecione o tipo de pesquisa:");
				System.out.println("> 1) Por ID");
				System.out.println("> 2) Por nome do cliente");
				System.out.println("> 3) Por CPF do cliente");
				System.out.println("> 4) Voltar");
				
				System.out.print("> ");
						
				
				try{
					comando = reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				//Solicita ao usu�rio o ID do cliente
				
				if(comando.equals("1")){
					System.out.println("");
					System.out.println("> Digite o ID do cliente que deseja pesquisar:");
					System.out.print("> ");
					
					
					try{
						comando = reader.readLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					try{
						cli.setId(Long.parseLong(comando));
					}catch (NumberFormatException e) {
						System.out.println("ID só pode ser númerico");
					}
					TransferObject to = new TransferObject("pesquisaID",cli);
					this.retornoPesquisaCliente(to);
					cli.setId(Long.valueOf(comando));
					
					
				}else if(comando.equals("2")){
					
					//Solicita ao usu�rio o nome do cliente
					
					System.out.println("");
					System.out.println("> Digite o nome, ou in�cio do nome, do cliente que deseja pesquisar:");
					System.out.print("> ");
					

					try{
						comando = reader.readLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					cli.setNome(comando);
					TransferObject to = new TransferObject("pesquisaNome",cli);
					this.retornoPesquisaCliente(to);
					
				}else if(comando.equals("3")){
					
					//Solicita ao usu�rio o CPF do cliente					
					while(cpf == null){
						try{
							System.out.println("> Digite o CPF do cliente que deseja pesquisar:");
							comando = reader.readLine();
							cpf = new CPF(comando);
							cli.setCpf(cpf);
							TransferObject to = new TransferObject("pesquisaCPF",cli);
							this.retornoPesquisaCliente(to);
							
						}catch (Exception e) {
							cpf = null;
							System.out.println("");
							System.out.println("> CPF inválido");
						}
					}
					

				}
				
			}
			
		}
		
		public void retornoPesquisaCliente(TransferObject to){
			ReturnObject ro = ComunicacaoServidor.enviaDados(to);
			
			if(ro!=null){
				Cliente resultado = (Cliente) ro.getObj();
				List<Cliente> listaClientes = (List<Cliente>) ro.getLista();
				
				if(resultado != null){
					System.out.println("> Resultado da pesquisa");
					System.out.println("> ID: "+resultado.getId());
					System.out.println("> NOME: "+resultado.getNome());
					System.out.println("> CPF: " +resultado.getCpf());
				}else if(listaClientes != null && listaClientes.size()>0){
					System.out.println("> Resultado da pesquisa");
					System.out.println("NOME\t\t\tCPF\t\t\tID");
					
					for(Cliente cli : listaClientes){
						System.out.println(cli.getNome()+"\t\t\t"+cli.getCpf()+"\t\t\t"+cli.getId());
					}					
				}else{
					System.out.println(ro.getMensagem());
				}
								
				
			}
		}
		
		
		public void alterarCliente(){
	
			Cliente cli = new Cliente();
			String comando = "";
			CPF cpf = null;
			Boolean sucesso = false;
			ReturnObject ro;
			
			while(!sucesso){
			
				//Solicita ao usu�rio o ID do cliente para altera��o
				
				System.out.println("");
				System.out.println("> Digite o ID do cliente que deseja alterar:");
				System.out.print("> ");
				
				try{
					comando = reader.readLine();
					cli.setId(Long.parseLong(comando));				
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				TransferObject cliBusca = new TransferObject("pesquisaID",cli);
				ro = ComunicacaoServidor.enviaDados(cliBusca);
				
				if(ro.getObj() != null){
					cli = (Cliente) ro.getObj();
				}else{
					System.out.println("Não existe nenhum cliente com esse ID");
					break;
				}		
				
				//Solicita ao usu�rio qual coluna deseja alterar
				
				System.out.println("");
				System.out.println("> Selecione qual informa��o deste cliente voc� deseja alterar:");
				System.out.println("> 1) Nome");
				System.out.println("> 2) CPF");
				System.out.print("> ");
				
				try{
					comando = reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				if(comando.equals("1")){
					
					//Solicita ao usu�rio o novo nome do cliente
					
					System.out.println("");
					System.out.println("> Digite o novo nome do cliente: ");
					
					try{
						cli.setNome(reader.readLine());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else if(comando.equals("2")){
					
					//Solicita ao usu�rio o novo CPF do cliente
					
					System.out.println("");
					System.out.println("> Digite o novo CPF do cliente: ");
					
					//Tratamento para CPF inválido
					
					while(cpf == null){
						try{
							comando = reader.readLine();
							cpf = new CPF(comando);
							cli.setCpf(cpf);
						}catch (Exception e) {
							System.out.println("");
							System.out.println("> CPF inválido!, digite novamente: ");
						}
					}
					
					
					
				//Tratamento para opção incorreta
					
				}else{
					System.out.println("");
					System.out.println("> Mensagem inválida! Insira um valor de 1 a 2");
	
				}
				
				TransferObject to = new TransferObject("atualiza",cli);	
				ro = ComunicacaoServidor.enviaDados(to);
				
				if(ro!=null){
					sucesso = ro.getSucesso();
					System.out.println(ro.getMensagem());
				}
				
				System.out.println("");
				
			}
		}
		
		
		public void excluirCliente(){
			
			
  			Boolean sucesso = false;
			
			while(!sucesso){
	  			Cliente cli = new Cliente();
				String comando = "";
				
				//Solicita ao usu�rio o ID do cliente para exclus�o
				
				System.out.println("");
				System.out.println("> Digite o ID do cliente que deseja excluir:");
				System.out.print("> ");
			
				try{
					comando = reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				cli.setId(Long.valueOf(comando));
				
				TransferObject to = new TransferObject("exclui",cli);
				ReturnObject ro = ComunicacaoServidor.enviaDados(to);
				
				if(ro!=null){
					sucesso = ro.getSucesso();
					System.out.println(ro.getMensagem());
				}
								
		}
	}
}
	

