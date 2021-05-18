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

public class Compra {

    private int id;
    private Double valorTotal;
    public static ArrayList<Item> itens = new ArrayList<>();
    private long data;
    private String local;
    private String titulo;

    public Compra (){
        setData(System.currentTimeMillis());
        setValorTotal(0.0);
        setLocal("");
        setTitulo("");
    }

    public Compra (SQLiteDatabase database, long data){
        final Cursor cursorCompra;
        final Cursor cursorItens;

        String[] camposCompra = {"_id", "comp_local", "comp_titulo", "comp_data", "comp_val_tot"};
        String[] camposItens  = {"_id", "item_nome", "item_valor", "item_qtde", "item_foto", "item_cat"};

        List<Item> itens = new ArrayList<>();

        cursorCompra = database.query("compra", camposCompra, "comp_data = "+ data, null, null, null, null);

        if(cursorCompra != null){
            cursorCompra.moveToFirst();

            if(cursorCompra.getCount() == 1){
                this.setId(cursorCompra.getInt(cursorCompra.getColumnIndexOrThrow("_id")));
                this.setTitulo(cursorCompra.getString(cursorCompra.getColumnIndexOrThrow("comp_titulo")));
                this.setLocal(cursorCompra.getString(cursorCompra.getColumnIndexOrThrow("comp_local")));
                this.setValorTotal(cursorCompra.getDouble(cursorCompra.getColumnIndexOrThrow("comp_val_tot")));

                cursorItens  = database.query("item", camposItens, "comp_id_fk = " + this.id, null, null, null, null);

                if (cursorItens.getCount() > 0){
                    for(cursorItens.moveToFirst(); !cursorItens.isAfterLast(); cursorItens.moveToNext()){
                        Item newItem = new Item();
                        newItem.setNome(cursorItens.getString(cursorItens.getColumnIndexOrThrow("item_nome")));
                        newItem.setValor(cursorItens.getDouble(cursorItens.getColumnIndexOrThrow("item_valor")));
                        newItem.setQuantidade(cursorItens.getInt(cursorItens.getColumnIndexOrThrow("item_qtde")));
                        itens.add(newItem);
                    }
                    this.setItens(itens);
                }
            }
        }
    }

    public int getId() {
        return id;
    }

    private void setId(SQLiteDatabase database, long timestamp) {
        final Cursor cursor;

        cursor = database.query("compra", new String[]{"_id"}, "comp_data = "+ timestamp, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();

            if(cursor.getCount() == 1){
                this.id = cursor.getInt(cursor.getColumnIndex("_id"));
            }
        }
    }

    private void setId(int id){
        this.id = id;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void setItens(List<Item> items) {
        for (Item item: items) {
            itens.add(item);
        }
    }

    public List<Item> getItens() {
        return itens;
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

    public void finalizarCompra (SQLiteDatabase database) {
        long dbInsert;

        ContentValues insertCompra = new ContentValues();
        ContentValues insertItem   = new ContentValues();

        insertCompra.put("comp_local",  this.getLocal()     );
        insertCompra.put("comp_titulo", this.getTitulo()    );
        insertCompra.put("comp_data",   this.getData()      );
        insertCompra.put("comp_valTot", this.getValorTotal());

        dbInsert = database.insert("compra", null, insertCompra);

        if(dbInsert > 0){
            this.setId(database, this.getData());
        }

        if(this.getItens().size() > 0){
            for (int i = 0; i < this.getItens().size(); i++) {
                Item item = itens.get(i);

                insertItem.put("item_nome", item.getNome());
                insertItem.put("item_valor", item.getValor());
                insertItem.put("item_qtde", item.getQuantidade());
//                insertItem.put("item_foto", item.getFoto());
                insertItem.put("item_cat", "");
                insertItem.put("comp_id_fk", this.getId());

                database.insertOrThrow("item", null, insertItem);
            }
        }
    }

}

