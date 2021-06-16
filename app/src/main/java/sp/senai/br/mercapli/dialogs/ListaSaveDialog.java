package sp.senai.br.mercapli.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import sp.senai.br.mercapli.R;
import sp.senai.br.mercapli.classes.Compra;
import sp.senai.br.mercapli.classes.Lista;
import sp.senai.br.mercapli.exceptions.MercadoException;

public class ListaSaveDialog extends DialogFragment {

    private Lista lista;
    private SQLiteDatabase database;

    public ListaSaveDialog(Lista lista, SQLiteDatabase database) {
        this.lista = lista;
        this.database = database;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.finalizar_lista)
                .setPositiveButton(R.string.sim, (dialogInterface, i) -> {
                    try{
                        lista.finalizar(database);
                        getActivity().onBackPressed();
                    } catch (MercadoException e) {
                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                        this.dismiss();
                    }
                })
                .setNegativeButton(R.string.nao, ((dialogInterface, i) -> {
                    this.dismiss();
                }));
        return builder.create();
    }
}
