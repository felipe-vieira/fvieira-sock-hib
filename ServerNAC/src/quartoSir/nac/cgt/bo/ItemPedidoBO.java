package quartoSir.nac.cgt.bo;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import quartoSir.nac.cgd.GenericDAO;
import quartoSir.nac.domain.Cliente;
import quartoSir.nac.domain.ItemPedido;
import quartoSir.nac.domain.ItemPedidoPK;
import quartoSir.nac.domain.Pedido;
import quartoSir.nac.domain.Produto;
import quartoSir.nac.domain.util.ReturnObject;

public class ItemPedidoBO {
	
	private GenericDAO dao;
	
	public ItemPedidoBO(){
		this.dao = new GenericDAO();
	}
	
	
	/**
	 * Realiza as verificações e salva um item do pedido
	 * @param ItemPedido
	 * @return ReturnObject indica o status da transação e mensagens de erro se existir.
	 */
	public ReturnObject salvaItemPedido(ItemPedido item){
		ReturnObject ro = new ReturnObject();
		ro.setSucesso(false);
		
		Session session = dao.getSession(); 
		Transaction t = session.beginTransaction();
		
		
		try{
			if(item.getPedido() == null){
				ro.setMensagem("ERRO: Pedido não pode ser vazio.");
				t.rollback();
				return ro;
			}else if(item.getPedido().getDataProcessamento() != null){
				ro.setMensagem("ERRO: Não é possível alterar ou incluir itens de pedido em pedido processado.");
				t.rollback();
				return ro;				
			}else if(item.getProduto() == null){
				ro.setMensagem("ERRO: Produto não pode ser vazio.");
				t.rollback();
				return ro;
			}else if(item.getQuantidade() == null || item.getQuantidade() < 1){
				ro.setMensagem("ERRO: Quantidade não pode ser vazio.");
				t.rollback();
				return ro;
			}
			
			BigDecimal valorTotal = this.calculaValorTotalItemPedido(item);
			
			item.setTotalDoItem(valorTotal);
			
			dao.saveOrUpdate(item);
			t.commit();
			ro.setSucesso(true);
			ro.setMensagem("Item de Pedido cadastrado com sucesso!");
			
		}catch(Exception e){
			t.rollback();
		}
		
		return ro;
	}
	
	
	/**
	 * Realiza as verificações e salva um item do pedido
	 * @param ItemPedido
	 * @return ReturnObject indica o status da transação e mensagens de erro se existir.
	 */
	public ReturnObject excluiItemPedido (ItemPedido item){
		ReturnObject ro = new ReturnObject();
		ro.setSucesso(false);
		
		Session session = dao.getSession(); 
		Transaction t = session.beginTransaction();
		
		
		try{
			if(item.getPedido().getDataProcessamento() != null){
				ro.setMensagem("ERRO: Não é possível Excluir itens de pedido em pedido processado.");
				t.rollback();
				return ro;				
			}
			
			dao.delete(item);
			t.commit();
			
			ro.setSucesso(true);
			ro.setMensagem("Item de Pedido excluido com sucesso!");
			
		}catch(Exception e){
			t.rollback();
		}
		
		return ro;
	}
	
	/**
	 * Lista todos os pedidos de um produto
	 * @param cli Cliente pesquisado
	 * @return Lista de itens de pedido
	 */
	public List<ItemPedido> listaItensPedidoProduto(Produto prod){
		
		Session session = dao.getSession();
		Transaction t = session.beginTransaction();
		
		try{
			
			if(prod != null && prod.getId() != null){
				Query query = session.createQuery("FROM ItemPedido where produto.id = :prodId");
				query.setLong("prodId", prod.getId());
				
				List<ItemPedido> retorno = dao.queryList(query);
				t.commit();
				return retorno;			
				
			}else{
				t.rollback();
				return null;
			}
			
		}catch(Exception e){
			t.rollback();
			return null;
		}	
		
	}
	
	/**
	 * Lista todos os items de um pedido
	 * @param Lista de itens de pedido
	 * @return Lista de itens de pedido
	 */
	public List<ItemPedido> listaItensPedidoPedido(Pedido pedido){
		Session session = dao.getSession();
		Transaction t = session.beginTransaction();
		
		try{
			
			if(pedido != null && pedido.getId() != null){
				Query query = session.createQuery("FROM ItemPedido where pedido.id = :pedidoId");
				query.setLong("pedidoId", pedido.getId());
				
				List<ItemPedido> retorno = dao.queryList(query);
				t.commit();
				return retorno;			
				
			}else{
				t.rollback();
				return null;
			}
			
		}catch(Exception e){
			t.rollback();
			return null;
		}	
	}
	
	
	/**
	 * Calcula o valor total de um item de pedido
	 * @param ItemPedido
	 * @return Quantidade
	 */
	public BigDecimal calculaValorTotalItemPedido(ItemPedido item){

		BigDecimal auxCem = new BigDecimal(100);
		BigDecimal total = new BigDecimal(0);
			
		BigDecimal valorUni = item.getPrecoUnitario();
		BigDecimal qtde = new BigDecimal(item.getQuantidade());
		BigDecimal valorDesconto = new BigDecimal(0);
		BigDecimal totalItem = new BigDecimal(0);
			
		if(item.getDesconto() != null){
			valorDesconto = item.getDesconto().divide(auxCem).multiply(valorUni);
		}
			
		totalItem = valorUni.subtract(valorDesconto).multiply(qtde);
						
		total.add(totalItem);
			
		
		return totalItem;
		
	}
	
	
	/**
	 * Processa os items de pedido
	 * @param pedido
	 */
	public void processaItensPedido(Pedido pedido){
		ProdutoBO produtoBO = new ProdutoBO();
		
		Session session = dao.getSession();
		Transaction t = session.beginTransaction();
		
		BigDecimal descontoPadrao = new BigDecimal(2);
		
		try{
			
			if(pedido != null && pedido.getId() != null){
				Query query = session.createQuery("FROM ItemPedido where pedido.id = :pedidoId");
				query.setLong("pedidoId", pedido.getId());
				
				List<ItemPedido> itens = dao.queryList(query);
				
				for(ItemPedido item:itens){
					
					Produto produto = item.getProduto();
					if(produto.getEstoque() - item.getQuantidade() < 0){
						item.setQuantidade(null);
					} else if(item.getQuantidade() > 10) {
						item.setDesconto(descontoPadrao);
						
						produto.setEstoque(produto.getEstoque() - item.getQuantidade());
						
					}
					
					BigDecimal valorTotal = this.calculaValorTotalItemPedido(item);
					item.setTotalDoItem(valorTotal);
					
					dao.update(item);
					produtoBO.atualizaProduto(produto);
				}			
				
				
				t.commit();
				
			}else{
				t.rollback();
			}
			
		}catch(Exception e){
			t.rollback();
		}	
		
		
	}
	
}
