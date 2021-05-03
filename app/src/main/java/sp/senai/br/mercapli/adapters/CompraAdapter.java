package sp.senai.br.mercapli.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import sp.senai.br.mercapli.R;
import sp.senai.br.mercapli.classes.Item;

import static sp.senai.br.mercapli.Constant.PROD_EDIT;
import static sp.senai.br.mercapli.Constant.PROD_VIEW;

public class CompraAdapter extends RecyclerView.Adapter {

    private List<Item> produtos = new ArrayList<>();
    private Context context;

    public CompraAdapter(Context context) {
        this.context = context;
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {
            case PROD_VIEW:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_produto, null);
                return new CompraViewHolder(view);
            case PROD_EDIT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_produto_edit, null);
                return new CompraViewHolder(view);
        }
        return null;
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        CompraViewHolder holder = (CompraViewHolder) viewHolder;
        Item produto = produtos.get(position);

        switch (produto.getTypeView()){
            case PROD_VIEW:
                holder.nome.setText(produto.getNome());
                holder.preco.setText(produto.getValor().toString());
                holder.cat.setText(produto.getCategoria());
                holder.id.setText("" +produto.getId());
                holder.qtde.setText("" + produto.getQuantidade());
                break;
            case PROD_EDIT:
                holder.nomeE.setText(produto.getNome());
                holder.precoE.setText(produto.getValor().toString());
                holder.idE.setText("" + produto.getId());
                holder.qtdeE.setText("" + produto.getQuantidade());
                break;
        }
    }

    @Override public int getItemCount() { return (produtos != null)?produtos.size():0; }

    @Override
    public int getItemViewType(int position) {
        return produtos.get(position).getTypeView();
    }

    public void addProduto(Item produto){
        this.produtos.add(produto);
    }
}
