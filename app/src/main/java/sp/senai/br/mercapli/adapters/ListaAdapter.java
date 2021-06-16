package sp.senai.br.mercapli.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import sp.senai.br.mercapli.ListaActivity;
import sp.senai.br.mercapli.R;
import sp.senai.br.mercapli.classes.Item;
import sp.senai.br.mercapli.classes.Lista;

public class ListaAdapter extends RecyclerView.Adapter {

    private List<Lista> listas = new ArrayList<>();
    private Context context;

    public ListaAdapter (Context context){
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_compra, parent, false);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new CompraListaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        CompraListaViewHolder holder = (CompraListaViewHolder) viewHolder;
        Lista lista = listas.get(position);

        holder.titulo.setText(lista.getTitulo());
        holder.data.setText(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.DATE_FIELD).format(lista.getData()));
        holder.local.setText(lista.getLocal());
        holder.valor.setText(NumberFormat.getInstance().format(lista.getValorTotal()));

        if(holder.titulo.getText().equals("")) holder.titulo.setHeight(1);
        if(holder.local.getText() .equals("")) holder.local .setHeight(1);

        holder.clLayout.setOnClickListener(view -> {
            Intent it = new Intent(context, ListaActivity.class);
            it.putExtra("newParam", false);
            it.putExtra("listaData", lista.getData());
            context.startActivity(it);
        });
    }

    @Override
    public int getItemCount() {
        return listas.size();
    }

    public void addLista(Lista lista){this.listas.add(lista);}

    public void resetListas(){this.listas.clear();}

    public void getListas(SQLiteDatabase database){
        final Cursor cursor;

        cursor = database.query("lista",null,null, null, null, null, null);

        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            this.resetListas();

            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                Lista newLista = new Lista();

                newLista.setId(cursor.getInt(cursor.getColumnIndexOrThrow("_id")));
                newLista.setValorTotal(cursor.getDouble(cursor.getColumnIndexOrThrow("_valTot")));
                newLista.setData(cursor.getLong(cursor.getColumnIndexOrThrow("_data")));
                newLista.setLocal(cursor.getString(cursor.getColumnIndexOrThrow("_local")));
                newLista.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow("_titulo")));

                this.addLista(newLista);
            }
        }
        this.notifyDataSetChanged();
    }
}
