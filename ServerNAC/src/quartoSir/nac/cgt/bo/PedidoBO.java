package quartoSir.nac.cgt.bo;

import java.awt.ItemSelectable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import quartoSir.nac.cgd.GenericDAO;
import quartoSir.nac.domain.Cliente;
import quartoSir.nac.domain.ItemPedido;
import quartoSir.nac.domain.Pedido;
import quartoSir.nac.domain.util.ReturnObject;

public class PedidoBO {
	
	private GenericDAO dao;
	
	public PedidoBO(){
		this.dao = new GenericDAO();
	}
	
	public ReturnObject cadastraPedido(Pedido ped){
		ReturnObject retorno = new ReturnObject();
		
		Session session = dao.getSession(); 
		Transaction t = session.beginTransaction();
		
		return null;
		
	}
	
	/**
	 * Lista todos os pedidos de um cliente
	 * @param cli Cliente pesquisado
	 * @return Lista de pedidos
	 */
	public List<Pedido> listaPedidosCliente(Cliente cli){
		
		Session session = dao.getSession();
		Transaction t = session.beginTransaction();
		
		try{
			
			if(cli != null && cli.getId() != null){
				Query query = session.createQuery("FROM Pedido where cliente.id = :cliId");
				query.setLong("cliId", cli.getId());
				
				List<Pedido> retorno = dao.queryList(query);
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
	 * Cadastra um pedido no sistema
	 * @param Pedido
	 * @return ReturnObject indica o status e a mensagem da operação 
	 */
	
	public ReturnObject salvaPedido(Pedido pedido){
		ReturnObject ro = new ReturnObject();
		ro.setSucesso(false);
		
		Session session = dao.getSession();
		Transaction t = session.getTransaction();
		
		try{
			if(pedido.getCliente() == null){
				ro.setMensagem("ERRO: Não é possível criar um pedido sem cliente");
				t.rollback();
				return ro;
			}
			
			pedido.setDataEmissao(new Date());
			Long id = (Long) dao.save(pedido);
			
			pedido = (Pedido) dao.getById(Pedido.class, id);
			
			t.commit();
			
			ro.setSucesso(true);
			ro.setObj(pedido);
			ro.setMensagem("Pedido incluido com sucesso, inclua novos itens");
			
		}catch(Exception e){
			t.rollback();
			e.printStackTrace();
		}
		
		
		return ro;
		
		
	}
	
	
	/**
	 * Atualiza um pedido no sistema
	 * @param Pedido
	 * @return ReturnObject indica o status e a mensagem da operação 
	 */	
	public ReturnObject atualizaPedido(Pedido pedido){
		
		ReturnObject ro = new ReturnObject();
		ro.setSucesso(false);
		
		Session session = dao.getSession();
		Transaction t = session.getTransaction();
		
		try{
			
			Pedido pedidoAntigo = (Pedido) dao.getById(Pedido.class, pedido.getId());
			
			if(pedido.getDataProcessamento() != null){
				ro.setMensagem("ERRO: O pedido já foi processado, não é possivel efetuar a alteração");
				t.rollback();
				return ro;
			}else if(!pedido.getCliente().equals(pedidoAntigo.getCliente())){
				ro.setMensagem("ERRO: Não é possível alterar o cliente de um pedido salvo");
				t.rollback();
				return ro;
			}
			
			dao.update(pedido);
			t.commit();
			
			ro.setSucesso(true);
			ro.setMensagem("Pedido atualizado com sucesso");
			
			
		}catch(Exception e){
			t.rollback();
			e.printStackTrace();
		}
		
		return ro;
	}
	
	/**
	 * Atualiza um pedido no sistema
	 * @param Pedido
	 * @return ReturnObject indica o status e a mensagem da operação 
	 */	
	public ReturnObject excluiPedido(Pedido pedido){
		
		ReturnObject ro = new ReturnObject();
		ro.setSucesso(false);
		
		Session session = dao.getSession();
		Transaction t = session.getTransaction();
		
		try{
			if(pedido.getDataProcessamento() != null){
				ro.setMensagem("ERRO: O pedido já foi processado, não é possivel excluir");
				t.rollback();
				return ro;
			}
			
			dao.delete(pedido);
			t.commit();
			
			ro.setSucesso(true);
			ro.setMensagem("Pedido excluido com sucesso");
			
		}catch(Exception e){
			t.rollback();
			e.printStackTrace();
		}
		
		return ro;
	}
	
	/**
	 * Retorna todos os pedidos de um cliente
	 * @param Cliente
	 * @return ReturnList indica o retorno da pesquisa e o status da transação, além de mensagens de erro se existir.
	 */
	public ReturnObject listaPedidosClientes(Cliente cli){
		
		ReturnObject ro = new ReturnObject();
		ro.setSucesso(false);
		
		Session session = dao.getSession();
		Transaction t = session.getTransaction();
		
		try{
			
			if(cli == null || cli.getId() == null){
				ro.setMensagem("ERRO: O cliente é obrigatorio para essa consulta");
				t.rollback();
				return ro;
			}
			
			Query query = session.createQuery("FROM Pedido where Cliente.id = :cliId");
			List<Pedido> listaPedidos = (List<Pedido>) dao.queryList(query);
			t.commit();
			
			for(Pedido p:listaPedidos){
				Integer qtde = this.calculaQuantidadeProdutosPedido(p);
				p.setQtdProdutos(qtde);
			}
			
			ro.setSucesso(true);
			ro.setLista(listaPedidos);
			
		
		}catch(Exception e){
			t.rollback();
			e.printStackTrace();
		}
		
		return ro;
		
	}
	
	/**
	 * Retorna um os pedidos pelo ID
	 * @param Pedido
	 * @return ReturnList indica o retorno da pesquisa e o status da transação, além de mensagens de erro se existir.
	 */
	public ReturnObject pegaPedidosID(Pedido pedido){
		
		ReturnObject ro = new ReturnObject();
		ro.setSucesso(false);
		
		Session session = dao.getSession();
		Transaction t = session.getTransaction();
		
		try{
			
			if(pedido == null || pedido.getId() == null){
				ro.setMensagem("ERRO: O ID é obrigatorio para essa consulta");
				t.rollback();
				return ro;
			}
			
			pedido = (Pedido) dao.getById(Pedido.class, pedido.getId());
			t.commit();
			
			Integer qtde = this.calculaQuantidadeProdutosPedido(pedido);
			pedido.setQtdProdutos(qtde);
					
			ro.setSucesso(true);
			ro.setObj(pedido);
			
		
		}catch(Exception e){
			t.rollback();
			e.printStackTrace();
		}
		
		return ro;
		
	}
	
	/**
	 * Atualiza o total do pedido após a inclusão de itens ou processamento
	 * @param Pedido
	 */
	private void atualizaTotalPedido(Pedido pedido){
		ItemPedidoBO itemPedidoBO = new ItemPedidoBO();
		List<ItemPedido> itens = itemPedidoBO.listaItensPedidoPedido(pedido);
		
		Session session = dao.getSession();
		Transaction t = session.getTransaction();
		
		try{
			BigDecimal valorTotal = this.calculaValorTotalPedido(pedido);
			pedido.setTotalPedido(valorTotal);
			dao.update(pedido);
			t.commit();
			
		}catch(Exception e){
			t.rollback();
			e.printStackTrace();
		}
		
	}
	
	
	
	/**
	 * Calcula a quantidade de produtos em um pedido
	 * @param pedido
	 * @return Quantidade
	 */
	public Integer calculaQuantidadeProdutosPedido(Pedido pedido){
		ItemPedidoBO itemPedidoBO = new ItemPedidoBO();
		List<ItemPedido> itens = itemPedidoBO.listaItensPedidoPedido(pedido);
		
		Integer qtde = 0;
		
		for(ItemPedido item:itens){
			qtde += item.getQuantidade() == null ? 0:item.getQuantidade();
		}
		
		return qtde;
		
	}

	
	/**
	 * Calcula o valor total de um pedido
	 * @param pedido
	 * @return Quantidade
	 */
	public BigDecimal calculaValorTotalPedido(Pedido pedido){
		ItemPedidoBO itemPedidoBO = new ItemPedidoBO();
		List<ItemPedido> itens = itemPedidoBO.listaItensPedidoPedido(pedido);
		BigDecimal total = new BigDecimal(0);
	
		for(ItemPedido item:itens){
			total.add(item.getTotalDoItem());
		}
		
		return total;
		
	}
	
	/**
	 * Processa o pedido
	 * @param pedido
	 * @return ReturnObject indica o status e a mensagem da operação 
	 */
	public ReturnObject processaPedido(Pedido pedido){
		ItemPedidoBO itemBO = new ItemPedidoBO();
		itemBO.processaItensPedido(pedido);
		
		BigDecimal valorTotal = this.calculaValorTotalPedido(pedido);
		Integer qtde = this.calculaQuantidadeProdutosPedido(pedido);
		
		pedido.setDataProcessamento(new Date());
		pedido.setQtdProdutos(qtde);
		pedido.setTotalPedido(valorTotal);
		
		Session session = dao.getSession();
		Transaction t = session.getTransaction();
		
		ReturnObject ro = new ReturnObject();
		ro.setSucesso(false);
		
		try{

			this.calculaValorTotalPedido(pedido);
			dao.update(pedido);
			ro.setSucesso(true);
			ro.setMensagem("Pedido processado!");
		}catch(Exception ex){
			t.rollback();
			return ro;
		}
		
		return ro;
	}
	

}
