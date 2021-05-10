package sp.senai.br.mercapli;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;

import sp.senai.br.mercapli.adapters.CarrinhoAdapter;
import sp.senai.br.mercapli.classes.Compra;
import sp.senai.br.mercapli.classes.Item;
import sp.senai.br.mercapli.database.CriarBD;
import sp.senai.br.mercapli.dialogs.CarrinhoDialog;

import static sp.senai.br.mercapli.Constant.PROD_EDIT;

public class CarrinhoActivity extends AppCompatActivity {

    private TextView tvValorTotal;
    private RecyclerView rvCompraProdutos;

    private CarrinhoAdapter adapter = new CarrinhoAdapter(this);
    private RecyclerView.RecyclerListener recyclerListener = holder -> {
        if(tvValorTotal != null){
            atualizarValorTotal();
        }
    };

    private Compra newCompra = new Compra();
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);
        getSupportActionBar().hide();

        rvCompraProdutos = findViewById(R.id.rvCompraProdutos);
        tvValorTotal     = findViewById(R.id.tvValorTotal    );
        database         = new CriarBD(getApplicationContext()).getWritableDatabase();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);

        tvValorTotal.setText(NumberFormat.getCurrencyInstance().format(0));

        rvCompraProdutos.setAdapter(adapter);
        rvCompraProdutos.setLayoutManager(layoutManager);
        rvCompraProdutos.setRecyclerListener(recyclerListener);
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

    public void atualizarValorTotal () {
        tvValorTotal.setText(
                NumberFormat.getCurrencyInstance().format(adapter.getValorTotal())
        );
    }

    public void finalizarCompra (View view) {
        newCompra.setValorTotal(adapter.getValorTotal());
        newCompra.setItems(adapter.getProdutos());

        DialogFragment dffinalizarCompra = new CarrinhoDialog(newCompra, database);
        dffinalizarCompra.show(getSupportFragmentManager(), "carrinho");
    }
}