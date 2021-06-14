package sp.senai.br.mercapli.classes;

import android.database.sqlite.SQLiteDatabase;

public class Lista extends Mercado {

    public Lista() {
        super("lista");
    }

    public Lista(SQLiteDatabase database, long timestamp) {
        super(database, timestamp, "lista");
    }
}
