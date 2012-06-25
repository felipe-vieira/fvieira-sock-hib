package quartoSir.nac.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class ItemPedido implements Serializable{
	
	private static final long serialVersionUID = 3019278809171121069L;

	
	private ItemPedidoPK id;
	
	private Pedido pedido;
	
	private Produto produto;
	
	private Integer quantidade;
	
	private BigDecimal precoUnitario;
	
	private BigDecimal totalDoItem;
	
	private BigDecimal desconto;

	public Integer getQuantidade() {
		return quantidade;
	}
	
	public ItemPedido(Pedido pedido, Produto produto) {
		this.id = new ItemPedidoPK(pedido, produto);
		this.pedido = pedido;
		this.produto = produto;
	}

	public ItemPedido() {}

	

	public ItemPedidoPK getId() {
		return id;
	}

	public void setId(ItemPedidoPK id) {
		this.id = id;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPrecoUnitario() {
		return precoUnitario;
	}

	public void setPrecoUnitario(BigDecimal precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

	public BigDecimal getTotalDoItem() {
		return totalDoItem;
	}

	public void setTotalDoItem(BigDecimal totalDoItem) {
		this.totalDoItem = totalDoItem;
	}

	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ItemPedido other = (ItemPedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
