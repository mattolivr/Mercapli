package sp.senai.br.mercapli.adapters;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sp.senai.br.mercapli.classes.Compra;
import sp.senai.br.mercapli.classes.Item;

public class CVAdapter extends RecyclerView.Adapter {

    private List<Item> produtos = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
