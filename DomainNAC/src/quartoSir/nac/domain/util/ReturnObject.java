package quartoSir.nac.domain.util;

import java.io.Serializable;
import java.util.List;

public class ReturnObject implements Serializable {
	
	private static final long serialVersionUID = -8288909572416731183L;
	private Boolean sucesso;
	private String mensagem;
	private Serializable obj;
	private List lista;
	
	public Boolean getSucesso() {
		return sucesso;
	}
	public void setSucesso(Boolean sucesso) {
		this.sucesso = sucesso;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public Serializable getObj() {
		return obj;
	}
	public void setObj(Serializable obj) {
		this.obj = obj;
	}
	public List getLista() {
		return lista;
	}
	public void setLista(List lista) {
		this.lista = lista;
	}
	
	
	
}
