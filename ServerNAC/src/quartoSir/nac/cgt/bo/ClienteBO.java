package quartoSir.nac.cgt.bo;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import quartoSir.nac.cgd.GenericDAO;
import quartoSir.nac.domain.CPF;
import quartoSir.nac.domain.Cliente;
import quartoSir.nac.domain.Pedido;
import quartoSir.nac.domain.util.ReturnObject;

public class ClienteBO {

	private GenericDAO dao;
	
	
	public ClienteBO(){
		this.dao = new GenericDAO();
	}
	
	/**
	 * Realiza as validações e persiste os objetos no banco de dados.
	 * @param cli
	 * @return ReturnObject indica o status e a mensagem da operação
	 */
	public ReturnObject salvaCliente(Cliente cli) {
		
		ReturnObject retorno = new ReturnObject();
		
		Session session = dao.getSession(); 
		Transaction t = session.beginTransaction();
		
		try{
			//Verifica se existem campos vazios
			if(cli != null && cli.getNome() != null && !cli.getNome().equals("")){
				
				if(cli.getCpf() != null){
					
					//Verifica se já existe usuario com o CPF informado
					Query query = session.createQuery("FROM Cliente WHERE cpf.numero = :numero AND cpf.digito = :digito");
					query.setLong("numero", cli.getCpf().getNumero());
					query.setInteger("digito", cli.getCpf().getDigito());
					
					List<CPF> listaCPF = (List<CPF>) dao.queryList(query);
					
					if(listaCPF != null && listaCPF.size()>0){
						retorno.setSucesso(false);
						retorno.setMensagem("ERRO: O CPF já está em uso");
						t.rollback();
					}else{
						dao.save(cli);
						t.commit();
						
						retorno.setSucesso(true);
						retorno.setMensagem("Cadastrado com sucesso!");
					}
					
					
				}else{
					retorno.setSucesso(false);
					retorno.setMensagem("ERRO: O CPF é obrigatorio");
					t.rollback();
				}
				
			}else{
				retorno.setSucesso(false);
				retorno.setMensagem("ERRO: O nome do cliente não pode ser vazio");
				t.rollback();
			}
		}catch(Exception e){
			t.rollback();
			e.printStackTrace();
		}
		
		return retorno;		
	}
	
	/**
	 * Pesquisa o cliente por CPF
	 * @param cli
	 * @return ReturnList indica o retorno da pesquisa e o status da transação, além de mensagens de erro se existir.
	 */
	public ReturnObject pesquisaClienteCPF(Cliente cli){
		ReturnObject retorno = new ReturnObject();
		
		Session session = dao.getSession(); 
		Transaction t = session.beginTransaction(); 
		
		try{
			
			if(cli.getCpf() != null){
				
				Query query = session.createQuery("FROM Cliente WHERE cpf.numero = :numero AND cpf.digito = :digito");
				query.setLong("numero", cli.getCpf().getNumero());
				query.setInteger("digito", cli.getCpf().getDigito());
				
				Cliente resultado = (Cliente) dao.queryUnique(query);
								
				
				if(resultado != null){
					retorno.setObj(resultado);
				}else{
					retorno.setMensagem("Nenhum cliente encontrado com esse cpf");
				}
				
				t.commit();
				
			}else{
				retorno.setMensagem("ERRO: CPF não pode ser vazio para essa consulta");
				t.rollback();
			}
			
		}catch(Exception e){
			t.rollback();
			e.printStackTrace();
			
		}
		
		return retorno;		
	}
	
	
	/**
	 * Pesquisa o cliente por ID
	 * @param cli
	 * @return ReturnList indica o retorno da pesquisa e o status da transação, além de mensagens de erro se existir.
	 */
	public ReturnObject pesquisaClienteID(Cliente cli){
		ReturnObject retorno = new ReturnObject();
		
		Session session = dao.getSession(); 
		Transaction t = session.beginTransaction(); 
		
		try{
			
			if(cli.getId() != null){
				
				Cliente resultado = (Cliente) dao.getById(Cliente.class, cli.getId());
				
				
				if(resultado != null){
					retorno.setObj(resultado);
				}else{
					retorno.setMensagem("Nenhum cliente encontrado com esse ID");
				}
				
				t.commit();
				
			}else{
				retorno.setMensagem("ERRO: ID não pode ser vazio para essa consulta");
				t.rollback();
			}
			
		}catch(Exception e){
			t.rollback();
			e.printStackTrace();
			
		}
		return retorno;		
	}
	
