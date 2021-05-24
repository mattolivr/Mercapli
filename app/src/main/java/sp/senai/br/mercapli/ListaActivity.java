package sp.senai.br.mercapli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import sp.senai.br.mercapli.adapters.ListaAdapter;
import sp.senai.br.mercapli.classes.Lista;
import sp.senai.br.mercapli.database.CriarBD;

public class ListaActivity extends AppCompatActivity {

    private TextView tvTitulo;
    private EditText etTitulo, etLocal;
    private Button btnAddBot;
    private ImageButton ibAddTop, ibBack;
    private RecyclerView rvItens;

    private ListaAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        getSupportActionBar().hide();

        tvTitulo = findViewById(R.id.tvListaViewTitulo);
        etTitulo = findViewById(R.id.etListaViewTitulo);
        etLocal  = findViewById(R.id.etListaViewLocal );
        btnAddBot = findViewById(R.id.btnListaViewAddBot);
        ibAddTop  = findViewById(R.id.ibListaViewAdd    );
        ibBack    = findViewById(R.id.ibListaViewBack   );
        rvItens = findViewById(R.id.rvListaViewItens);

        database = new CriarBD(getApplicationContext()).getWritableDatabase();
        adapter  = new ListaAdapter();
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        rvItens.setAdapter(adapter);
        rvItens.setLayoutManager(layoutManager);

        btnAddBot.setOnClickListener(adicionar -> adicionarItem());
        ibAddTop .setOnClickListener(adicionar -> adicionarItem());
        ibBack   .setOnClickListener(voltar -> fecharLista());
    }

    private void fecharLista(){
        super.onBackPressed();
    }

    private void adicionarItem(){

    }
}