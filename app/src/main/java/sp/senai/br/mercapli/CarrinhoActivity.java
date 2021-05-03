package sp.senai.br.mercapli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.List;

import sp.senai.br.mercapli.adapters.CompraAdapter;
import sp.senai.br.mercapli.classes.Item;
import sp.senai.br.mercapli.database.CriarBD;

import static sp.senai.br.mercapli.Constant.PROD_EDIT;
import static sp.senai.br.mercapli.Constant.PROD_VIEW;

public class CarrinhoActivity extends AppCompatActivity {
    RecyclerView rvCompraProdutos;
    CompraAdapter adapter = new CompraAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);
        getSupportActionBar().hide();

        rvCompraProdutos = findViewById(R.id.rvCompraProdutos);

        rvCompraProdutos.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        rvCompraProdutos.setLayoutManager(layoutManager);
    }

    public void closeActivity(){
        super.onBackPressed();
    }

    public void preencherLista(){
        adapter.notifyDataSetChanged();
    }

    public void adicionarProduto(View view) {
        Item newItem = new Item();
        newItem.setTypeView(PROD_VIEW);

        adapter.addProduto(newItem);
        preencherLista();
    }
}