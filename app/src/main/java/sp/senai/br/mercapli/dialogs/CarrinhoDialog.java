package sp.senai.br.mercapli.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import sp.senai.br.mercapli.R;
import sp.senai.br.mercapli.classes.Compra;

public class CarrinhoDialog extends DialogFragment {

    private Compra compra;
    private SQLiteDatabase database;


    public CarrinhoDialog (Compra compra, SQLiteDatabase database) {
        this.compra = compra;
        this.database = database;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.finalizar_compra)
                .setPositiveButton(R.string.sim, (dialogInterface, i) -> {
                    compra.finalizarCompra(database);
                    System.out.println("alalalalala");
                    getActivity().onBackPressed();
                })
                .setNegativeButton(R.string.nao, ((dialogInterface, i) -> {

                }));
        return builder.create();
    }
}
