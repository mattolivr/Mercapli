package sp.senai.br.mercapli;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.navigation.NavigationView;

import sp.senai.br.mercapli.adapters.ListaAdapter;
import sp.senai.br.mercapli.database.CriarBD;

public class ListaFragment extends Fragment {

    private RecyclerView rvListas;
    private ListaAdapter listaAdapter;
    private LinearLayoutManager listaLayoutManager;

    private Button btAdd;
    private DrawerLayout drawerLayoutLista;
    private ImageButton imageMenuLista;
    private NavigationView navigationViewLista;
    private NavController navControllerLista;

    private SQLiteDatabase database;

    public ListaFragment() {}

    public static ListaFragment newInstance() {
        ListaFragment fragment = new ListaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista, container, false);

        rvListas = view.findViewById(R.id.rvListaItens);

        btAdd = view.findViewById(R.id.btnListaAdd);
        drawerLayoutLista = view.findViewById(R.id.drawerLayoutLista);
        imageMenuLista = view.findViewById(R.id.ibListaMenu);
        navigationViewLista = view.findViewById(R.id.navigationViewLista);
        navControllerLista = Navigation.findNavController(getActivity(), R.id.fragment);

        listaAdapter = new ListaAdapter(this.getContext(), ListaAdapter.LISTA_DEFAULT);
        listaLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, true);

        database = new CriarBD(view.getContext()).getReadableDatabase();

        rvListas.setAdapter(listaAdapter);
        rvListas.setLayoutManager(listaLayoutManager);

        NavigationUI.setupWithNavController(navigationViewLista, navControllerLista);

        btAdd.setOnClickListener(add -> adicionarLista());
        imageMenuLista.setOnClickListener(open -> drawerLayoutLista.openDrawer(GravityCompat.START));

        listaAdapter.setListas(database);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        listaAdapter.setListas(database);
    }

    private void adicionarLista(){
        Intent it = new Intent(this.getContext(), ListaActivity.class);
        startActivity(it);
    }

}