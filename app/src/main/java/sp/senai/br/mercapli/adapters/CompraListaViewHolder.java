package sp.senai.br.mercapli.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import sp.senai.br.mercapli.R;

public class CompraListaViewHolder extends RecyclerView.ViewHolder {
    final ConstraintLayout clLayout;
//    final TextView id;
    final TextView valor;
    final TextView local;
    final TextView data;
    final TextView titulo;

    public CompraListaViewHolder(View view) {
        super(view);
        clLayout = (ConstraintLayout) view.findViewById(R.id.clCompras);
//        id     = (TextView) view.findViewById(R.id.tvCompraID    );
        valor  = (TextView) view.findViewById(R.id.tvCompraValor );
        local  = (TextView) view.findViewById(R.id.tvCompraLocal );
        data   = (TextView) view.findViewById(R.id.tvCompraData  );
        titulo = (TextView) view.findViewById(R.id.tvCompraTitulo);
    }
}
