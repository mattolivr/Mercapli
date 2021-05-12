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
import java.util.ArrayList;
import java.util.List;

import sp.senai.br.mercapli.CompraViewActivity;
import sp.senai.br.mercapli.R;
import sp.senai.br.mercapli.classes.Compra;

public class CompraAdapter extends RecyclerView.Adapter {

    private final List<Compra> compras = new ArrayList<>();
    private Context context;

    private final SQLiteDatabase database;

    public CompraAdapter (SQLiteDatabase database) {
        this.database = database;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_compra, parent, false);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        context = view.getContext();
        return new CompraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        CompraViewHolder holder = (CompraViewHolder) viewHolder;
        Compra compra = compras.get(position);
        compra.setId(database);

        holder.titulo.setText(compra.getTitulo());
        holder.data  .setText(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.DATE_FIELD).format(compra.getData()));
        holder.local .setText(compra.getLocal());
        holder.valor .setText(String.valueOf(compra.getValorTotal()));

        if(holder.titulo.getText().equals("")) holder.titulo.setHeight(0);
        if(holder.local .getText().equals("")) holder.local .setHeight(0);

        holder.clLayout.setOnClickListener(view -> {
            Intent it = new Intent(context, CompraViewActivity.class);
            it.putExtra("ItemID", compra.getId());
            context.startActivity(it);
        });
    }

    @Override
    public int getItemCount() {
        return (compras != null)?compras.size():0;
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
}
