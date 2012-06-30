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
	 * Salva ou atualiza um objeto no banco de dados
	 *  @param obj
	 */
	public void saveOrUpdate(Object obj){
		this.session.saveOrUpdate(obj);
	}
	
	/**
	 * Recebe uma query e retorna o resultado em lista
	 * @param query
	 * @return List
	 */
	public List queryList(Query query){
		return query.list();			
	}
	
	
	/**
	 * Recebe uma query e retorna um unico resultado
	 * @param query
	 * @return Objeto
	 */
	public Object queryUnique(Query query){
		return query.uniqueResult();			
	}
	
	/**
	 * Recebe uma id e retorna o objeto.
	 * @param classe
	 * @param id
	 * @return Objeto
	 */
	public Object getById(Class classe, Serializable id){
		return this.session.get(classe, id);
	}
	
	/**
	 * Atualiza um objeto
	 * @param obj
	 */
	public void update(Object obj){
		this.session.merge(obj);
	}

	/**
	 * Delete um objeto
	 * @param cli
	 */
	public void delete(Object obj) {
		this.session.delete(obj);
	}
		
	
	
}
