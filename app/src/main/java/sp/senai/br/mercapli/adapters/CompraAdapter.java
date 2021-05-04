package sp.senai.br.mercapli.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import sp.senai.br.mercapli.R;
import sp.senai.br.mercapli.classes.Item;

import static sp.senai.br.mercapli.Constant.PROD_EDIT;
import static sp.senai.br.mercapli.Constant.PROD_VIEW;

public class CompraAdapter extends RecyclerView.Adapter {

    private List<Item> produtos = new ArrayList<>();
    private Context context;
    private Boolean editing;

    public CompraAdapter(Context context) {
        this.context = context;
        this.editing = false;
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
        produto.setId(position);

        switch (produto.getTypeView()){
            case PROD_VIEW: // Alterações em modo de visualização
                holder.nome.setText(produto.getNome());
                holder.preco.setText(produto.getValor().toString());
                holder.cat.setText(produto.getCategoria());
                holder.id.setText("" +produto.getId());
                holder.qtde.setText("x" + produto.getQuantidade());
                holder.precoFinal.setText(NumberFormat.getCurrencyInstance().format(produto.getValorFinal()));

                break;
            case PROD_EDIT: // Modo de Edição
                setEditing(true); // Permitir somente 1 edição por vez

                // Resgatar dados preexistentes
                holder.nomeE.setText(produto.getNome());
                holder.precoE.setText((produto.getValor() != 0.0)?produto.getValor().toString():"");
                holder.idE.setText("" + produto.getId());
                holder.qtdeE.setText((produto.getQuantidade() > 0)?"" + produto.getQuantidade():"");

                // Ouvir ação dos Botões
                holder.btnPrdEdtAdicionar.setOnClickListener(view -> {
                    produto.setTypeView(PROD_VIEW);

                    insertDataOnItem(holder, produto);

                    this.notifyItemChanged(position);
                    this.setEditing(false);
                });

                holder.btnPrdEdtCancelar.setOnClickListener(view -> {
                    produtos.remove(position);
                    this.notifyItemRemoved(position);
                    this.setEditing(false);
                });
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

    public boolean isEditing(){
        return this.editing;
    }

    public void setEditing(Boolean editing) {
        this.editing = editing;
    }

    private void insertDataOnItem(CompraViewHolder holder, Item produto) {
        Double dValor = Double.parseDouble(holder.precoE.getText().toString());
        Integer iQtde = Integer.parseInt(holder.qtdeE.getText().toString());

        produto.setNome(holder.nomeE.getText().toString());
        produto.setValor(dValor);
//                    produto.setCategoria(holder.catE.toString());
        produto.setQuantidade(iQtde);

        produto.setValorFinal(dValor * iQtde);
    }
}
