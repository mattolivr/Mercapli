package sp.senai.br.mercapli.classes;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import sp.senai.br.mercapli.exceptions.MercadoException;

public abstract class Mercado {

    private int id;
    private String local;
    private String titulo;
    private String table;
    private long data;
    private Double valorTotal;
    private ArrayList<Item> itens = new ArrayList<>();

    public Mercado (String table){
        setData(System.currentTimeMillis());
        setValorTotal(0.0);
        setLocal("");
        setTitulo("");
        setTable(table);
        clearItens();
    }

    public Mercado (SQLiteDatabase database, long timestamp, String table){
        final Cursor cursorMercado;
        final Cursor cursorItens;

        List<Item> itens = new ArrayList<>();
        this.setTable(table);

        cursorMercado = database.query(table, null, "_data = " + timestamp, null, null, null, null);

        if(cursorMercado.getCount() > 0){
            cursorMercado.moveToFirst();

            if(cursorMercado.getCount() == 1){
                this.setId(cursorMercado.getInt(cursorMercado.getColumnIndexOrThrow("_id")));
                this.setTitulo(cursorMercado.getString(cursorMercado.getColumnIndexOrThrow("_titulo")));
                this.setLocal(cursorMercado.getString(cursorMercado.getColumnIndexOrThrow("_local")));
                this.setValorTotal(cursorMercado.getDouble(cursorMercado.getColumnIndexOrThrow("_valTot")));
                this.setData(cursorMercado.getLong(cursorMercado.getColumnIndexOrThrow("_data")));

                clearItens();

                cursorItens  = database.query("item", null, table + "_id_fk = " + this.id, null, null, null, null);

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

    public void setId(int id) {
        this.id = id;
    }

    public void setId(SQLiteDatabase database, long timestamp) {
        final Cursor cursor;

        cursor = database.query(getTable(), null, "_data = " + timestamp, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();

            if(cursor.getCount() == 1){
                this.id = cursor.getInt(cursor.getColumnIndex("_id"));
            }
        }
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

    private String getTable() {
        return table;
    }

    private void setTable(String table){
        this.table = table;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void clearItens () {
        itens.clear();
    }

    public void setItens(List<Item> items) {
        for (Item item: items) {
            itens.add(item);
        }
    }

    public void finalizar(SQLiteDatabase database) throws MercadoException {
        long dbInsert;

        ContentValues insertMercado = new ContentValues();
        ContentValues insertItem   = new ContentValues();

        if(this.getItens().size() == 0){
            throw new MercadoException("Não é possível finalizar uma compra sem produtos");
        }

        insertMercado.put("_local",  this.getLocal()     );
        insertMercado.put("_titulo", this.getTitulo()    );
        insertMercado.put("_data",   this.getData()      );
        insertMercado.put("_valTot", this.getValorTotal());

        dbInsert = database.insert(this.getTable(), null, insertMercado);

        if(dbInsert > 0){
            this.setId(database, this.getData());
        }

        for (int i = 0; i < this.getItens().size(); i++) {
            Item item = itens.get(i);

            insertItem.put("item_nome", item.getNome());
            insertItem.put("item_valor", item.getValor());
            insertItem.put("item_qtde", item.getQuantidade());
            insertItem.put("item_cat", "");
            insertItem.put(this.getTable()+"_id_fk", this.getId());

            database.insertOrThrow("item", null, insertItem);
        }
    }

    public void atualizar(SQLiteDatabase database){
        ContentValues updateCompra = new ContentValues();
        ContentValues updateItem   = new ContentValues();

        updateCompra.put("_local" , this.getLocal());
        updateCompra.put("_titulo", this.getTitulo());
        updateCompra.put("_data"  , this.getData());
        updateCompra.put("_valTot", this.getValorTotal());

        database.update(this.table, updateCompra, "_id = " + this.getId(), null);

        if (this.getItens().size() > 0){
            for (Item item: this.getItens()) {
                updateItem.put("item_nome" , item.getNome());
                updateItem.put("item_valor", item.getValor());
                updateItem.put("item_qtde" , item.getQuantidade());
                updateItem.put("item_cat"  , "");
                updateItem.put(this.getTable() + "_id_fk", this.getId());

                database.update("item", updateItem, this.getTable() + "_id_fk = " + this.getId(), null);
            }
        }
    }

    public void deletar(SQLiteDatabase database) throws MercadoException {
        final Cursor cursorCompras;
        final Cursor cursorItens;

        cursorCompras = database.query(this.getTable(), null, "_id = " + this.getId(), null, null, null, null);

        if(cursorCompras.getCount() == 1) {
            database.delete(this.getTable(), "_id = " + this.getId(), null);

            cursorItens = database.query("item", null, this.getTable() +"_id_fk = " + this.getId(), null, null, null, null);

            if (cursorItens.getCount() > 0) {
                for (cursorItens.moveToFirst(); !cursorItens.isAfterLast(); cursorItens.moveToNext()) {
                    database.delete("item", this.getTable() + "_id_fk = " + this.getId(), null);
                }
            }
        } else {
            throw new MercadoException("Um erro ocorreu");
        }
    }
}
