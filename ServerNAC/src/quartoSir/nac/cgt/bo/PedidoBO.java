package quartoSir.nac.cgt.bo;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import quartoSir.nac.cgd.GenericDAO;
import quartoSir.nac.domain.Cliente;
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

}
