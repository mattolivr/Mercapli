package sp.senai.br.mercapli.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import sp.senai.br.mercapli.R;
import sp.senai.br.mercapli.classes.Lista;
import sp.senai.br.mercapli.exceptions.ListaException;
import sp.senai.br.mercapli.exceptions.MercadoException;

public class ListaDeleteDialog extends DialogFragment {

    private Lista lista;
    private SQLiteDatabase database;
    private Boolean isNew;

    public ListaDeleteDialog (Lista lista, SQLiteDatabase database) {
        this.lista = lista;
        this.database = database;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.deletar_compra)
                .setPositiveButton(R.string.sim, ((dialogInterface, i) -> {
                    try{
                        lista.deletar(database);
                        getActivity().onBackPressed();
                    } catch (MercadoException e){
                        System.out.println(e.toString());
                    }
                }))
                .setNegativeButton(R.string.nao, ((dialogInterface, i) -> this.dismiss()));
        return builder.create();
    }
}
