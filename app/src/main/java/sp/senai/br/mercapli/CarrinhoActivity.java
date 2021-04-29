package sp.senai.br.mercapli;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

import sp.senai.br.mercapli.database.CriarBD;

public class CarrinhoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);
        getSupportActionBar().hide();
    }

    public void closeActivity(){
        super.onBackPressed();
    }

    public void preencherLista(){
//        final Cursor cursor;
//        String[] camposBD = {
//                "_id",
//                "item_nome",
//                "item_valor",
//                "item_qtde",
//                "item_foto",
//                "item_cat"
//        };
//        int[] camposLayout = {
//                R.id.id_produto_layout ,
//                R.id.nome_produto_layout,
//                R.id.preco_produto_layout,
//                R.id.qtde_produto_layout,
//                R.id.foto_produto_layout,
//                R.id.cat_produto_layout
//        };
//
//        SQLiteDatabase db = new CriarBD(getApplicationContext()).getReadableDatabase();
//
//        cursorQuery = db.query("item", camposBD, null, null, null, null, null);

    }

    public void adicionarProduto(){
        final Cursor cursor;
        String[] camposBD = {
                "_id",
                "item_nome",
                "item_valor",
                "item_qtde",
                "item_foto",
                "item_cat"
        };

        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.layout_produto, )

    }
}