package quartoSir.nac.domain.util;

import java.io.Serializable;

public class TransferObject implements Serializable {
	
	private static final long serialVersionUID = -4419320090420590011L;
	
	private String action;
	private Serializable value;
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Serializable getValue() {
		return value;
	}
	public void setValue(Serializable value) {
		this.value = value;
	}
	
}
