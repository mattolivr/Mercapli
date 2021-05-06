package sp.senai.br.mercapli.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CriarBD extends SQLiteOpenHelper {

    public static int VERSAO = 1;

    public CriarBD (Context context){
        super(context, "mercapli.db", null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE item(" +
                "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "comp_id_fk INTEGER NOT NULL," +
                "item_nome VARCHAR(50) NOT NULL," +
                "item_valor DOUBLE NOT NULL," +
                "item_qtde INTEGER NOT NULL," +
                "item_foto VARCHAR(100)," +
                "item_cat VARCHAR(50) NOT NULL,"+
                "FOREIGN KEY(comp_id_fk) REFERENCES compra(_id));");

        sqLiteDatabase.execSQL("CREATE TABLE compra(" +
                "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "comp_local VARCHAR(50)," +
                "comp_titulo VARCHAR(50)," +
                "comp_data LONG NOT NULL," +
                "comp_valTot DOUBLE NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS item");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS compra");

        onCreate(sqLiteDatabase);
    }
}
