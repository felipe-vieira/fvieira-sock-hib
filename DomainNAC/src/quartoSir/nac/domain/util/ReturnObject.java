package quartoSir.nac.domain.util;

import java.io.Serializable;

public class ReturnObject implements Serializable {
	
	private static final long serialVersionUID = -8288909572416731183L;
	private Boolean sucesso;
	private String mensagem;
	
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
	
	
	
}
