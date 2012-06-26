package quartoSir.nac.cih;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import quartoSir.nac.domain.Produto;

public class CadProduto {
	
	public void executar(){
		
		String comando = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		//While que faz o formul�rio aparecer toda vez que uma execu��o terminar
		
		while(!comando.equals("5")){
			
			System.out.println("");
			System.out.println("> Selecione uma op��o: ");
			System.out.println("> 1 - Cadastrar novo produto");
			System.out.println("> 2 - Listar produto(s)");
			System.out.println("> 3 - Alterar produto");
			System.out.println("> 4 - Excluir produto");
			System.out.println("> 5 - Voltar");
	
			System.out.print("> ");
			
			try {
			    comando = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//Chamada de cada um dos m�todos
			
			if(comando.equals("1")){
				cadastrarProduto();
			}else if(comando.equals("2")){
				listarProduto();
			}else if(comando.equals("3")){
				alterarProduto();
			}else if(comando.equals("4")){
				excluirProduto();
			
			//Tratamento para op��o incorreta
			
			}else if(!comando.equals("5")){
				System.out.println("");
				System.out.println("> Mensagem inv�lida! Insira um valor de 1 a 5");
			}
		}
	}
				
			
		
		
	public Produto cadastrarProduto(){
		Produto prod = new Produto();
		BufferedReader reader;
		String comando = "";
		
		//Solicita ao usu�rio a descri��o do produto
		
		System.out.println("");
		System.out.println("> Digite a descri��o do produto:");
		System.out.print("> ");
		reader = new BufferedReader(new InputStreamReader(System.in));
		
		try{
			comando = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		prod.setDescricao(comando);
		
		//Solicita ao usu�rio o pre�o do produto
		
		System.out.println("");
		System.out.println("> Digite o pre�o do produto:");
		System.out.print("> ");
		reader = new BufferedReader(new InputStreamReader(System.in));
		
		try{
			comando = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		//Valida��o do pre�o e do formato do formato de dados
		
		if(Double.parseDouble(comando)<=0){
			System.out.println("");
			System.out.println("> Pre�o n�o pode ser menor ou igual a Zero.");
			return null;
		}
		
		try{
			prod.setPreco(BigDecimal.valueOf(Double.parseDouble(comando)));
		} catch (NumberFormatException n) {
			System.out.println("Valor inv�lido. Por favor, digite um valor num�rico.");
		} 
		
		//Solicita ao usu�rio o estoque inicial do produto
		
		System.out.println("");
		System.out.println("> Digite o estoque do produto:");
		System.out.print("> ");
		reader = new BufferedReader(new InputStreamReader(System.in));
		
		try{
			comando = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Valida��o do estoque inicial do produto
		
		if(Integer.parseInt(comando)<10){
			System.out.println("");
			System.out.println("Estoque inicial n�o pode ser menor que 10!");
			return null;
		}
		
		prod.setEstoque(Integer.parseInt(comando));
		
		//PARTE DO FELIPE
		System.out.println("");
		System.out.println("> Produto " +prod.getDescricao()+" cadastrado com sucesso!");
		
		return prod;
		
		
		
	}
	
	public Produto listarProduto(){
		Produto prod = new Produto();
		BufferedReader reader;
		String comando = "";
		
		//Solicita ao usu�rio o par�metro que ser� utilizado na busca
		
		System.out.println("");
		System.out.println("> Selecione o tipo de pesquisa:");
		System.out.println("> 1) Por ID");
		System.out.println("> 2) Por descri��o do produto");
		
		System.out.print("> ");
				
		reader = new BufferedReader(new InputStreamReader(System.in));
		
		try{
			comando = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Solicita ao usu�rio o ID do produto e seta em um objeto Produto
		
		if(comando.equals("1")){
			System.out.println("");
			System.out.println("> Digite o ID do produto que deseja pesquisar:");
			System.out.print("> ");
			
			reader = new BufferedReader(new InputStreamReader(System.in));
			
			try{
				comando = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			prod.setId(Long.valueOf(comando));
			
		//Solicita ao usu�rio a descri��o do produto e seta em um objeto Produto
			
		}else if(comando.equals("2")){
			System.out.println("");
			System.out.println("> Digite a descri��o, ou in�cio da descri��o, do produto que deseja pesquisar:");
			System.out.print("> ");
			
			reader = new BufferedReader(new InputStreamReader(System.in));
			
			try{
				comando = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			prod.setDescricao(comando);
			
		//Tratamento para op��o incorreta
			
		}else{
			System.out.println("");
			System.out.println("> Mensagem inv�lida! Insira um valor de 1 a 2");
		}
		
		return prod;
	}
	
	public Produto alterarProduto(){

		Produto prod = new Produto();
		BufferedReader reader;
		String comando = "";
		
		//Solicita ao usu�rio o ID do produto para altera��o
		
		System.out.println("");
		System.out.println("> Digite o ID do produto que deseja alterar:");
		System.out.print("> ");
		
		reader = new BufferedReader(new InputStreamReader(System.in));
		
		try{
			comando = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Seleciona qual coluna ser� atualizada
		
		System.out.println("");
		System.out.println("> Selecione qual informa��o deste produto voc� deseja alterar:");
		System.out.println("> 1) Descri��o");
		System.out.println("> 2) Pre�o");
		System.out.println("> 3) Estoque");
		System.out.print("> ");
		
		reader = new BufferedReader(new InputStreamReader(System.in));
		
		try{
			comando = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Solicita ao usu�rio a nova descri��o do produto
		
		if(comando.equals("1")){
			System.out.println("");
			System.out.println("> Digite a nova descri��o do produto: ");
			System.out.print("> ");
			
			reader = new BufferedReader(new InputStreamReader(System.in));
			
			try{
				prod.setDescricao(reader.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//Solicita ao usu�rio o novo pre�o do produto
			
		}else if(comando.equals("2")){
			System.out.println("");
			System.out.println("> Digite o novo pre�o do produto: ");
			System.out.print("> ");
			
			reader = new BufferedReader(new InputStreamReader(System.in));
			
			//Tratamento para pre�o inv�lido
			
			try{
				if(Double.parseDouble(reader.readLine())<=0){
					System.out.println("");
					System.out.println("> Pre�o n�o pode ser menor ou igual a Zero.");
					return null;
				}
			}catch (IOException e) {
				e.printStackTrace();
			} 
			
			//Valida o tipo de dados do pre�o
			
			try{
				prod.setPreco(BigDecimal.valueOf(Double.parseDouble(reader.readLine())));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		//Solicita ao usu�rio o novo estoque do produto
			
		}else if(comando.equals("3")){
			System.out.println("");
			System.out.println("> Digite o novo estoque do produto: ");
			System.out.print("> ");
			
			reader = new BufferedReader(new InputStreamReader(System.in));
			
			try{
				prod.setEstoque(Integer.parseInt(reader.readLine()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		//Tratamento para op��o incorreta
			
		}else{
			System.out.println("");
			System.out.println("> Mensagem inv�lida! Insira um valor de 1 a 3");
			return null;
		}
		
		//PARTE DO FELIPE
		
		System.out.println("");
		System.out.println("> Produto " +prod.getId()+" alterado com sucesso!");
		
		return prod;
		
	}
	
	public Produto excluirProduto(){
		
		Produto prod = new Produto();
		BufferedReader reader;
		String comando = "";
		
		//Solicita ao usu�rio o ID do produto para exclus�o
		
		System.out.println("");
		System.out.println("> Digite o ID do produto que deseja excluir:");
		System.out.print("> ");
		
		reader = new BufferedReader(new InputStreamReader(System.in));
		
		try{
			comando = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		prod.setId(Long.valueOf(comando));
		
		//PARTE DO FELIPE
		
		System.out.println("");
		System.out.println("> Produto " +prod.getId()+" exclu�do com sucesso!");
		
		return prod;
		
	}
}
