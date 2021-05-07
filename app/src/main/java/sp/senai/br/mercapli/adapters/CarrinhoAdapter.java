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

public class CarrinhoAdapter extends RecyclerView.Adapter {

    private List<Item> produtos = new ArrayList<>();
    private Context context;
    private Boolean editing;

    public CarrinhoAdapter(Context context) {
        this.context = context;
        this.editing = false;
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {
            case PROD_VIEW:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_produto, parent, false);
                view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
                return new CarrinhoViewHolder(view);
            case PROD_EDIT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_produto_edit, parent, false);
                view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
                return new CarrinhoViewHolder(view);
        }
        return null;
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        CarrinhoViewHolder holder = (CarrinhoViewHolder) viewHolder;
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

    public List<Item> getProdutos() {
        return produtos;
    }

    @Override
    public int getItemViewType(int position) {
        return produtos.get(position).getTypeView();
    }

    public Double getValorTotal() {
        Double ValorTotal = 0.0;

        for(int i = 0; i<produtos.size(); i++)
        {
            if( produtos.get(i).getValorFinal() != null){
                ValorTotal += produtos.get(i).getValorFinal();
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

    private void insertDataOnItem(CarrinhoViewHolder holder, Item produto) {
        Double  dValor = Double.parseDouble(holder.precoE.getText().toString());
        Integer iQtde  = Integer.parseInt  (holder.qtdeE .getText().toString());

        produto.setNome(holder.nomeE.getText().toString());
        produto.setValor(dValor);
//                    produto.setCategoria(holder.catE.toString());
        produto.setQuantidade(iQtde);

        produto.setValorFinal(dValor * iQtde);
    }
}