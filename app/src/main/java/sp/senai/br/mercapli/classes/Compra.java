package sp.senai.br.mercapli.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import sp.senai.br.mercapli.database.CriarBD;

public class Compra {

    private int id;
    private Double valorTotal;
    public static ArrayList<Item> itens = new ArrayList<>();
    private long data;
    private String local;
    private String titulo;

    public Compra (){
        setData();
        setValorTotal(0.0);
        setLocal("");
        setTitulo("");
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
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

    public void setItems (List<Item> items) {
        for (Item item: items) {
            this.itens.add(item);
        }
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

    public void finalizarCompra (SQLiteDatabase database) {
        long dbInsert;
        String[] campos = {"_id", "comp_local", "comp_titulo", "comp_data", "comp_valTot"};
        ContentValues insertValues = new ContentValues();

        insertValues.put("comp_local", this.getLocal());
        insertValues.put("comp_titulo", this.getTitulo());
        insertValues.put("comp_data", this.getData());
        insertValues.put("comp_valTot", this.getValorTotal());

        dbInsert = database.insert("compra", null, insertValues);

        if(dbInsert > 0){
            System.out.println("Dados inseridos");
            System.out.println(dbInsert + " linhas afetadas");

            this.setId(database);
            System.out.println("Id da compra: " + this.getId());
        } else {
            System.out.println("Erro na inserção");
        }
    }

    public int getId() {
        return id;
    }

    public void setId(SQLiteDatabase database) {
        final Cursor cursor;

        cursor = database.query("compra", new String[]{"comp_data"}, "comp_data = "+ this.getData(), null, null, null, null);

        if(cursor.getCount() == 1){
            this.id = cursor.getInt(0);
        }
    }
}

