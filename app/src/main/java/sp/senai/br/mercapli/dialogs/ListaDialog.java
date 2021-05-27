package sp.senai.br.mercapli.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import sp.senai.br.mercapli.R;
import sp.senai.br.mercapli.classes.Compra;
import sp.senai.br.mercapli.classes.Lista;

public class ListaDialog extends DialogFragment {

    private Lista lista;
    private SQLiteDatabase database;

    public ListaDialog (Lista lista, SQLiteDatabase database) {
        this.lista = lista;
        this.database = database;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.finalizar_lista)
                .setPositiveButton(R.string.sim, (dialogInterface, i) -> {
                    lista.finalizarLista(database);
                    getActivity().onBackPressed();
                })
                .setNegativeButton(R.string.nao, ((dialogInterface, i) -> {
                    this.dismiss();
                }));
        return builder.create();
    }
}
