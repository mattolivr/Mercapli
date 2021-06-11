package sp.senai.br.mercapli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
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

import java.io.File;

import sp.senai.br.mercapli.adapters.CompraAdapter;
import sp.senai.br.mercapli.classes.Compra;
import sp.senai.br.mercapli.classes.Meta;
import sp.senai.br.mercapli.database.CriarBD;
import sp.senai.br.mercapli.dialogs.MetaDialog;
import sp.senai.br.mercapli.exceptions.MetaException;

import static sp.senai.br.mercapli.GlobalVariables.GASTO_TOTAL;
import static sp.senai.br.mercapli.GlobalVariables.META_GASTOS;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        // Barra de Navegação Inferior
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this,  R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        CriarBD criarBD = new CriarBD(getApplicationContext());
//        criarBD.onUpgrade(criarBD.getWritableDatabase(), 1,1);
        database = criarBD.getWritableDatabase();

        getDatabaseValues();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        refreshDatabaseValues();
    }

    private void getDatabaseValues(){
        Cursor cursorGastos;
        Double valorTotal = 0.0;
        META_GASTOS = new Meta();

        // Meta
        try {
            META_GASTOS.atualizarMeta(database);
        } catch (MetaException e) {
            DialogFragment dfNovaMeta = new MetaDialog(database);
            dfNovaMeta.show(getSupportFragmentManager(), "MetaNull");
        }

        // Gasto Total
        if(GASTO_TOTAL == 0.0){
            cursorGastos = database.query("compra", new String[] {"comp_valTot"}, null, null, null, null, null);

            for (cursorGastos.moveToFirst(); !cursorGastos.isAfterLast(); cursorGastos.moveToNext()){
                valorTotal += cursorGastos.getDouble(cursorGastos.getColumnIndexOrThrow("comp_valTot"));
            }

            GASTO_TOTAL = valorTotal;
        }
    }

    private void refreshDatabaseValues(){
        Cursor cursorGastos;
        Double valorTotal = 0.0;

        cursorGastos = database.query("compra", new String[] {"comp_valTot"}, null, null, null, null, null);

        for (cursorGastos.moveToFirst(); !cursorGastos.isAfterLast(); cursorGastos.moveToNext()){
            valorTotal += cursorGastos.getDouble(cursorGastos.getColumnIndexOrThrow("comp_valTot"));
        }

        GASTO_TOTAL = valorTotal;
    }
}