	/**
	 * Pesquisa o cliente por Nome
	 * @param cli
	 * @return ReturnList indica o retorno da pesquisa e o status da transação, além de mensagens de erro se existir.
	 */
	public ReturnObject pesquisaClienteNome(Cliente cli){
		ReturnObject retorno = new ReturnObject();
		
		Session session = dao.getSession(); 
		Transaction t = session.beginTransaction(); 
		
		try{
			
			if(cli.getNome() != null && !cli.getNome().equals("")){
				
				Query query = session.createQuery("FROM Cliente WHERE nome like :nome ORDER BY nome");
				query.setString("nome", cli.getNome()+"%");
				
				List<Cliente> resultados = (List<Cliente>) dao.queryList(query);
				
				if(resultados != null && resultados.size() > 0){
					retorno.setLista(resultados);
				}else{
					retorno.setMensagem("Nenhum cliente encontrado com esse NOME");
				}
				
				t.commit();
				
			}else{
				retorno.setMensagem("ERRO: NOME não pode ser vazio para essa consulta");
				t.rollback();
			}
			
		}catch(Exception e){
			t.rollback();
			e.printStackTrace();
			
		}
		return retorno;		
	}

	
	/**
	 * Atualiza o cliente.
	 * @param cli
	 * @return ReturnObject indica o status e a mensagem da operação 
	 */
	public ReturnObject atualizaCliente(Cliente cli) {
		
		Session session = dao.getSession();
		Transaction t = session.beginTransaction();
		
		ReturnObject retorno = new ReturnObject();	
		retorno.setSucesso(false);
		
		try{
			
			PedidoBO pedidoBO = new PedidoBO();
			List<Pedido> pedidos = pedidoBO.listaPedidosCliente(cli);
			
			if(pedidos != null && pedidos.size() > 0){
				Cliente cliAntigo = (Cliente) dao.getById(Cliente.class, cli.getId());
				
				if(!cliAntigo.getCpf().equals(cli.getCpf())){
					retorno.setMensagem("ERRO: Não é possível alterar o CPF de um cliente que já possui pedidos.");
					t.rollback();
					return retorno;
				}		
			}
			
			dao.update(cli);						
			t.commit();
					
			retorno.setSucesso(true);
			retorno.setMensagem("Cliente alterado");
			
		}catch(Exception e){
			t.rollback();
		}
		 
		return retorno;
		
	}
	
	
	/**
	 * Deleta o cliente.
	 * @param cli
	 * @return ReturnObject indica o status e a mensagem da operação 
	 */
	public ReturnObject excluiCliente(Cliente cli) {
		
		Session session = dao.getSession();
		Transaction t = session.beginTransaction();
		
		ReturnObject retorno = new ReturnObject();	
		retorno.setSucesso(false);
		
		try{
			PedidoBO pedidoBO = new PedidoBO();
			List<Pedido> pedidos = pedidoBO.listaPedidosCliente(cli);
			
			if(pedidos != null && pedidos.size() > 0){
				retorno.setMensagem("ERRO: Não é excluir um cliente que já possui pedidos.");
				t.rollback();
			}else{
				dao.delete(cli);
				t.commit();
				retorno.setSucesso(true);
				retorno.setMensagem("Cliente excluido com sucesso!");
			}
			
		}catch(Exception e){
			t.rollback();
		}
		 
		return retorno;
		
	}
	
}
