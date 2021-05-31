package sp.senai.br.mercapli.adapters;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sp.senai.br.mercapli.classes.Item;
import sp.senai.br.mercapli.classes.Lista;

public class ListaAdapter extends RecyclerView.Adapter {

    private List<Lista> listas = new ArrayList<>();

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

    public void addLista(Lista lista){this.listas.add(lista);}

    public void resetListas(){this.listas.clear();}
}
