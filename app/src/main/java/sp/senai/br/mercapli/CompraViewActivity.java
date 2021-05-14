package sp.senai.br.mercapli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

import sp.senai.br.mercapli.classes.Compra;
import sp.senai.br.mercapli.classes.Item;
import sp.senai.br.mercapli.database.CriarBD;

public class CompraViewActivity extends AppCompatActivity {

    private int compraID;
    private SQLiteDatabase database;
    private Compra compra;

    TextView tvValorTotal, tvTitulo, tvLocal;
    ImageButton btnCloseActivity;
    RecyclerView rvItens;
    LinearLayout llDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra_view);
        getSupportActionBar().hide();

        tvValorTotal = findViewById(R.id.tvCompraViewValorTotal);
        tvTitulo     = findViewById(R.id.tvCompraViewTitulo);
        tvLocal      = findViewById(R.id.tvCompraViewLocal);
        btnCloseActivity = findViewById(R.id.ibCompraViewVoltar);
        rvItens = findViewById(R.id.rvCompraViewProdutos);
        llDados = findViewById(R.id.llCompraViewDados);

        database = new CriarBD(this.getApplicationContext()).getWritableDatabase();

        compraID = getIntent().getIntExtra("ItemID", 1);

        compra = new Compra(database, compraID);

        btnCloseActivity.setOnClickListener(this::closeActivity);

        preencherTela();
    }

    private void closeActivity(View view) {
        super.finish();
    }

    private void preencherTela(){
        tvValorTotal.setText(DecimalFormat.getCurrencyInstance().format(compra.getValorTotal()));

        if(compra.getTitulo() != "" || compra.getLocal() != ""){
            tvTitulo.setText(compra.getTitulo());
            tvLocal.setText(compra.getLocal());
        } else {
            llDados.setVisibility(View.INVISIBLE);
        }
    }
    
    private void preencherRecyclerView() {
        for (Item item: compra.getItens()) {
            // TODO: Atualizar CVAdapter e CVViewHolder
        }
    }
}