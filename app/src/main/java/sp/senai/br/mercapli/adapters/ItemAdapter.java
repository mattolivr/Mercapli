package sp.senai.br.mercapli.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import sp.senai.br.mercapli.R;
import sp.senai.br.mercapli.classes.Item;
import sp.senai.br.mercapli.dialogs.FotoProdutoDialog;

import static sp.senai.br.mercapli.GlobalVariables.GASTO_LOCAL;
import static sp.senai.br.mercapli.GlobalVariables.ITEM_CARRINHO;
import static sp.senai.br.mercapli.GlobalVariables.PROD_EDIT;
import static sp.senai.br.mercapli.GlobalVariables.PROD_VIEW;

public class ItemAdapter extends RecyclerView.Adapter {

    private List<Item> produtos = new ArrayList<>();
    private int listType;
    private Context context;
    private Boolean editing;
    private FragmentManager fragmentManager;

    public ItemAdapter(Context context, FragmentManager fragmentManager, int listType) {
        this.context = context;
        this.editing = false;
        this.fragmentManager = fragmentManager;
        this.listType = listType;
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {
            case PROD_VIEW:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_produto_view, parent, false);
                view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
                return new ItemViewHolder(view);
            case PROD_EDIT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_produto_edit, parent, false);
                view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
                return new ItemViewHolder(view);
        }
        return null;
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ItemViewHolder holder = (ItemViewHolder) viewHolder;
        Item produto = produtos.get(position);
        produto.setId(position);

        switch (produto.getTypeView()){
            case PROD_VIEW: // Alterações em modo de visualização
                holder.nome.setText(produto.getNome());
                holder.preco.setText(produto.getValor().toString());
                holder.qtde.setText("x" + produto.getQuantidade());
                holder.precoFinal.setText(NumberFormat.getCurrencyInstance().format(produto.getValor() * produto.getQuantidade()));

                // Ouvir clique para editar
                holder.cLayout.setOnClickListener(view -> {
                    if(!isEditing()){
                        produto.setTypeView(PROD_EDIT);

                        this.notifyItemChanged(position);
                        this.setEditing(true);
                    }
                });

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

                    if(listType == ITEM_CARRINHO){
                        GASTO_LOCAL = getProdutosSum();
                    }
                });

                holder.btnPrdEdtCancelar.setOnClickListener(view -> {
                    produto.setTypeView(PROD_VIEW);
                    this.notifyItemChanged(position);
                    this.setEditing(false);
                });

                holder.btnPrdEdtExcluir.setOnClickListener(view -> {
                    produtos.remove(position);
                    this.notifyItemRemoved(position);
                    this.setEditing(false);

                    if(listType == ITEM_CARRINHO){
                        GASTO_LOCAL = getProdutosSum();
                    }
                });
                break;
        }
    }

    @Override public int getItemCount() { return (produtos != null)?produtos.size():0; }

    public List<Item> getProdutos() {
        return produtos;
    }

    @Override
    public int getItemViewType(int position) {
        return produtos.get(position).getTypeView();
    }

    public Double getValorTotal() {
        Double ValorTotal = 0.0;

        for (Item produto: produtos) {
            if(produto != null){
                ValorTotal += produto.getValorFinal();
            }
        }

        return ValorTotal;
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

    private void insertDataOnItem(ItemViewHolder holder, Item produto) {
        Double  dValor = Double.parseDouble(holder.precoE.getText().toString());
        Integer iQtde  = Integer.parseInt  (holder.qtdeE .getText().toString());

        produto.setNome(holder.nomeE.getText().toString());
        produto.setValor(dValor);
        produto.setQuantidade(iQtde);
    }

    public void resetGastoLocal(){
        GASTO_LOCAL = 0.0;
    }

    public Double getProdutosSum(){
        Double sum = 0.0;
        for (Item produto: produtos) {
            sum += produto.getValorFinal();
        }
        return sum;
    }
}
