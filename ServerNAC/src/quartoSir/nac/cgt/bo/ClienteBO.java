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
	 * Realiza as validacoes e persiste os objetos no banco de dados.
	 * @param cli
	 * @return ReturnObject indica o status e a mensagem da operacao
	 */
	public ReturnObject salvaCliente(Cliente cli) {
		
		ReturnObject retorno = new ReturnObject();
		
		Session session = dao.getSession(); 
		Transaction t = session.beginTransaction();
		
		try{
			//Verifica se existem campos vazios
			if(cli != null && cli.getNome() != null && !cli.getNome().equals("")){
				
				if(cli.getCpf() != null){
					
					//Verifica se ja existe usuario com o CPF informado
					Query query = session.createQuery("FROM Cliente WHERE cpf.numero = :numero AND cpf.digito = :digito");
					query.setLong("numero", cli.getCpf().getNumero());
					query.setInteger("digito", cli.getCpf().getDigito());
					
					List<CPF> listaCPF = (List<CPF>) dao.queryList(query);
					
					if(listaCPF != null && listaCPF.size()>0){
						retorno.setSucesso(false);
						retorno.setMensagem("ERRO: O CPF ja esta em uso");
						t.rollback();
					}else{
						dao.save(cli);
						t.commit();
						
						retorno.setSucesso(true);
						retorno.setMensagem("Cadastrado com sucesso!");
					}
					
					
				}else{
					retorno.setSucesso(false);
					retorno.setMensagem("ERRO: O CPF e obrigatorio");
					t.rollback();
				}
				
			}else{
				retorno.setSucesso(false);
				retorno.setMensagem("ERRO: O nome do cliente nao pode ser vazio");
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
	 * @return ReturnList indica o retorno da pesquisa e o status da transacao, alem de mensagens de erro se existir.
	 */
	public ReturnObject pesquisaClienteCPF(Cliente cli){
		ReturnObject retorno = new ReturnObject();
		
		Session session = dao.getSession(); 
		Transaction t = session.beginTransaction(); 
		
		try{
			
			if(cli.getCpf() != null && !cli.getCpf().equals("")){
				
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
				retorno.setMensagem("ERRO: CPF nao pode ser vazio para essa consulta");
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
	 * @return ReturnList indica o retorno da pesquisa e o status da transacao, alem de mensagens de erro se existir.
	 */
	public ReturnObject pesquisaClienteID(Cliente cli){
		ReturnObject retorno = new ReturnObject();
		
		Session session = dao.getSession(); 
		Transaction t = session.beginTransaction(); 
		
		try{
			
			if(cli.getId() != null && !cli.getId().equals("")){
				
				Cliente resultado = (Cliente) dao.getById(Cliente.class, cli.getId());
				
				
				if(resultado != null){
					retorno.setObj(resultado);
				}else{
					retorno.setMensagem("> Nenhum cliente encontrado com esse ID");
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
	 * Pesquisa o cliente por Nome
	 * @param cli
	 * @return ReturnList indica o retorno da pesquisa e o status da transacao, alem de mensagens de erro se existir.
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
					retorno.setMensagem("> Nenhum cliente encontrado com esse NOME");
				}
				
				t.commit();
				
			}else{
				retorno.setMensagem("ERRO: NOME nao pode ser vazio para essa consulta");
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
	 * @return ReturnObject indica o status e a mensagem da operacao 
	 */
	public ReturnObject atualizaCliente(Cliente cli) {
		
		ReturnObject retorno = new ReturnObject();	
		retorno.setSucesso(false);
		
		PedidoBO pedidoBO = new PedidoBO();
		List<Pedido> pedidos = pedidoBO.listaPedidosCliente(cli);
		
		Session session = dao.getSession();
		Transaction t = session.beginTransaction();
		
		try{
			Cliente cliAntigo = (Cliente) dao.getById(Cliente.class, cli.getId());
			
			if(pedidos != null && pedidos.size()> 0){
				if(!cli.getCpf().equals(cliAntigo.getCpf())){
					retorno.setMensagem("ERRO: Não é possível alterar cpf de clientes que já tenham pedido");
					return retorno;
				}
			}else if(cli.getCpf() != null){
				
				Query query = session.createQuery("FROM Cliente WHERE cpf.numero = :numero AND cpf.digito = :digito" +
												  "   AND id != :cliId");
				
				query.setLong("numero", cli.getCpf().getNumero());
				query.setInteger("digito", cli.getCpf().getDigito());
				query.setLong("cliId", cli.getId());
				
				List<Cliente> listaCPF = (List<Cliente>) dao.queryList(query);
				
				if(listaCPF != null && listaCPF.size()>0){
					retorno.setMensagem("ERRO: O CPF ja esta em uso");
					t.rollback();
					return retorno;
				}
	
				
			}else{
				retorno.setMensagem("ERRO: O CPF é obrigatorio");
				t.rollback();
				return retorno;
			}
			
			if(cli.getNome() == null || cli.getNome().equals("")){
				retorno.setMensagem("ERRO: O NOME é obrigatorio");
			}
					
			dao.update(cli);						
			t.commit();
					
			retorno.setSucesso(true);
			retorno.setMensagem("> Cliente alterado");
			
		}catch(Exception e){
			e.printStackTrace();
			t.rollback();
		}
		 
		return retorno;
		
	}
	
	
	/**
	 * Deleta o cliente.
	 * @param cli
	 * @return ReturnObject indica o status e a mensagem da operacao 
	 */
	public ReturnObject excluiCliente(Cliente cli) {
		
		ReturnObject retorno = new ReturnObject();	
		retorno.setSucesso(false);
		
		PedidoBO pedidoBO = new PedidoBO();
		List<Pedido> pedidos = pedidoBO.listaPedidosCliente(cli);
		
		Session session = dao.getSession();
		Transaction t = session.beginTransaction();
		
		try{

			if(cli.getId() != null && !cli.getId().equals("")){
				
				if(pedidos != null && pedidos.size() > 0){
					retorno.setMensagem("ERRO: Nao excluir um cliente que ja possui pedidos.");
					t.rollback();
				}else{
					dao.delete(cli);
					t.commit();
					retorno.setSucesso(true);
					retorno.setMensagem("> Cliente excluido com sucesso!");
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
