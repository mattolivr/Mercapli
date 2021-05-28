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
import sp.senai.br.mercapli.database.CriarBD;
import sp.senai.br.mercapli.dialogs.ListaDialog;

import static sp.senai.br.mercapli.Constant.PROD_EDIT;

public class ListaActivity extends AppCompatActivity {

    private TextView tvTitulo;
    private EditText etTitulo, etLocal;
    private Button btnAddBot, btnFinalizar;
    private ImageButton ibAddTop, ibBack;
    private RecyclerView rvItens;

    private ItemAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

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

        database = new CriarBD(getApplicationContext()).getWritableDatabase();
        adapter  = new ItemAdapter(this, this.getSupportFragmentManager());
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        newLista = new Lista();

        rvItens.setAdapter(adapter);
        rvItens.setLayoutManager(layoutManager);

        btnAddBot.setOnClickListener(adicionar -> adicionarItem());
        ibAddTop .setOnClickListener(adicionar -> adicionarItem());
        btnFinalizar.setOnClickListener(finalizar -> finalizarLista());
        ibBack   .setOnClickListener(voltar -> fecharLista());
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
}