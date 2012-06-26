package quartoSir.nac.util;

import quartoSir.nac.cih.Bootstrap;
import quartoSir.nac.domain.util.ReturnObject;
import quartoSir.nac.domain.util.TransferObject;

public class ComunicacaoServidor {
	
	/**
	 * Classe responsável por enviar dados ao servidor e tratar o retorno.
	 * @param TransferObject
	 * @return ReturnObject
	 */
	public static ReturnObject enviaDados(TransferObject to){
		ReturnObject ro = null;
		
		SocketUtil socketUtil = new SocketUtil();
		socketUtil.conectaSocket(Bootstrap.enderecoServidor);
		System.out.println("vai enviar");
		socketUtil.enviaObjeto(to);
		System.out.println("enviou");
		ro = socketUtil.recebeObjeto();
		
		return ro;
		
	}
	
	
}
