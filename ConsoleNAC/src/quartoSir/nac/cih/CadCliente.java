package quartoSir.nac.cih;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
					
				//Tratamento para opção incorreta
					
				}else if(!comando.equals("5")){
					System.out.println("");
					System.out.println("> Mensagem inválida! Insira um valor de 1 a 5");
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
				System.out.print("> ");
				
				try{
					comando = reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				cli.setNome(comando);
				
				//Solicita ao usu�rio o CPF do cliente
				
				System.out.println("");
				System.out.println("> Digite o CPF do cliente:");
				System.out.print("> ");
							
				while(cpf == null){
					try{
						comando = reader.readLine();
						cpf = new CPF(comando);
					}catch (Exception e) {
						System.out.println("");
						System.out.println("> CPF inválido!, digite novamente: ");
					}
				}
				
				cli.setCpf(cpf);
				
				//Envia ao servidor
				TransferObject to = new TransferObject();
				to.setAction("cadastro");
				to.setValue(cli);
				
				ReturnObject ro = ComunicacaoServidor.enviaDados(to);
				
				if(ro!=null){
					sucesso = ro.getSucesso();
					System.out.println(ro.getMensagem());
				}
				
			}
		}
		
		public Cliente listarCliente(){
			Cliente cli = new Cliente();
			String comando = "";
			CPF cpf;
			
			//Solicita ao usu�rio o par�metro para consulta de cliente
			
			System.out.println("");
			System.out.println("> Selecione o tipo de pesquisa:");
			System.out.println("> 1) Por ID");
			System.out.println("> 2) Por nome do cliente");
			System.out.println("> 3) Por CPF do cliente");
			
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
			}else if(comando.equals("3")){
				
				//Solicita ao usu�rio o CPF do cliente
				
				System.out.println("");
				System.out.println("> Digite o CPF do cliente que deseja pesquisar:");
				System.out.print("> ");
				
				
				try{
					comando = reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				//Tratamento para CPF inv�lido
				
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
			}
			
			return cli;
		}
		
		public Cliente alterarCliente(){
	
			Cliente cli = new Cliente();
			BufferedReader reader;
			String comando = "";
			CPF cpf;
			
			//Solicita ao usu�rio o ID do cliente para altera��o
			
			System.out.println("");
			System.out.println("> Digite o ID do cliente que deseja alterar:");
			System.out.print("> ");
			
			reader = new BufferedReader(new InputStreamReader(System.in));
			
			try{
				comando = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//Solicita ao usu�rio qual coluna deseja alterar
			
			System.out.println("");
			System.out.println("> Selecione qual informa��o deste cliente voc� deseja alterar:");
			System.out.println("> 1) Nome");
			System.out.println("> 2) CPF");
			System.out.print("> ");
			
			reader = new BufferedReader(new InputStreamReader(System.in));
			
			try{
				comando = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(comando.equals("1")){
				
				//Solicita ao usu�rio o novo nome do cliente
				
				System.out.println("");
				System.out.println("> Digite o novo nome do cliente: ");
				System.out.print("> ");
				
				reader = new BufferedReader(new InputStreamReader(System.in));
				
				try{
					cli.setNome(reader.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(comando.equals("2")){
				
				//Solicita ao usu�rio o novo CPF do cliente
				
				System.out.println("");
				System.out.println("> Digite o novo CPF do cliente: ");
				System.out.print("> ");
				
				reader = new BufferedReader(new InputStreamReader(System.in));
				
				//Tratamento para CPF inv�lido
				
				try{
					cpf = new CPF(reader.readLine());
				}catch (Exception e) {
					System.out.println("");
					System.out.println("> CPF inv�lido!");
					return null;
				}
				
				cli.setCpf(cpf);
				
			//Tratamento para op��o incorreta
				
			}else{
				System.out.println("");
				System.out.println("> Mensagem inv�lida! Insira um valor de 1 a 2");
				return null;
			}
			
			//PARTE DO FELIPE
			
			System.out.println("");
			System.out.println("> cliente " +cli.getId()+" alterado com sucesso!");
			
			return cli;
			
		}
		
		public Cliente excluirCliente(){
			
			Cliente cli = new Cliente();
			BufferedReader reader;
			String comando = "";
			
			//Solicita ao usu�rio o ID do cliente para exclus�o
			
			System.out.println("");
			System.out.println("> Digite o ID do cliente que deseja excluir:");
			System.out.print("> ");
			
			reader = new BufferedReader(new InputStreamReader(System.in));
			
			try{
				comando = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			cli.setId(Long.valueOf(comando));
			
			//PARTE DO FELIPE
			
			System.out.println("");
			System.out.println("> Cliente " +cli.getId()+" exclu�do com sucesso!");
			
			return cli;
			
		}
}
	

