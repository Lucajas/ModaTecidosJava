package Entidades;

import java.util.Date;

public class CompraVenda {
    private int idTransacao;
    private Produto produto;
    private int idLoja;
    private String tipo;
    private int quantidade;
    private Date data;
    private double valorUnitario;

    public CompraVenda(int idTransacao, Produto produto, int idLoja, String tipo, int quantidade, Date data, double valorUnitario) {
        this.idTransacao = idTransacao;
        this.produto = produto;
        this.idLoja = idLoja;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.data = data;
        this.valorUnitario = valorUnitario;
    }

	public int getIdTransacao() {
		return idTransacao;
	}

	public void setIdTransacao(int idTransacao) {
		this.idTransacao = idTransacao;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public int getIdLoja() {
		return idLoja;
	}

	public void setIdLoja(int idLoja) {
		this.idLoja = idLoja;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public double getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
}
