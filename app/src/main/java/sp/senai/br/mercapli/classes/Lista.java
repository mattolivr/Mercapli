package sp.senai.br.mercapli.classes;

import java.util.ArrayList;
import java.util.List;

public class Lista {

    private int id;
    private String titulo;
    private String local;
    private static ArrayList<Item> itens = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public static ArrayList<Item> getItens() {
        return itens;
    }

    public static void setItens(List<Item> itens) {
        itens.addAll(itens);
    }

}
