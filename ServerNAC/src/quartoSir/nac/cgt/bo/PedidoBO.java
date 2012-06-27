package quartoSir.nac.cgt.bo;

import org.hibernate.Session;
import org.hibernate.Transaction;

import quartoSir.nac.cgd.GenericDAO;
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

}
