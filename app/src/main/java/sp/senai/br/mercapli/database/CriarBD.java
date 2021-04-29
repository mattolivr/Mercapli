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
                "item_nome VARCHAR(50) NOT NULL," +
                "item_valor FLOAT NOT NULL," +
                "item_qtde INTEGER NOT NULL," +
                "item_foto VARCHAR(100)," +
                "item_cat VARCHAR(50) NOT NULL);");

        sqLiteDatabase.execSQL("CREATE TABLE compra(" +
                "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "comp_local VARCHAR(50)," +
                "comp_data INTEGER NOT NULL," +
                "comp_valTot FLOAT DEFAULT 0);");

        sqLiteDatabase.execSQL("CREATE TABLE comp_itens(" +
                "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "comp_id_fk INTEGER NOT NULL," +
                "item_id_fk INTEGER NOT NULL," +
                "FOREIGN KEY(comp_id_fk) REFERENCES compra(_id)," +
                "FOREIGN KEY(item_id_fk) REFERENCES item(_id));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS item");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS compra");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS comp_itens");

        onCreate(sqLiteDatabase);
    }
}
