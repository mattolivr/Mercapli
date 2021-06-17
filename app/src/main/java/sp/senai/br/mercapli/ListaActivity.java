package sp.senai.br.mercapli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import sp.senai.br.mercapli.adapters.ItemAdapter;
import sp.senai.br.mercapli.classes.Item;
import sp.senai.br.mercapli.classes.Lista;
import sp.senai.br.mercapli.components.ProgressBarMeta;
import sp.senai.br.mercapli.database.CriarBD;
import sp.senai.br.mercapli.dialogs.BackDialog;
import sp.senai.br.mercapli.dialogs.ListaDeleteDialog;
import sp.senai.br.mercapli.dialogs.ListaSaveDialog;

import static sp.senai.br.mercapli.GlobalVariables.ITEM_LISTA;
import static sp.senai.br.mercapli.GlobalVariables.META_GASTOS;
import static sp.senai.br.mercapli.GlobalVariables.PROD_EDIT;
import static sp.senai.br.mercapli.GlobalVariables.PROD_VIEW;

public class ListaActivity extends AppCompatActivity {

    private Boolean isNew;

    private TextView tvTitulo;
    private EditText etTitulo, etLocal;
    private Button btnAddBot, btnFinalizar;
    private ImageButton ibDelete, ibBack;
    private RecyclerView rvItens;
    private ProgressBarMeta pbMeta;

    private ItemAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.RecyclerListener recyclerListener;

    private Lista newLista;
    private long listaData;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        getSupportActionBar().hide();

        isNew = getIntent().getBooleanExtra("newParam", true);
        listaData = getIntent().getLongExtra("listaData", 0);

        tvTitulo     = findViewById(R.id.tvListaViewTitulo);
        etTitulo     = findViewById(R.id.etListaViewTitulo);
        etLocal      = findViewById(R.id.etListaViewLocal );
        btnAddBot    = findViewById(R.id.btnListaViewAddBot);
        btnFinalizar = findViewById(R.id.btnListaViewFinalizar);
        ibDelete     = findViewById(R.id.ibListaViewAdd    );
        ibBack       = findViewById(R.id.ibListaViewBack   );
        rvItens      = findViewById(R.id.rvListaViewItens);

        pbMeta         = new ProgressBarMeta(findViewById(R.id.pbListaViewMeta));
        database       = new CriarBD(getApplicationContext()).getWritableDatabase();
        adapter        = new ItemAdapter(this, this.getSupportFragmentManager(), ITEM_LISTA);
        layoutManager  = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);

        recyclerListener = holder -> atualizarProgressoMeta();

        rvItens.setAdapter(adapter);
        rvItens.setLayoutManager(layoutManager);
        rvItens.setRecyclerListener(recyclerListener);

        btnAddBot   .setOnClickListener(adicionar -> adicionarItem());
        ibDelete    .setOnClickListener(deletar   -> deletarLista());
        btnFinalizar.setOnClickListener(finalizar -> finalizarLista());
        ibBack      .setOnClickListener(voltar    -> cancelarLista());

        if(isNew){
            newLista = new Lista();

            etTitulo.setText("");
            etLocal.setText("");
        } else {
            if(listaData > 0){
                newLista = new Lista(database, listaData);

                etTitulo.setText(newLista.getTitulo());
                etLocal .setText(newLista.getTitulo());

                btnFinalizar.setText("FINALIZAR ALTERAÇÕES");

                for(Item item: newLista.getItens()) {
                    item.setTypeView(PROD_VIEW);
                    adapter.addProduto(item);
                }
            } else {
                super.onBackPressed();
                Toast.makeText(super.getApplicationContext(), "Algo deu Errado!", Toast.LENGTH_SHORT).show();
            }
        }
        atualizarProgressoMeta();
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

        if(isNew){
            DialogFragment dialogFragment = new ListaSaveDialog(newLista, database);
            dialogFragment.show(getSupportFragmentManager(), "lista");
        } else {
            newLista.atualizar(database);
            super.onBackPressed();
        }
    }

    private void cancelarLista(){
        adapter.resetGastoLocal();
        if(isNew){
            super.onBackPressed();
        } else {
            DialogFragment dfcancelarLista = new BackDialog();
            dfcancelarLista.show(getSupportFragmentManager(), "carrinhoVoltar");
        }
    }

    private void deletarLista(){
        DialogFragment dfDeletarLista = new ListaDeleteDialog(newLista, database);
        dfDeletarLista.show(getSupportFragmentManager(), "listaDeletar");
    }

    private void atualizarProgressoMeta(){
        int valorRestante = META_GASTOS.getValorRestantePorcentagem();
        pbMeta.atualizar(valorRestante);
    }
}