package sp.senai.br.mercapli.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import sp.senai.br.mercapli.R;
import sp.senai.br.mercapli.classes.Compra;
import sp.senai.br.mercapli.exceptions.CompraException;

public class CarrinhoDeleteDialog extends DialogFragment {

    private Compra compra;
    private SQLiteDatabase database;
    private Boolean isNew;

    public CarrinhoDeleteDialog (Compra compra, SQLiteDatabase database, boolean isNew) {
        this.compra = compra;
        this.database = database;
        this.isNew = isNew;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.deletar_compra)
                .setPositiveButton(R.string.sim, ((dialogInterface, i) -> {
                    try{
                        if(!isNew) { compra.deletarCompra(database); }
                        getActivity().onBackPressed();
                    } catch (CompraException e){
                        System.out.println(e.toString());
                    }
                }))
                .setNegativeButton(R.string.nao, ((dialogInterface, i) -> this.dismiss()));
        return builder.create();
    }
}
