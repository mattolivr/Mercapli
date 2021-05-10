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
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;

import sp.senai.br.mercapli.adapters.CompraAdapter;
import sp.senai.br.mercapli.classes.Compra;
import sp.senai.br.mercapli.database.CriarBD;

public class CompraFragment extends Fragment {

    private RecyclerView rvCompras;
    private CompraAdapter compraAdapter;

    public RecyclerView.RecyclerListener recyclerListener = holder -> {
        System.out.println("Repetição");
//        getCompras();
//        atualizarGastos();
    };

    private Button btnAdicionarCompra;
    private TextView tvValorTotal;

    private SQLiteDatabase database;

    DrawerLayout drawerLayoutCompra;
    ImageButton imageMenuCompra;

    public CompraFragment() {
        // Required empty public constructor
    }

    public static CompraFragment newInstance() {
        CompraFragment fragment = new CompraFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getCompras();
        atualizarGastos();
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
        tvValorTotal = view.findViewById(R.id.tvValorTotal);

        RecyclerView.LayoutManager verticalLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, true);
        database = new CriarBD(view.getContext()).getReadableDatabase();

        rvCompras.setAdapter(compraAdapter);
        rvCompras.setLayoutManager(verticalLayoutManager);
        rvCompras.setRecyclerListener(recyclerListener);

        btnAdicionarCompra.setOnClickListener(view1 -> {
            Compra newCompra = new Compra();

            compraAdapter.addCompra(newCompra);
            callCarrinhoActivity();
        });
        getCompras();
        atualizarGastos();
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

        System.out.println("Número de Registros" + cursor.getCount());

        if(cursor.getCount() > 0){

            cursor.moveToFirst();
            compraAdapter.resetCompra();

            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                Compra newCompra = new Compra();

                newCompra.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow("comp_titulo")));
                newCompra.setLocal (cursor.getString(cursor.getColumnIndexOrThrow("comp_local" )));
                newCompra.setValorTotal(cursor.getDouble(cursor.getColumnIndexOrThrow("comp_valTot")));

                compraAdapter.addCompra(newCompra);
            }
        }
    }

    public void atualizarGastos() {
        Double dGastos = 0.0;

        if(compraAdapter.getCompras() != null){
            for(Compra compra : compraAdapter.getCompras()){
                dGastos += compra.getValorTotal();
            }
        }

        tvValorTotal.setText(NumberFormat.getCurrencyInstance().format(dGastos));
    }

    public void callCarrinhoActivity() {
        Intent it = new Intent(super.getContext(), CarrinhoActivity.class);
        startActivity(it);
    }
}