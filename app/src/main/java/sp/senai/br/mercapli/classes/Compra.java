package sp.senai.br.mercapli.classes;

import android.database.sqlite.SQLiteDatabase;

public class Compra extends Mercado {

    public Compra() {
        super("compra");
    }

    public Compra(SQLiteDatabase database, long timestamp) {
        super(database, timestamp, "compra");
    }
}

