package Entidades;

public class Estoque {
    private int id;
    private int lojaId;
    private int produtoId;
    private int quantidade;

    public Estoque(int id, int lojaId, int produtoId, int quantidade) {
        this.id = id;
        this.lojaId = lojaId;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLojaId() {
		return lojaId;
	}

	public void setLojaId(int lojaId) {
		this.lojaId = lojaId;
	}

	public int getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(int produtoId) {
		this.produtoId = produtoId;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

}

    