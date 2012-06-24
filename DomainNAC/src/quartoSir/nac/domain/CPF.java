package quartoSir.nac.domain;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CPF implements Serializable {


	private static final long serialVersionUID = 7977598121680845396L;

	private Long numero;

	private Integer digito;

	/**
	 * Define um objeto da classe CPF a partir de uma String. Aceita os
	 * formatos: 99999999999 e 999.999.999-99
	 * 
	 * @param cpf
	 */
	public CPF(String cpfString) throws Exception{
		
		cpfString = cpfString.replace(".", "").replace("-","");
		if(cpfString.length() != 11){
			throw new Exception("Formato irregular de CPF");
		}else{
			this.numero = Long.parseLong(cpfString.substring(0, 9));
			this.digito = Integer.parseInt(cpfString.substring(9,11));
		}
		
		
	}
	
	public CPF() {};
	

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Integer getDigito() {
		return digito;
	}

	public void setDigito(Integer digito) {
		this.digito = digito;
	}
	
	/**
	 * Retorna o CPF no formato correto.
	 */
	@Override
	public String toString() {

		String cpfFormatado = "";
		
		//Cria uma expressão regular para formatar o cpf
        Pattern pattern = Pattern.compile("(\\d{3})(\\d{3})(\\d{3})(\\d{2})");  
        	
		NumberFormat nfNumero = new DecimalFormat("000000000");		
		NumberFormat nfDigito = new DecimalFormat("00");
		
		cpfFormatado +=  nfNumero.format(this.numero);
		cpfFormatado +=  nfDigito.format(this.digito);
		
		//Faz as substituições
		Matcher matcher = pattern.matcher(cpfFormatado);  
		cpfFormatado = matcher.replaceAll("$1.$2.$3-$4");
			
		return cpfFormatado;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((digito == null) ? 0 : digito.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CPF other = (CPF) obj;
		if (digito == null) {
			if (other.digito != null)
				return false;
		} else if (!digito.equals(other.digito))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}
	
	
	

}
