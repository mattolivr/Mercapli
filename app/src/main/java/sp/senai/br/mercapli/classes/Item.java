package sp.senai.br.mercapli.classes;

public class Item {

    private String nome;
    private Double valor;
    private String categoria;
    private String foto;
    private int quantidade;

    private int id;
    private int typeView;
    private Double valorFinal;

    public Item(int id, String nome, Double valor, int quantidade, int typeView){
        this.setId(id);
        this.setNome(nome);
        this.setValor(valor);
        this.setQuantidade(quantidade);
        this.setTypeView(typeView);
    }

    public Item(){
        this(0, "", 0.0, 0, 0);
    }

    public String getNome () {
        return this.nome;
    }

    public void setNome (String string) {
        this.nome = string;
    }

    public Double getValor () {
        return this.valor;
    }

    public void setValor (Double valor){
        this.valor = valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getTypeView() {
        return typeView;
    }

    public void setTypeView(int typeView) {
        this.typeView = typeView;
    }

    public Double getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(Double valorFinal) {
        this.valorFinal = valorFinal;
    }
}

