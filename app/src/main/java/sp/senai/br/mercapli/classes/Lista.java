package sp.senai.br.mercapli.classes;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import sp.senai.br.mercapli.exceptions.CompraException;
import sp.senai.br.mercapli.exceptions.ListaException;

public class Lista {

    private int id;
    private Double valorTotal;
    private String titulo;
    private String local;
    private long data;
    private ArrayList<Item> itens = new ArrayList<>();

    public Lista (){
        setValorTotal(0.0);
        setTitulo("");
        setLocal("");
        setData(System.currentTimeMillis());
        clearItens();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private void setId(SQLiteDatabase database, long timestamp) {
        // TODO: Criar DATA
        final Cursor cursor;

        cursor = database.query("lista", new String[]{"_id"}, "lista_data = "+ timestamp, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();

            if(cursor.getCount() == 1){
                this.id = cursor.getInt(cursor.getColumnIndex("_id"));
            }
        }
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
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

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> items) {
        for (Item item: items) {
            itens.add(item);
        }
    }

    public void clearItens(){
        itens.clear();
    }

    public void finalizarLista(SQLiteDatabase database) {
        long dbInsert;

        ContentValues insertCompra = new ContentValues();
        ContentValues insertItem   = new ContentValues();

        insertCompra.put("lista_local" , this.getLocal()     );
        insertCompra.put("lista_titulo", this.getTitulo()    );
        insertCompra.put("lista_valTot", this.getValorTotal());
        insertCompra.put("lista_data"  , this.getData());

        dbInsert = database.insert("lista", null, insertCompra);

        if(dbInsert > 0){
            this.setId(database, this.getData());
        }

        System.out.println("Itens: " + getItens().size());

        if(this.getItens().size() > 0){
            for (Item item: getItens()) {

                insertItem.put("item_nome", item.getNome());
                insertItem.put("item_valor", item.getValor());
                insertItem.put("item_qtde", item.getQuantidade());
                insertItem.put("item_foto", "");
                insertItem.put("item_cat", "");
                insertItem.put("lista_id_fk", this.getId());

                database.insertOrThrow("item", null, insertItem);
            }
        }
    }

    public void deletarLista(SQLiteDatabase database) throws ListaException {
        final Cursor cursorListas;
        final Cursor cursorItens;

        cursorListas = database.query("lista", null, "_id = " + this.getId(), null, null, null, null);

        if(cursorListas.getCount() == 1) {
            database.delete("lista", "_id = " + this.getId(), null);

            cursorItens = database.query("item", null, "lista_id_fk = " + this.getId(), null, null, null, null);

            if (cursorItens.getCount() > 0) {
                for (cursorItens.moveToFirst(); !cursorItens.isAfterLast(); cursorItens.moveToNext()) {
                    database.delete("item", "lista_id_fk = " + this.getId(), null);
                }
            }
        } else {
            throw new ListaException("Um erro ocorreu");
        }
    }

}
