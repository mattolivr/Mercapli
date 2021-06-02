package sp.senai.br.mercapli.adapters;

import android.content.Context;
import android.content.Intent;
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

        // TODO: utilizar ID ao invés de Data
//        holder.id    .setText(compra.getId());
        holder.titulo.setText(compra.getTitulo());
        holder.data  .setText(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.DATE_FIELD).format(compra.getData()));
        holder.local .setText(compra.getLocal());
        holder.valor .setText(NumberFormat.getInstance().format(compra.getValorTotal()));

        // Campos vazios não ocuparem espaço
        // TODO: campos somem ao sair de vizualização
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
