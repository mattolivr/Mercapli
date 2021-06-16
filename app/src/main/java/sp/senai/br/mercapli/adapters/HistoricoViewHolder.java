package sp.senai.br.mercapli.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import sp.senai.br.mercapli.R;

public class HistoricoViewHolder extends RecyclerView.ViewHolder {

    final TextView meta;
    final TextView valor;

    public HistoricoViewHolder(View view) {
        super(view);
        meta  = (TextView) view.findViewById(R.id.tvHistMeta);
        valor = (TextView) view.findViewById(R.id.tvHistValor);
    }
}
