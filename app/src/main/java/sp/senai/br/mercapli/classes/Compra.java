package sp.senai.br.mercapli.classes;

import java.util.ArrayList;

public class Compra {

    private int id;
    private Float valorTotal;
    public static ArrayList<Item> itens = new ArrayList<>();
    private long data;
    private String local;
    private String titulo;

    public Compra (){
        setData();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public long getData() {
        return data;
    }

    private void setData() {
        this.data = System.currentTimeMillis();
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public static String getItens() {

        String smensagem = "";

        for(Item item : itens) {
            smensagem += ""+item.getNome()+" ~ " + item.getValor() + " ~ " + item.getQuantidade() +" // ";
        }

        return smensagem;
    }

    public void inserirItem (Item item) {
        this.itens.add(item);
    }

    public void alterarItem (int itemId, String itemNome, Double itemValor, Integer itemQtde) {

        // Compensar valores null
        if (itemNome == null)  { itemNome  = itens.get(itemId-1).getNome(); }
        if (itemValor == null) { itemValor = itens.get(itemId-1).getValor(); }
        if (itemQtde == null)  { itemQtde  = itens.get(itemId-1).getQuantidade(); }

        // Reescrever valores
        for (int i = 0; i < itens.toArray().length; i++) {
            if (itens.get(i).getId() == itemId){
                itens.get(i).setNome(itemNome);
                itens.get(i).setValor(itemValor);
                itens.get(i).setQuantidade(itemQtde);
            }
        }

    }
    public void excluirItem (String itemNome) {
        for (int i = 0; i < itens.toArray().length; i++) {
            if (itens.get(i).getNome().equals(itemNome)){
                itens.remove(itens.get(i));
            }
        }
    }

    public void excluirItem (int itemID) {
        for (int i = 0; i < itens.toArray().length; i++) {
            if (itens.get(i).getId() == itemID){
                itens.remove(itens.get(i));
            }
        }
    }

    public void finalizarCompra(){

    }
}

