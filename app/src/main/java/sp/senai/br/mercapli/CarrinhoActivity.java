package sp.senai.br.mercapli;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import sp.senai.br.mercapli.adapters.CompraAdapter;
import sp.senai.br.mercapli.classes.Item;

import static sp.senai.br.mercapli.Constant.PROD_EDIT;

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
        if(!adapter.isEditing()){
            Item newItem = new Item();
            newItem.setTypeView(PROD_EDIT);

            adapter.addProduto(newItem);
            preencherLista();
        }
        else{
            Toast.makeText(this, "Por favor, termine de inserir o produto", Toast.LENGTH_SHORT).show();
        }
    }
}