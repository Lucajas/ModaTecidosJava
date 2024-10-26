package Entidades;

public class Produto {
    private int id;
    private String nome;
    private String tipo;
    private String subTipo;
    private int lojaId;
    private int estoque;

    // Construtor completo
    public Produto(int id, String nome, String tipo, String subTipo, int lojaId, int estoque) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.subTipo = subTipo;
        this.lojaId = lojaId;
        this.estoque = estoque;
    }

    // Construtor que aceita apenas o ID
    public Produto(int id) {
        this.id = id;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSubTipo() {
        return subTipo;
    }

    public void setSubTipo(String subTipo) {
        this.subTipo = subTipo;
    }

    public int getLojaId() {
        return lojaId;
    }

    public void setLojaId(int lojaId) {
        this.lojaId = lojaId;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }
}
