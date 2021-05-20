package sp.senai.br.mercapli;

import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class ListaFragment extends Fragment {

    public ListaFragment() {}

    public static ListaFragment newInstance() {
        ListaFragment fragment = new ListaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    DrawerLayout drawerLayoutLista;
    ImageButton imageMenuLista;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lista, container, false);

        drawerLayoutLista = view.findViewById(R.id.drawerLayoutLista);
        imageMenuLista = view.findViewById(R.id.ibListaMenu);

        imageMenuLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayoutLista.openDrawer(GravityCompat.START);
            }
        });

        return view;
    }
}