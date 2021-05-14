package sp.senai.br.mercapli.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import sp.senai.br.mercapli.database.CriarBD;

import static sp.senai.br.mercapli.Constant.PROD_VIEW;

public class Compra {

    private int id;
    private Double valorTotal;
    private long data;
    private String local;
    private String titulo;

    private static ArrayList<Item> itens = new ArrayList<>();

    public Compra (){
        setData(1);
        setValorTotal(0.0);
        setLocal("");
        setTitulo("");
    }

    public Compra (SQLiteDatabase database, int id){
        final Cursor cursorCompra;
        final Cursor cursorItens;
        String[] camposCompra = {"_id", "comp_local", "comp_titulo", "comp_data", "comp_valTot"};
        String[] camposItem   = {"_id", "item_nome", "item_valor", "item_qtde", "item_foto", "item_cat"};
        List<Item> itens = new ArrayList<>();

        cursorCompra = database.query("compra", camposCompra, "_id = " + id, null, null, null, null);
        cursorItens  = database.query("item", camposItem, "comp_id_fk = " + id, null, null, null, null);

        if(cursorCompra != null){
            cursorCompra.moveToFirst();
        }

        if(cursorCompra.getCount() == 1){
            setId(id);
            setData(cursorCompra.getLong(cursorCompra.getColumnIndexOrThrow("comp_data")));
            setLocal(cursorCompra.getString(cursorCompra.getColumnIndexOrThrow("comp_local")));
            setTitulo(cursorCompra.getString(cursorCompra.getColumnIndexOrThrow("comp_titulo")));
            setValorTotal(cursorCompra.getDouble(cursorCompra.getColumnIndexOrThrow("comp_valTot")));

            if(cursorItens != null){
                for (cursorItens.moveToFirst(); !cursorItens.isAfterLast(); cursorItens.moveToNext()){
                    Item newItem = new Item(
                            cursorItens.getInt(cursorItens.getColumnIndexOrThrow("_id")),
                            cursorItens.getString(cursorItens.getColumnIndexOrThrow("item_nome")),
                            cursorItens.getDouble(cursorItens.getColumnIndexOrThrow("item_valor")),
                            cursorItens.getInt(cursorItens.getColumnIndexOrThrow("item_qtde")),
                            PROD_VIEW
                    );
                    itens.add(newItem);
                }
                this.setItens(itens);
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(SQLiteDatabase database) {
        final Cursor cursor;

        cursor = database.query("compra", new String[]{"_id"}, "comp_data = "+ this.getData(), null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        if(cursor.getCount() == 1){
            this.id = cursor.getInt(cursor.getColumnIndex("_id"));
        }
    }

    public void setId(int id){
        this.id = id;
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

    public void setData(long data) {
        this.data = data;
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

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> items) {
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
        ContentValues insertValues = new ContentValues();

        insertValues.put("comp_local", this.getLocal());
        insertValues.put("comp_titulo", this.getTitulo());
        insertValues.put("comp_data", System.currentTimeMillis());
        insertValues.put("comp_valTot", this.getValorTotal());

        database.insert("compra", null, insertValues);
    }
}

