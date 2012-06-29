package quartoSir.nac.cih;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;

import quartoSir.nac.domain.Cliente;
import quartoSir.nac.domain.Produto;
import quartoSir.nac.domain.util.ReturnObject;
import quartoSir.nac.domain.util.TransferObject;
import quartoSir.nac.util.ComunicacaoServidor;

public class CadProduto {

	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	public void executar() {

		String comando = "";

		// While que faz o formul�rio aparecer toda vez que uma execu��o
		// terminar

		while (!comando.equals("5")) {

			System.out.println("");
			System.out.println("> Selecione uma op��o: ");
			System.out.println("> 1 - Cadastrar novo produto");
			System.out.println("> 2 - Listar produto(s)");
			System.out.println("> 4 - Excluir produto");
			System.out.println("> 5 - Voltar");

			System.out.print("> ");

			try {
				comando = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Chamada de cada um dos m�todos

			if (comando.equals("1")) {
				cadastrarProduto();
			} else if (comando.equals("2")) {
				listarProduto();
			} else if (comando.equals("3")) {
				alterarProduto();
			} else if (comando.equals("4")) {
				excluirProduto();

				// Tratamento para op��o incorreta

			} else if (!comando.equals("5")) {
				System.out.println("");
				System.out.println("> Mensagem inv�lida! Insira um valor de 1 a 5");
			}
		}
	}

	public void cadastrarProduto() {
		Boolean sucesso = false;
		
		while(!sucesso){
		
			Produto prod = new Produto();
			String comando = "";
	
			// Solicita ao usu�rio a descri��o do produto
	
			System.out.println("");
			System.out.println("> Digite a descri��o do produto:");
			System.out.print("> ");
	
			try {
				comando = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
	
			prod.setDescricao(comando);
	
			// Solicita ao usu�rio o pre�o do produto
	
			System.out.println("");
			System.out.println("> Digite o pre�o do produto:");
			System.out.print("> ");
	
			try {
				comando = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
	
			try {
				prod.setPreco(BigDecimal.valueOf(Double.parseDouble(comando)));
			} catch (NumberFormatException n) {
				System.out
						.println("Valor inv�lido. Por favor, digite um valor num�rico.");
			}
	
			// Solicita ao usu�rio o estoque inicial do produto
	
			System.out.println("");
			System.out.println("> Digite o estoque do produto:");
			System.out.print("> ");
	
			try {
				comando = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
	
			try{
				prod.setEstoque(Integer.parseInt(comando));
			}catch(NumberFormatException n){
				System.out
					.println("Valor invalido, Por favor, digite um valor númerico");
			}

			//Envia os dados do servidor
			TransferObject to = new TransferObject("cadastro",prod);
			ReturnObject ro = ComunicacaoServidor.enviaDados(to);
			
			sucesso = ro.getSucesso();
			System.out.println(ro.getMensagem());
		}

	}

	public Produto listarProduto() {
		Produto prod = new Produto();
		String comando = "";

		// Solicita ao usu�rio o par�metro que ser� utilizado na busca

		System.out.println("");
		System.out.println("> Selecione o tipo de pesquisa:");
		System.out.println("> 1) Por ID");
		System.out.println("> 2) Por descri��o do produto");

		System.out.print("> ");


		try {
			comando = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Solicita ao usu�rio o ID do produto e seta em um objeto Produto

		if (comando.equals("1")) {
			System.out.println("");
			System.out
					.println("> Digite o ID do produto que deseja pesquisar:");
			System.out.print("> ");


			try {
				comando = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try{
				prod.setId(Long.valueOf(comando));
				TransferObject to = new TransferObject("pesquisaID", prod);
				this.retornoPesquisaProduto(to);
			}catch(NumberFormatException e){
				System.out.println("ID deve ser um número");
			}
			
			// Solicita ao usu�rio a descri��o do produto e seta em um objeto
			// Produto

		} else if (comando.equals("2")) {
			System.out.println("");
			System.out
					.println("> Digite a descri��o, ou in�cio da descri��o, do produto que deseja pesquisar:");
			System.out.print("> ");


			try {
				comando = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

			prod.setDescricao(comando);

			TransferObject to = new TransferObject("pesquisaDescricao", prod);
			this.retornoPesquisaProduto(to);
		} else {
			System.out.println("");
			System.out.println("> Mensagem inv�lida! Insira um valor de 1 a 2");
		}

		return prod;
	}

	
	public void retornoPesquisaProduto(TransferObject to) {
		ReturnObject ro = ComunicacaoServidor.enviaDados(to);
		Produto resultado = (Produto) ro.getObj();
		List<Produto> listaProdutos = (List<Produto>) ro.getLista();
		
		if(resultado != null){
			System.out.println("> Resultado da pesquisa");
			System.out.println("> ID: "+resultado.getId());
			System.out.println("> Descricao: "+resultado.getDescricao());
			System.out.println("> Estoque: " +resultado.getEstoque());
			System.out.println("> Preço: " +resultado.getPreco());
		}else if(listaProdutos != null && listaProdutos.size()>0){
			System.out.println("> Resultado da pesquisa");
			System.out.println("DESCRICAO\t\t\tESTOQUE\t\t\tPREÇO\t\t\tID");
			
			for(Produto cli : listaProdutos){
				System.out.println(cli.getDescricao()+"\t\t\t"+cli.getEstoque()+"\t\t\t"+cli.getPreco()+"\t\t\t"+cli.getId());
			}					
			
		}else{
			System.out.println(ro.getMensagem());
		}
		
		
		
	}
	
	public void alterarProduto() {

		Produto prod = new Produto();
		String comando = "";
		Boolean sucesso = false;
		
		while(!sucesso){

			// Solicita ao usu�rio o ID do produto para altera��o
	
			System.out.println("");
			System.out.println("> Digite o ID do produto que deseja alterar:");
			System.out.print("> ");
	
	
			try {
				comando = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
	
			// Seleciona qual coluna ser� atualizada
	
			System.out.println("");
			System.out.println("> Selecione qual informa��o deste produto voc� deseja alterar:");
			System.out.println("> 1) Descri��o");
			System.out.println("> 2) Pre�o");
			System.out.println("> 3) Estoque");
			System.out.print("> ");
	
	
			try {
				comando = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
	
			// Solicita ao usu�rio a nova descri��o do produto
	
			if (comando.equals("1")) {
				System.out.println("");
				System.out.println("> Digite a nova descri��o do produto: ");
				System.out.print("> ");
	
	
				try {
					prod.setDescricao(reader.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
	
				// Solicita ao usu�rio o novo pre�o do produto
	
			} else if (comando.equals("2")) {
				System.out.println("");
				System.out.println("> Digite o novo pre�o do produto: ");
				System.out.print("> ");
	
	
				// Tratamento para pre�o inv�lido
	
				try {
					if (Double.parseDouble(reader.readLine()) <= 0) {
						System.out.println("");
						System.out
								.println("> Pre�o n�o pode ser menor ou igual a Zero.");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
	
				// Valida o tipo de dados do pre�o
	
				try {
					prod.setPreco(BigDecimal.valueOf(Double.parseDouble(reader
							.readLine())));
				} catch (IOException e) {
					e.printStackTrace();
				}
	
				// Solicita ao usu�rio o novo estoque do produto
	
			} else if (comando.equals("3")) {
				System.out.println("");
				System.out.println("> Digite o novo estoque do produto: ");
				System.out.print("> ");
	
	
				try {
					prod.setEstoque(Integer.parseInt(reader.readLine()));
				} catch (IOException e) {
					e.printStackTrace();
				}
	
				// Tratamento para op��o incorreta
	
			} else {
				System.out.println("");
				System.out.println("> Mensagem inv�lida! Insira um valor de 1 a 3");
			}
	
			TransferObject to = new TransferObject("atualiza",prod);	
			ReturnObject ro = ComunicacaoServidor.enviaDados(to);
			
			if(ro!=null){
				sucesso = ro.getSucesso();
				System.out.println(ro.getMensagem());
			}
	
			System.out.println("");
			System.out.println("> Produto " + prod.getId() + " alterado com sucesso!");
	
		}
	}

	public void excluirProduto() {

		Boolean sucesso = false;
		Produto prod = new Produto();
		String comando = "";
		
		while(!sucesso){

			// Solicita ao usu�rio o ID do produto para exclus�o
	
			System.out.println("");
			System.out.println("> Digite o ID do produto que deseja excluir:");
			System.out.print("> ");
	
	
			try {
				comando = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
	
			prod.setId(Long.valueOf(comando));
	
			TransferObject to = new TransferObject("exclui",prod);				
			ReturnObject ro = ComunicacaoServidor.enviaDados(to);
			
			if(ro!=null){
				sucesso = ro.getSucesso();
				System.out.println(ro.getMensagem());
			}
			
			System.out.println("");
			System.out.println("> Produto " +prod.getId()+" exclu�do com sucesso!");
		}
	}
}
