package sp.senai.br.mercapli.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import sp.senai.br.mercapli.R;

public class CompraListaViewHolder extends RecyclerView.ViewHolder {

    final ConstraintLayout clLayout;
    final TextView valor;
    final TextView local;
    final TextView data;
    final TextView titulo;

    final ConstraintLayout clLayoutS;
    final TextView tituloS;
    final TextView valorS;
    final TextView localS;
    final TextView dataS;

    public CompraListaViewHolder(View view) {
        super(view);
        clLayout = (ConstraintLayout) view.findViewById(R.id.clCompras);
        valor  = (TextView) view.findViewById(R.id.tvCompraValor );
        local  = (TextView) view.findViewById(R.id.tvCompraLocal );
        data   = (TextView) view.findViewById(R.id.tvCompraData  );
        titulo = (TextView) view.findViewById(R.id.tvCompraTitulo);

        tituloS  = (TextView) itemView.findViewById(R.id.tvBaseItemTitulo);
        valorS   = (TextView) itemView.findViewById(R.id.tvBaseItemValor);
        localS   = (TextView) itemView.findViewById(R.id.tvBaseItemLocal);
        dataS    = (TextView) itemView.findViewById(R.id.tvBaseItemData);
        clLayoutS = (ConstraintLayout) itemView.findViewById(R.id.clBaseItemLayout);
    }
}
