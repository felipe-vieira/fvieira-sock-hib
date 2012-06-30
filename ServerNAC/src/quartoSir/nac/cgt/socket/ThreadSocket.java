package quartoSir.nac.cgt.socket;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import quartoSir.nac.cgt.bo.ClienteBO;
import quartoSir.nac.cgt.bo.ItemPedidoBO;
import quartoSir.nac.cgt.bo.PedidoBO;
import quartoSir.nac.cgt.bo.ProdutoBO;
import quartoSir.nac.domain.Cliente;
import quartoSir.nac.domain.ItemPedido;
import quartoSir.nac.domain.Pedido;
import quartoSir.nac.domain.Produto;
import quartoSir.nac.domain.util.ReturnObject;
import quartoSir.nac.domain.util.TransferObject;

public class ThreadSocket implements Runnable {
	
	private Socket clientSocket;
	
	public ThreadSocket(Socket clientSocket){
		this.clientSocket = clientSocket;
	}
	
	@Override
	public void run() {
		this.handleCommands();	
	}
	
	/**
	 * @author Felipe 
	 * Responsável por receber e tratar comandos do cliente
	 */
	private void handleCommands() {
		ObjectInputStream in;
		ObjectOutputStream out;
		Object obj = null;
				
		try {
			in = new ObjectInputStream(this.clientSocket.getInputStream());
			
			
			ReturnObject retorno = null;
			
			if(this.clientSocket != null && this.clientSocket.isBound()){
				
				try{

					while((obj = in.readObject()) == null){
						Thread.sleep(100);
					}
					
					if(obj instanceof TransferObject){
						retorno = this.trataObjetoRecebido((TransferObject) obj);
					}
					
					if(retorno != null){
						out = new ObjectOutputStream(this.clientSocket.getOutputStream());
						out.writeObject(retorno);
						out.flush();
					}
					
					in.close();
					
				}catch (EOFException ex) {
					System.out.println("Leitura encerrada");
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		} catch (InterruptedException e){
			e.printStackTrace();
		}
		
		
	}
	
	
	public ReturnObject trataObjetoRecebido(TransferObject to){
		System.out.println("Action: "+ to.getAction());
		String action = to.getAction();
		
		//Chama os métodos do BO do cliente
		if(to.getValue() instanceof Cliente){
			ClienteBO bo = new ClienteBO();
			Cliente cli = (Cliente) to.getValue();
			
			if(action.equals("cadastro")){
				return bo.salvaCliente(cli);
			}else if(action.equals("pesquisaCPF")){
				return bo.pesquisaClienteCPF(cli);
			}else if(action.equals("pesquisaID")){
				return bo.pesquisaClienteID(cli);
			}else if(action.equals("pesquisaNome")){
				return bo.pesquisaClienteNome(cli);
			}else if(action.equals("atualiza")){
				return bo.atualizaCliente(cli);
			}else if(action.equals("exclui")){
				return bo.excluiCliente(cli);
			}
			
		}
		
		//Chama os métodos do BO do produto
		if(to.getValue() instanceof Produto){
			ProdutoBO bo = new ProdutoBO();
			Produto prod = (Produto) to.getValue();
			
			if(action.equals("cadastro")){
				return bo.salvaProduto(prod);
			}else if(action.equals("pesquisaID")){
				return bo.pesquisaProdutoID(prod);
			}else if(action.equals("pesquisaDescricao")){
				return bo.pesquisaProdutoDescricao(prod);
			}else if(action.equals("atualiza")){
				return bo.atualizaProduto(prod);
			}else if(action.equals("exclui")){
				return bo.excluiProduto(prod);
			}
			
		}
		
		//Chama os métodos do BO de pedido
		if(to.getValue() instanceof Pedido){

			PedidoBO bo = new PedidoBO();
			Pedido pedido = (Pedido) to.getValue();

			if(action.equals("cadastro")){
				return bo.salvaPedido(pedido);
			}else if(action.equals("atualiza")){
				return bo.atualizaPedido(pedido);
			}else if(action.equals("exclui")){
				return bo.excluiPedido(pedido);
			}else if(action.equals("processa")){
				return bo.processaPedido(pedido);
			}else if(action.equals("calcula")){
				bo.atualizaTotalPedido(pedido);
				return null;
			}else if(action.equals("pesquisaID")){
				return bo.pegaPedidoID(pedido);
			}else if(action.equals("pesquisaCliente")){
				return bo.listaPedidosClientes(pedido);
			}
		}
		
		if(to.getValue() instanceof ItemPedido){
			
			ItemPedidoBO bo = new ItemPedidoBO();
			
			ItemPedido item = (ItemPedido) to.getValue();
			if(action.equals("cadastro")){
				return bo.salvaItemPedido(item);
			}else if(action.equals("atualiza")){
				return bo.salvaItemPedido(item);
			}else if(action.equals("exclui")){
				return bo.excluiItemPedido(item);
			}
			
		}
		
		return null;
		
	}
	
	public void closeSocket(){
		try{
			this.clientSocket.close();
		}catch(IOException ex){
			System.out.println("Erro ao terminar a conexão...");
			ex.printStackTrace();
		}
	}
	
}
