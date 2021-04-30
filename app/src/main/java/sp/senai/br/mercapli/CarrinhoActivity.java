package sp.senai.br.mercapli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.List;

import sp.senai.br.mercapli.adapters.CompraAdapter;
import sp.senai.br.mercapli.classes.Item;
import sp.senai.br.mercapli.database.CriarBD;

public class CarrinhoActivity extends AppCompatActivity {
    RecyclerView rvCompraProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);
        getSupportActionBar().hide();

        rvCompraProdutos = findViewById(R.id.rvCompraProdutos);
        List<Item> Produtos = null;

        rvCompraProdutos.setAdapter(new CompraAdapter(Produtos, this));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        rvCompraProdutos.setLayoutManager(layoutManager);
    }

    public void closeActivity(){
        super.onBackPressed();
    }

    public void preencherLista(){}

    public void adicionarProduto() {
        
    }
}