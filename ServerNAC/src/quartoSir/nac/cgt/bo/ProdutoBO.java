package quartoSir.nac.cgt.bo;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import quartoSir.nac.cgd.GenericDAO;
import quartoSir.nac.domain.ItemPedido;
import quartoSir.nac.domain.Produto;
import quartoSir.nac.domain.util.ReturnObject;

public class ProdutoBO {

	private GenericDAO dao;
	
	public ProdutoBO(){
		this.dao = new GenericDAO();
	}
	
	/**
	 * Realiza as verificacoes e salva o produto
	 * @param prod
	 * @return ReturnObject indica o status da transacao e mensagens de erro se existir.
	 */
	public ReturnObject salvaProduto(Produto prod){
		ReturnObject retorno = new ReturnObject();
		retorno.setSucesso(false);
		
		Session session = dao.getSession(); 
		Transaction t = session.beginTransaction();
		
		BigDecimal decimalZero = new BigDecimal(0);
		
		try{
			
			if(prod.getDescricao() == null || prod.getDescricao().equals("")){
				retorno.setMensagem("ERRO: PRODUTO nao pode ser vazio");
				t.rollback();
			}else if(prod.getEstoque() == null){
				retorno.setMensagem("ERRO: ESTOQUE nao pode ser vazio");
				t.rollback();
			}else if(prod.getEstoque() < 0){
				retorno.setMensagem("ERRO: ESTOQUE nao pode ser negativo");
				t.rollback();
			}else if(prod.getId() == null && prod.getEstoque() < 10){
				retorno.setMensagem("ERRO: ESTOQUE inicial nao pode ser inferior a 10");
				t.rollback();
			}else if(prod.getPreco() == null){
				retorno.setMensagem("ERRO: PRECO nao pode ser vazio");
				t.rollback();
			}else if(prod.getPreco().compareTo(decimalZero) == -1 || prod.getPreco().compareTo(decimalZero) == 0) {
				retorno.setMensagem("ERRO: PRECO deve ser maior que zero.");
				t.rollback();
			}else{
				dao.save(prod);
				t.commit();
				retorno.setSucesso(true);
				retorno.setMensagem("> Produto cadastrado com sucesso!");
			}
			
		}catch(Exception e){
			t.rollback();
		}
		
		return retorno;
	}
	
	/**
	 * Pesquisa o produto por ID
	 * @param prod
	 * @return ReturnList indica o retorno da pesquisa e o status da transacao, alem de mensagens de erro se existir.
	 */
	public ReturnObject pesquisaProdutoID(Produto prod){
		ReturnObject retorno = new ReturnObject();
		
		Session session = dao.getSession(); 
		Transaction t = session.beginTransaction(); 
		
		try{
			
			if(prod.getId() != null  && !prod.getId().equals("")){
				
				Produto resultado = (Produto) dao.getById(Produto.class, prod.getId());
				
				
				if(resultado != null){
					retorno.setObj(resultado);
				}else{
					retorno.setMensagem("> Nenhum produto encontrado com esse ID");
				}
				
				t.commit();
				
			}else{
				retorno.setMensagem("ERRO: ID nao pode ser vazio para essa consulta");
				t.rollback();
			}
			
		}catch(Exception e){
			t.rollback();
			e.printStackTrace();
			
		}
		return retorno;		
	}
	
	/**
	 * Pesquisa o produto pela descricao
	 * @param cli
	 * @return ReturnList indica o retorno da pesquisa e o status da transacao, alem de mensagens de erro se existir.
	 */
	public ReturnObject pesquisaProdutoDescricao(Produto prod){
		ReturnObject retorno = new ReturnObject();
		
		Session session = dao.getSession(); 
		Transaction t = session.beginTransaction(); 
		
		try{
			
			if(prod.getDescricao() != null && !prod.getDescricao().equals("")){
				
				Query query = session.createQuery("FROM Produto WHERE descricao like :descricao ORDER BY descricao");
				query.setString("descricao", prod.getDescricao()+"%");
				
				List<Produto> resultados = (List<Produto>) dao.queryList(query);
				
				if(resultados != null && resultados.size() > 0){
					retorno.setLista(resultados);
				}else{
					retorno.setMensagem("> Nenhum produto encontrado com essa DESCRICAO");
				}
				t.commit();
				
			}else{
				retorno.setMensagem("ERRO: Descricao nao pode ser vazia para essa consulta");
				t.rollback();
			}
			
		}catch(Exception e){
			t.rollback();
			e.printStackTrace();
			
		}
		return retorno;		
	}

	
	/**
	 * Atualiza o produto.
	 * @param prod
	 * @return ReturnObject indica o status e a mensagem da operacao 
	 */
	public ReturnObject atualizaProduto(Produto prod) {
		
		BigDecimal decimalZero = new BigDecimal(0);
		
		ReturnObject retorno = new ReturnObject();	
		retorno.setSucesso(false);
		
		Session session = dao.getSession();
		Transaction t = session.beginTransaction();
		
		try{
			if(prod.getDescricao() == null || prod.getDescricao().equals("")){
				retorno.setMensagem("ERRO: PRODUTO nao pode ser vazio");
				t.rollback();
			}else if(prod.getEstoque() == null){
				retorno.setMensagem("ERRO: ESTOQUE nao pode ser vazio");
				t.rollback();
			}else if(prod.getEstoque() < 0){
				retorno.setMensagem("ERRO: ESTOQUE nao pode ser negativo");
				t.rollback();
			}else if(prod.getId() == null && prod.getEstoque() < 10){
				retorno.setMensagem("ERRO: ESTOQUE inicial nao pode ser inferior a 10");
				t.rollback();
			}else if(prod.getPreco() == null){
				retorno.setMensagem("ERRO: PRECO nao pode ser vazio");
				t.rollback();
			}else if(prod.getPreco().compareTo(decimalZero) == -1 || prod.getPreco().compareTo(decimalZero) == 0) {
				retorno.setMensagem("ERRO: PRECO deve ser maior que zero.");
				t.rollback();
			}else{
				dao.update(prod);						
				t.commit();		
				retorno.setSucesso(true);
				retorno.setMensagem("> Produto alterado");
			}
		}catch(Exception e){
			t.rollback();
		}
		 
		return retorno;
		
	}
	
	/**
	 * Deleta o produto.
	 * @param prod
	 * @return ReturnObject indica o status e a mensagem da operacao
	 */
	public ReturnObject excluiProduto(Produto prod) {
		
		ReturnObject retorno = new ReturnObject();	
		retorno.setSucesso(false);
		
		ItemPedidoBO itemPedidoBO = new ItemPedidoBO();
		List<ItemPedido> itensPedido = itemPedidoBO.listaItensPedidoProduto(prod);
		
		Session session = dao.getSession();
		Transaction t = session.beginTransaction();
		
		try{
			
			if(prod.getId() != null && !prod.getId().equals("")){
				
				if(itensPedido != null && itensPedido.size() > 0){
					retorno.setMensagem("ERRO: nao excluir um produto que ja possui pedidos.");
					t.rollback();
				}else{
					dao.delete(prod);
					t.commit();
					retorno.setSucesso(true);
					retorno.setMensagem("> Produto excluido com sucesso!");
				}
				
			}else{
				retorno.setMensagem("ERRO: ID nao pode ser vazio para essa consulta");
				t.rollback();
			}	

		}catch(Exception e){
			t.rollback();
		}
		 
		return retorno;
		
	}
	
}
