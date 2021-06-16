package sp.senai.br.mercapli.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import sp.senai.br.mercapli.R;

public class ItemBaseViewHolder extends RecyclerView.ViewHolder {

    final TextView titulo;
    final TextView valor;
    final TextView local;
    final TextView data;

    final ConstraintLayout clLayout;

    public ItemBaseViewHolder(View itemView) {
        super(itemView);
        titulo  = (TextView) itemView.findViewById(R.id.tvBaseItemTitulo);
        valor   = (TextView) itemView.findViewById(R.id.tvBaseItemValor);
        local   = (TextView) itemView.findViewById(R.id.tvBaseItemLocal);
        data    = (TextView) itemView.findViewById(R.id.tvBaseItemData);
        clLayout = (ConstraintLayout) itemView.findViewById(R.id.clBaseItemLayout);
    }
}
