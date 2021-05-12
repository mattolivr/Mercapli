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
        setData(1);
        setValorTotal(0.0);
        setLocal("");
        setTitulo("");
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

    public void setItens(List<Item> items) {
        for (Item item: items) {
            this.itens.add(item);
        }
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
        ContentValues insertValues = new ContentValues();

        insertValues.put("comp_local", this.getLocal());
        insertValues.put("comp_titulo", this.getTitulo());
        insertValues.put("comp_data", System.currentTimeMillis());
        insertValues.put("comp_valTot", this.getValorTotal());

        database.insert("compra", null, insertValues);
    }

}

