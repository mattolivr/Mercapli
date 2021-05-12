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

import static sp.senai.br.mercapli.Constant.GASTO_TOTAL;

public class CompraFragment extends Fragment {

    private RecyclerView rvCompras;
    private CompraAdapter compraAdapter;
    private LinearLayoutManager compraLayoutManager;

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
        imageMenuCompra = view.findViewById(R.id.ibCompraMenu);

        imageMenuCompra.setOnClickListener(view12 -> drawerLayoutCompra.openDrawer(GravityCompat.START));

        rvCompras = view.findViewById(R.id.rvCompraItens);
        btnAdicionarCompra = view.findViewById(R.id.btnCompraAdd);
        tvValorTotal = view.findViewById(R.id.tvCompraValorTotal);

        compraLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, true);
        RecyclerView.LayoutManager verticalLayoutManager = compraLayoutManager;
        database = new CriarBD(view.getContext()).getReadableDatabase();

        compraAdapter = new CompraAdapter(database);

        rvCompras.setAdapter(compraAdapter);
        rvCompras.setLayoutManager(verticalLayoutManager);

        btnAdicionarCompra.setOnClickListener(view1 -> adicionarCompra());

        getCompras();
        atualizarGastos();
        return view;
    }

    public void adicionarCompra() {
        Compra newCompra = new Compra();

        compraAdapter.addCompra(newCompra);
        callCarrinhoActivity();
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
                newCompra.setData(cursor.getLong(cursor.getColumnIndexOrThrow("comp_data")));

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

        GASTO_TOTAL = dGastos;
        tvValorTotal.setText(NumberFormat.getCurrencyInstance().format(dGastos));
    }

    public void callCarrinhoActivity() {
        Intent it = new Intent(super.getContext(), CarrinhoActivity.class);
        startActivity(it);
    }
}