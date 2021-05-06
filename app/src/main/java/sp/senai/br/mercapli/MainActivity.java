package sp.senai.br.mercapli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import sp.senai.br.mercapli.adapters.CompraAdapter;
import sp.senai.br.mercapli.classes.Compra;
import sp.senai.br.mercapli.database.CriarBD;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvCompras;
    private RecyclerView rvListas;

    private CompraAdapter compraAdapter;

    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        database = new CriarBD(getApplicationContext()).getWritableDatabase();

        // Barra de Navegação Inferior
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this,  R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        // Dashboard

        // Compras

        compraAdapter = new CompraAdapter();

        RecyclerView.LayoutManager verticalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);

        if(rvCompras != null){
            rvCompras.setAdapter(compraAdapter);
            rvCompras.setLayoutManager(verticalLayoutManager);

            getCompras();
        }
    }

    public void adicionarCompra(View v){
//        Compra newCompra = new Compra();

//        compraAdapter.addCompra(newCompra);
//        callCompraActivity();

        System.out.println(rvCompras);
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
        database.close();

        if(cursor != null){
            for(cursor.moveToFirst(); cursor.isAfterLast(); cursor.moveToNext()){
                Compra newCompra = new Compra();

                newCompra.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow("comp_titulo")));
                newCompra.setLocal (cursor.getString(cursor.getColumnIndexOrThrow("comp_local" )));
                newCompra.setValorTotal(cursor.getDouble(cursor.getColumnIndexOrThrow("comp_valTot")));

                compraAdapter.addCompra(newCompra);
            }
        }
    }

    public void callCompraActivity() {
        Intent it = new Intent(MainActivity.this, CarrinhoActivity.class);
        startActivity(it);
    }
}