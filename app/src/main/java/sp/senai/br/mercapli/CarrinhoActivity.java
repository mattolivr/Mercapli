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

import sp.senai.br.mercapli.adapters.ItemAdapter;
import sp.senai.br.mercapli.classes.Compra;
import sp.senai.br.mercapli.classes.Item;
import sp.senai.br.mercapli.components.ProgressBarMeta;
import sp.senai.br.mercapli.database.CriarBD;
import sp.senai.br.mercapli.dialogs.BackDialog;
import sp.senai.br.mercapli.dialogs.CarrinhoDeleteDialog;
import sp.senai.br.mercapli.dialogs.CarrinhoSaveDialog;
import sp.senai.br.mercapli.exceptions.MetaException;

import static sp.senai.br.mercapli.GlobalVariables.ITEM_CARRINHO;
import static sp.senai.br.mercapli.GlobalVariables.META_GASTOS;
import static sp.senai.br.mercapli.GlobalVariables.PROD_EDIT;
import static sp.senai.br.mercapli.GlobalVariables.PROD_VIEW;

public class CarrinhoActivity extends AppCompatActivity {

    private Boolean isNew;

    private TextView tvValorTotal;
    private EditText etTitulo, etLocal;
    private RecyclerView rvCompraProdutos;
    private ImageButton ibBack, ibExcluir;
    private Button btFinalizar, btAdicionar;
    private ProgressBarMeta pbMeta;

    private ItemAdapter adapter;
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

        isNew       = getIntent().getBooleanExtra("newParam", true);
        compraData  = getIntent().getLongExtra("compraData", 0);

        tvValorTotal     = findViewById(R.id.tvCarrinhoValorTotal);
        etTitulo         = findViewById(R.id.etCarrinhoTitulo);
        etLocal          = findViewById(R.id.etCarrinhoLocal);
        rvCompraProdutos = findViewById(R.id.rvCarrinhoProdutos);
        ibBack           = findViewById(R.id.ibCarrinhoBack);
        ibExcluir        = findViewById(R.id.ibCarrinhoDeleteCompra);
        btFinalizar      = findViewById(R.id.btnCarrinhoFinalizar);
        btAdicionar      = findViewById(R.id.btnCarrinhoAdicionar);

        pbMeta          = new ProgressBarMeta(findViewById(R.id.pbCarrinhoMeta));
        database        = new CriarBD(getApplicationContext()).getWritableDatabase();
        adapter         = new ItemAdapter(this, this.getSupportFragmentManager(), ITEM_CARRINHO);
        layoutManager   = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);

        recyclerListener = holder -> {
            if(tvValorTotal != null){
                atualizarValorTotal();
                atualizarProgressoMeta();
                verificarMeta();
            }
        };

        ibBack     .setOnClickListener(back -> cancelarCompra());
        ibExcluir  .setOnClickListener(excl -> deletarCompra());
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
                etLocal .setText(newCompra.getLocal());

                btFinalizar.setText("FINALIZAR ALTERAÇÕES");

                for (Item item: newCompra.getItens()) {
                    item.setTypeView(PROD_VIEW);
                    adapter.addProduto(item);
                }

//                GASTO_TOTAL -= adapter.getProdutosSum();
//                GASTO_LOCAL =  adapter.getProdutosSum();

                tvValorTotal.setText(NumberFormat.getCurrencyInstance().format(newCompra.getValorTotal()));
            } else {
                super.onBackPressed();
                Toast.makeText(super.getApplicationContext(), "Algo deu Errado!", Toast.LENGTH_SHORT).show();
            }
        }

        atualizarProgressoMeta();

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
        adapter.resetGastoLocal();

        if(isNew){
            // Finalizar compra nova
            DialogFragment dffinalizarCompra = new CarrinhoSaveDialog(newCompra, database);
            dffinalizarCompra.show(getSupportFragmentManager(), "carrinho");
        } else {
            // Finalizar alterações
            newCompra.atualizar(database);
            super.onBackPressed();
        }
    }

    private void cancelarCompra() {
        adapter.resetGastoLocal();
        if(isNew){
            super.onBackPressed();
        } else {
            DialogFragment dfcancelarCompra = new BackDialog();
            dfcancelarCompra.show(getSupportFragmentManager(), "carrinhoVoltar");
        }
    }

    private void deletarCompra() {
        DialogFragment dfExcluirCompra = new CarrinhoDeleteDialog(newCompra, database, isNew);
        dfExcluirCompra.show(getSupportFragmentManager(), "carrinhoExcluir");
    }
    
    private void verificarMeta(){
        try{
            if(adapter.getValorTotal() < META_GASTOS.getValor()){
                if(adapter.getValorTotal() > META_GASTOS.getValor() - (META_GASTOS.getValor() * 0.3)){
                    throw new MetaException("Você está quase excedendo sua meta de gastos!");
                }
            } else {
                throw new MetaException("Sua compra excede sua meta de gastos!\nTente remover alguns itens do carrinho");
            }
        } catch (MetaException e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void atualizarProgressoMeta(){
        int valorRestante = META_GASTOS.getValorRestantePorcentagem();
        pbMeta.atualizar(valorRestante);
    }
}