package quartoSir.nac.cgd;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import quartoSir.nac.util.cgd.DBUtil;

public class GenericDAO {
	
	private Session session;

	/**
	 * Pega a sessão atual doo hibernate, se for vazia, pega uma nova antes de retornar
	 * @return Session
	 */
	public Session getSession() {
		if(this.session == null){
			this.session = DBUtil.getCurrentSession();
		}
		return session;
	}
	
	/**
	 * Salva um objeto no banco de dados e retorna seu ID
	 * @param obj
	 * @return Serializable
	 */
	public Serializable save(Object obj){
		return this.session.save(obj);
	}
	
	/**
	 * Recebe uma query e retorna o resultado em lista
	 * @param query
	 * @return List
	 */
	public List queryList(Query query){
		return query.list();			
	}
	
		
	
	
}
