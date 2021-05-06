package sp.senai.br.mercapli;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import sp.senai.br.mercapli.classes.Compra;

public class CompraFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CompraFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CompraFragment newInstance(String param1, String param2) {
        CompraFragment fragment = new CompraFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    DrawerLayout drawerLayoutCompra;
    ImageButton imageMenuCompra;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compra, container, false);

        drawerLayoutCompra = view.findViewById(R.id.drawerLayoutCompra);
        imageMenuCompra = view.findViewById(R.id.imageMenuCompra);

        imageMenuCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayoutCompra.openDrawer(GravityCompat.START);
            }
        });

        return view;
    }
}