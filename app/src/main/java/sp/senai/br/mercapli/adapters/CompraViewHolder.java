package sp.senai.br.mercapli.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import sp.senai.br.mercapli.R;

public class CompraViewHolder extends RecyclerView.ViewHolder {
    final TextView nome;
    final TextView preco;
    final TextView qtde;
    final TextView cat;
    final TextView id;


    public CompraViewHolder(View view) {
        super(view);
        nome    = (TextView) view.findViewById(R.id.nome_produto_layout );
        preco   = (TextView) view.findViewById(R.id.preco_produto_layout);
        qtde    = (TextView) view.findViewById(R.id.qtde_produto_layout );
        cat     = (TextView) view.findViewById(R.id.cat_produto_layout  );
        id      = (TextView) view.findViewById(R.id.id_produto_layout   );
    }
}
