package sp.senai.br.mercapli.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sp.senai.br.mercapli.R;
import sp.senai.br.mercapli.classes.Compra;

public class CompraAdapter extends RecyclerView.Adapter {

    private List<Compra> compras = new ArrayList<>();

    public CompraAdapter () {}

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_compra, parent, false);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new CompraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        CompraViewHolder holder = (CompraViewHolder) viewHolder;
        Compra compra = compras.get(position);

        holder.titulo.setText(compra.getTitulo());
        holder.data  .setText(String.valueOf(compra.getData()));
        holder.local .setText(compra.getLocal());
        holder.valor .setText(String.valueOf(compra.getValorTotal()));

        holder.clLayout.setOnClickListener(view -> {

        });
    }

    @Override
    public int getItemCount() {
        return (compras != null)?compras.size():0;
    }

    public void addCompra(Compra compra){
        this.compras.add(compra);
    }
}
