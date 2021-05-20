package sp.senai.br.mercapli;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import sp.senai.br.mercapli.dialogs.CarrinhoBackDialog;
import sp.senai.br.mercapli.dialogs.CarrinhoDialog;

import static sp.senai.br.mercapli.Constant.PROD_EDIT;
import static sp.senai.br.mercapli.Constant.PROD_VIEW;

public class CarrinhoActivity extends AppCompatActivity {

    private Boolean isNew;

    private TextView tvValorTotal;
    private EditText etTitulo, etLocal;
    private RecyclerView rvCompraProdutos;
    private ImageButton ibBack;
    private Button btFinalizar, btAdicionar;

    private CarrinhoAdapter adapter;
    private RecyclerView.RecyclerListener recyclerListener;
    private RecyclerView.LayoutManager layoutManager;

    private Compra newCompra;
    private long compraData;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);
        getSupportActionBar().hide();

        isNew = getIntent().getBooleanExtra("newParam", true);
        compraData = getIntent().getLongExtra("compraData", 0);

        tvValorTotal     = findViewById(R.id.tvCarrinhoValorTotal);
        etTitulo         = findViewById(R.id.etCarrinhoTitulo);
        etLocal          = findViewById(R.id.etCarrinhoLocal);
        rvCompraProdutos = findViewById(R.id.rvCarrinhoProdutos);
        ibBack           = findViewById(R.id.ibCarrinhoBack);
        btFinalizar      = findViewById(R.id.btnCarrinhoFinalizar);
        btAdicionar      = findViewById(R.id.btnCarrinhoAdicionar);

        database = new CriarBD(getApplicationContext()).getWritableDatabase();
        adapter  = new CarrinhoAdapter(this);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);

        recyclerListener = holder -> {
            if(tvValorTotal != null){
                atualizarValorTotal();
            }
        };

        // TODO: Diálogo -> descartar alterações
        ibBack     .setOnClickListener(back -> cancelarCompra());
        btFinalizar.setOnClickListener(view -> finalizarCompra());
        btAdicionar.setOnClickListener(view -> adicionarProduto());

        if(isNew){
            // Vizualização Nova
            newCompra = new Compra();

            tvValorTotal.setText(NumberFormat.getCurrencyInstance().format(0));
            etTitulo.setText("");
            etLocal.setText("");

        }else{
            // Vizualização reciclada
            if (compraData > 0){
                newCompra = new Compra(database, compraData);

                etTitulo.setText(newCompra.getTitulo());
                etLocal.setText(newCompra.getLocal());

                btFinalizar.setText("FINALIZAR ALTERAÇÕES");

                for (Item item: newCompra.getItens()) {
                    item.setTypeView(PROD_VIEW);
                    adapter.addProduto(item);
                }
                newCompra.testarItens();
                tvValorTotal.setText(NumberFormat.getCurrencyInstance().format(newCompra.getValorTotal()));
                // TODO: opções de edição?
            } else {
                super.onBackPressed();
                Toast.makeText(super.getApplicationContext(), "Algo deu Errado!", Toast.LENGTH_SHORT).show();
            }
        }

        rvCompraProdutos.setAdapter(adapter);
        rvCompraProdutos.setLayoutManager(layoutManager);
        rvCompraProdutos.setRecyclerListener(recyclerListener);
    }

    private void adicionarProduto() {
        if(!adapter.isEditing()){
            Item newItem = new Item();
            newItem.setTypeView(PROD_EDIT);

            adapter.addProduto(newItem);
            adapter.notifyDataSetChanged();
        }
        else{
            Toast.makeText(this, "Por favor, termine de inserir o produto", Toast.LENGTH_SHORT).show();
        }
    }

    private void atualizarValorTotal () {
        tvValorTotal.setText(
                NumberFormat.getCurrencyInstance().format(adapter.getValorTotal())
        );
    }

    private void finalizarCompra () {
        newCompra.setValorTotal(adapter.getValorTotal());
        newCompra.setItens(adapter.getProdutos());
        newCompra.setTitulo(etTitulo.getText().toString());
        newCompra.setLocal(etLocal.getText().toString());

        if(isNew){
            // Finalizar compra nova
            DialogFragment dffinalizarCompra = new CarrinhoDialog(newCompra, database);
            dffinalizarCompra.show(getSupportFragmentManager(), "carrinho");
        } else {
            // Finalizar alterações
            newCompra.atualizarCompra(database);
            super.onBackPressed();
        }
    }

    private void cancelarCompra() {
        if(isNew){

        } else {
            DialogFragment dfcancelarCompra = new CarrinhoBackDialog();
            dfcancelarCompra.show(getSupportFragmentManager(), "carrinhoVoltar");
        }
    }
}