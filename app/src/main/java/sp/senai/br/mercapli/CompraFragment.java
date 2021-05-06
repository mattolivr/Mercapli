package sp.senai.br.mercapli;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import sp.senai.br.mercapli.adapters.CompraAdapter;
import sp.senai.br.mercapli.classes.Compra;
import sp.senai.br.mercapli.database.CriarBD;

public class CompraFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView rvCompras;
    private CompraAdapter compraAdapter;

    private RecyclerView.RecyclerListener recyclerListener;

    private Button btnAdicionarCompra;

    private SQLiteDatabase database;

    DrawerLayout drawerLayoutCompra;
    ImageButton imageMenuCompra;

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

        imageMenuCompra.setOnClickListener(view12 -> drawerLayoutCompra.openDrawer(GravityCompat.START));

        compraAdapter = new CompraAdapter();

        rvCompras = view.findViewById(R.id.rvCompras);
        btnAdicionarCompra = view.findViewById(R.id.btnNovaCompra);

        RecyclerView.LayoutManager verticalLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, true);
        database = new CriarBD(view.getContext()).getReadableDatabase();
        recyclerListener = holder -> {
            compraAdapter.notifyDataSetChanged();
            getCompras();
        };

        rvCompras.setAdapter(compraAdapter);
        rvCompras.setLayoutManager(verticalLayoutManager);
        rvCompras.setRecyclerListener(recyclerListener);

        btnAdicionarCompra.setOnClickListener(view1 -> {
            Compra newCompra = new Compra();

            compraAdapter.addCompra(newCompra);
            callCarrinhoActivity();
        });

        getCompras();
        return view;
    }

    public void getCompras() {

        final Cursor cursor;

        cursor = database.query(
                "compra",
                new String[]{"_id","comp_local", "comp_titulo", "comp_data", "comp_valTot"},
                null,
                null,
                null,
                null,
                null
        );

        System.out.println(cursor.getCount());

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                System.out.println(cursor.getString(cursor.getPosition()));
                Compra newCompra = new Compra();

                newCompra.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow("comp_titulo")));
                newCompra.setLocal (cursor.getString(cursor.getColumnIndexOrThrow("comp_local" )));
                newCompra.setValorTotal(cursor.getDouble(cursor.getColumnIndexOrThrow("comp_valTot")));

                compraAdapter.addCompra(newCompra);

                System.out.println(newCompra.getData());
            }
        }
    }

    public void callCarrinhoActivity() {
        Intent it = new Intent(super.getContext(), CarrinhoActivity.class);
        startActivity(it);
    }
}