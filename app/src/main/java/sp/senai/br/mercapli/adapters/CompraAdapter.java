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

import sp.senai.br.mercapli.CarrinhoActivity;
import sp.senai.br.mercapli.R;
import sp.senai.br.mercapli.classes.Compra;

import static sp.senai.br.mercapli.GlobalVariables.META_GASTOS;

public class CompraAdapter extends RecyclerView.Adapter {

    private List<Compra> compras = new ArrayList<>();
    private Context context;

    public CompraAdapter (Context context) {
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
        Compra compra = compras.get(position);

        holder.titulo.setText(compra.getTitulo());
        holder.data  .setText(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.DATE_FIELD).format(compra.getData()) +
                ((compra.getData() < META_GASTOS.getDataCriacao())?" - Finalizada":""));
        holder.local .setText(compra.getLocal());
        holder.valor .setText(NumberFormat.getInstance().format(compra.getValorTotal()));

        if(holder.titulo.getText().equals("")) holder.titulo.setHeight(1);
        if(holder.local.getText() .equals("")) holder.local .setHeight(1);

        holder.clLayout.setOnClickListener(view -> {
            Intent it = new Intent(context, CarrinhoActivity.class);
            it.putExtra("newParam", false);
            it.putExtra("compraData", compra.getData());
            context.startActivity(it);
        });
    }

    @Override
    public int getItemCount() {
        return compras.size();
    }

    public void addCompra(Compra compra){
        this.compras.add(compra);
    }

    public void resetCompra() { this.compras.clear(); }

    public List<Compra> getCompras() {
        if(this.getItemCount() > 0){
            return this.compras;
        } else {
            return null;
        }
    }

    public void getCompras(SQLiteDatabase database) {

        final Cursor cursor;

        cursor = database.query("compra", null,null, null,null,null,null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            this.resetCompra();

            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                Compra newCompra = new Compra();

                newCompra.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow("_titulo")));
                newCompra.setLocal (cursor.getString(cursor.getColumnIndexOrThrow("_local" )));
                newCompra.setValorTotal(cursor.getDouble(cursor.getColumnIndexOrThrow("_valTot")));
                newCompra.setData(cursor.getLong(cursor.getColumnIndexOrThrow("_data")));

                this.addCompra(newCompra);
            }
        }
        this.notifyDataSetChanged();
    }
}
