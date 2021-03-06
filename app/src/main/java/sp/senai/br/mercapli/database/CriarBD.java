package sp.senai.br.mercapli.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import sp.senai.br.mercapli.R;

public class CriarBD extends SQLiteOpenHelper {

    public static int VERSAO = 1;

    public CriarBD (Context context){
        super(context, "mercapli.db", null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE item(" +
                "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "compra_id_fk INTEGER," +
                "lista_id_fk INTEGER," +
                "item_nome VARCHAR(50) NOT NULL," +
                "item_valor DOUBLE NOT NULL," +
                "item_qtde INTEGER NOT NULL," +
                "FOREIGN KEY(compra_id_fk) REFERENCES compra(_id)," +
                "FOREIGN KEY(lista_id_fk) REFERENCES lista(_id));");

        sqLiteDatabase.execSQL("CREATE TABLE compra(" +
                "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "_local VARCHAR(50)," +
                "_titulo VARCHAR(50)," +
                "_data LONG NOT NULL," +
                "_valTot DOUBLE NOT NULL);");

        sqLiteDatabase.execSQL("CREATE TABLE lista(" +
                "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "_valTot DOUBLE NOT NULL," +
                "_data LONG NOT NULL," +
                "_local VARCHAR(50)," +
                "_titulo VARCHAR(50));");

        sqLiteDatabase.execSQL("CREATE TABLE meta(" +
                "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "meta_valor DOUBLE NOT NULL," +
                "meta_gasto DOUBLE NOT NULL," +
                "meta_cria LONG NOT NULL," +
                "meta_excl LONG NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS item");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS compra");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS lista");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS meta");

        onCreate(sqLiteDatabase);
    }

    private void insertDefaultImages(){

    }
}
