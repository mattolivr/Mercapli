package sp.senai.br.mercapli.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sp.senai.br.mercapli.R;
import sp.senai.br.mercapli.classes.Item;

public class CompraAdapter extends RecyclerView.Adapter {

    private List<Item> produtos;
    private Context context;

    public CompraAdapter(List<Item> produtos, Context context) {
        this.produtos = produtos;
        this.context = context;
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_produto, parent, false);
        CompraViewHolder holder = new CompraViewHolder(view);
        return holder;
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        CompraViewHolder holder = (CompraViewHolder) viewHolder;
        Item produto = produtos.get(position);

        holder.nome.setText(produto.getNome());
        holder.preco.setText(produto.getValor().toString());
        holder.cat.setText(produto.getCategoria());
        holder.id.setText(produto.getId());
        holder.qtde.setText(produto.getQuantidade());
    }

    @Override public int getItemCount() { return produtos.size(); }
}
