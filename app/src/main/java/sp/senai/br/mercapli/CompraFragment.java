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
import android.widget.Toast;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.text.NumberFormat;

import sp.senai.br.mercapli.adapters.CompraAdapter;
import sp.senai.br.mercapli.classes.Compra;
import sp.senai.br.mercapli.database.CriarBD;

import static sp.senai.br.mercapli.GlobalVariables.GASTO_TOTAL;

public class CompraFragment extends Fragment {

    private RecyclerView rvCompras;
    private CompraAdapter compraAdapter;
    private LinearLayoutManager compraLayoutManager;

    private Button btnAdicionarCompra;
    private TextView tvValorTotal;

    private SQLiteDatabase database;

    private DrawerLayout drawerLayoutCompra;
    private ImageButton imageMenuCompra;
    private NavigationView navigationViewCompra;
    private NavController navControllerCompra;

    public CompraFragment() {}

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
        getCompras();
        atualizarGastos();
        super.onResume();
        // TODO: scroll para o topo
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compra, container, false);

        rvCompras = view.findViewById(R.id.rvCompraItens);

        btnAdicionarCompra = view.findViewById(R.id.btnCompraAdd);
        tvValorTotal = view.findViewById(R.id.tvCompraValorTotal);

        drawerLayoutCompra = view.findViewById(R.id.drawerLayoutCompra);
        imageMenuCompra = view.findViewById(R.id.ibCompraMenu);
        navigationViewCompra = view.findViewById(R.id.navigationViewCompra);
        navControllerCompra = Navigation.findNavController(getActivity(), R.id.fragment);

        compraAdapter = new CompraAdapter(this.getContext());
        compraLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, true);
        database = new CriarBD(view.getContext()).getReadableDatabase();

        rvCompras.setAdapter(compraAdapter);
        rvCompras.setLayoutManager(compraLayoutManager);

        NavigationUI.setupWithNavController(navigationViewCompra, navControllerCompra);

        btnAdicionarCompra.setOnClickListener(view1 -> adicionarCompra());
        imageMenuCompra.setOnClickListener(view12 -> drawerLayoutCompra.openDrawer(GravityCompat.START));

        getCompras();
        atualizarGastos();
        return view;
    }

    public void adicionarCompra() {
        callCarrinhoActivity();
    }

    public void getCompras() {

        final Cursor cursor;

        cursor = database.query("compra", null,null, null,null,null,null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            compraAdapter.resetCompra();

            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                Compra newCompra = new Compra();

                newCompra.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow("_titulo")));
                newCompra.setLocal (cursor.getString(cursor.getColumnIndexOrThrow("_local" )));
                newCompra.setValorTotal(cursor.getDouble(cursor.getColumnIndexOrThrow("_valTot")));
                newCompra.setData(cursor.getLong(cursor.getColumnIndexOrThrow("_data")));

                compraAdapter.addCompra(newCompra);
            }
        }
        compraAdapter.notifyDataSetChanged();
    }

    public void atualizarGastos() {
        tvValorTotal.setText(NumberFormat.getCurrencyInstance().format(GASTO_TOTAL));
    }

    public void callCarrinhoActivity() {
        Intent it = new Intent(super.getContext(), CarrinhoActivity.class);
        it.putExtra("newParam", true);
        startActivity(it);
    }
}