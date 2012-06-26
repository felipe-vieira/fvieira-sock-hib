package quartoSir.nac.cgt.bo;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;

import quartoSir.nac.cgd.GenericDAO;
import quartoSir.nac.domain.CPF;
import quartoSir.nac.domain.Cliente;
import quartoSir.nac.domain.util.ReturnObject;

public class ClienteBO {

	private GenericDAO dao;
	
	
	public ClienteBO(){
		this.dao = new GenericDAO();
	}
	
	public ReturnObject cadastraCliente(Cliente cli) {
		
		ReturnObject retorno = new ReturnObject();
		
		Transaction t = dao.getSession().beginTransaction();
		
		try{
			//Verifica se existem campos vazios
			if(cli != null && cli.getNome() != null && !cli.getNome().equals("")){
				
				if(cli.getCpf() != null){
					
					//Verifica se já existe usuario com o CPF informado
					Query query = dao.getSession().createQuery("FROM Cliente WHERE cpf.numero = :numero AND cpf.digito = :digito");
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
	
	
}
