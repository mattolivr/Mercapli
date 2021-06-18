package sp.senai.br.mercapli.adapters;

import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import sp.senai.br.mercapli.R;
import sp.senai.br.mercapli.classes.Meta;

public class HistoricoAdapter extends RecyclerView.Adapter {

    private List<Meta> metas = new ArrayList<>();

    public HistoricoAdapter (SQLiteDatabase database){
        this.getMetas(database);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_historico, parent, false);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new HistoricoViewHolder(view);
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder viewHolder, int position) {
        HistoricoViewHolder holder = (HistoricoViewHolder) viewHolder;
        Meta meta = metas.get(position);

        Double valorRestante = meta.getValor() - meta.getGastoTotal();

        holder.meta.setText(
                DateFormat.getDateInstance(DateFormat.MEDIUM).format(meta.getDataCriacao()) + " - " +
                        DateFormat.getDateInstance(DateFormat.MEDIUM).format(meta.getDataExclusao())
        );
        holder.valor.setText(
                ((valorRestante >= 0)?"+":"") +
                        NumberFormat.getCurrencyInstance().format(valorRestante)
        );
        holder.valor.setTextColor(
                ((valorRestante >= 0)? Color.GREEN: Color.RED)
        );
    }

    @Override
    public int getItemCount() {
        return metas.size();
    }

    private void resetMetas(){
        this.metas.clear();
    }

    private void addMeta(Meta meta){
        metas.add(meta);
    }

    private void getMetas(SQLiteDatabase database){
        final Cursor cursorMetas;

        cursorMetas = database.query("meta", null, "meta_excl > 0 AND meta_valor > 0", null, null, null, null);

        if(cursorMetas.getCount() > 0){
            resetMetas();
            for (cursorMetas.moveToFirst(); !cursorMetas.isAfterLast(); cursorMetas.moveToNext()){
//                if(cursorMetas.getPosition() == 1) continue;
                Meta newMeta = new Meta(
                        cursorMetas.getDouble(cursorMetas.getColumnIndexOrThrow("meta_valor")),
                        cursorMetas.getDouble(cursorMetas.getColumnIndexOrThrow("meta_gasto")),
                        cursorMetas.getLong  (cursorMetas.getColumnIndexOrThrow("meta_cria")),
                        cursorMetas.getLong  (cursorMetas.getColumnIndexOrThrow("meta_excl"))
                );

                this.addMeta(newMeta);
            }
        }
        this.notifyDataSetChanged();
    }
}
