package sp.senai.br.mercapli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import sp.senai.br.mercapli.adapters.ItemAdapter;
import sp.senai.br.mercapli.classes.Item;
import sp.senai.br.mercapli.classes.Lista;
import sp.senai.br.mercapli.components.ProgressBarMeta;
import sp.senai.br.mercapli.database.CriarBD;
import sp.senai.br.mercapli.dialogs.ListaDialog;

import static sp.senai.br.mercapli.GlobalVariables.ITEM_LISTA;
import static sp.senai.br.mercapli.GlobalVariables.META_GASTOS;
import static sp.senai.br.mercapli.GlobalVariables.PROD_EDIT;

public class ListaActivity extends AppCompatActivity {

    private TextView tvTitulo;
    private EditText etTitulo, etLocal;
    private Button btnAddBot, btnFinalizar;
    private ImageButton ibAddTop, ibBack;
    private RecyclerView rvItens;
    private ProgressBarMeta pbMeta;

    private ItemAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.RecyclerListener recyclerListener;

    private SQLiteDatabase database;

    private Lista newLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        getSupportActionBar().hide();

        tvTitulo = findViewById(R.id.tvListaViewTitulo);
        etTitulo = findViewById(R.id.etListaViewTitulo);
        etLocal  = findViewById(R.id.etListaViewLocal );
        btnAddBot = findViewById(R.id.btnListaViewAddBot);
        btnFinalizar = findViewById(R.id.btnListaViewFinalizar);
        ibAddTop  = findViewById(R.id.ibListaViewAdd    );
        ibBack    = findViewById(R.id.ibListaViewBack   );
        rvItens = findViewById(R.id.rvListaViewItens);

        pbMeta = new ProgressBarMeta(findViewById(R.id.pbCarrinhoMeta));
        database = new CriarBD(getApplicationContext()).getWritableDatabase();
        adapter  = new ItemAdapter(this, this.getSupportFragmentManager(), ITEM_LISTA);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        newLista = new Lista();

        recyclerListener = holder -> {
            atualizarProgressoMeta();
        };

        rvItens.setAdapter(adapter);
        rvItens.setLayoutManager(layoutManager);
        rvItens.setRecyclerListener(recyclerListener);

        btnAddBot.setOnClickListener(adicionar -> adicionarItem());
        ibAddTop .setOnClickListener(adicionar -> adicionarItem());
        btnFinalizar.setOnClickListener(finalizar -> finalizarLista());
        ibBack   .setOnClickListener(voltar -> fecharLista());

        atualizarProgressoMeta();
    }

    private void fecharLista(){
        super.onBackPressed();
    }

    private void adicionarItem(){
        if(!adapter.isEditing()){
            Item newItem = new Item();
            newItem.setTypeView(PROD_EDIT);

            adapter.addProduto(newItem);
            adapter.notifyDataSetChanged();
        }
        else{
            Toast.makeText(this, "Por favor, termine de inserir o item", Toast.LENGTH_SHORT).show();
        }
    }

    private void finalizarLista(){
        newLista.setValorTotal(adapter.getValorTotal());
        newLista.setItens(adapter.getProdutos());
        newLista.setTitulo(etTitulo.getText().toString());
        newLista.setLocal(etLocal.getText().toString());

        DialogFragment dialogFragment = new ListaDialog(newLista, database);
        dialogFragment.show(getSupportFragmentManager(), "lista");
    }

    private void atualizarProgressoMeta(){
        int valorRestante = META_GASTOS.getValorRestantePorcentagem();
        pbMeta.atualizar(valorRestante);
    }
}