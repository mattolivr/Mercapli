package sp.senai.br.mercapli.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import sp.senai.br.mercapli.R;
import sp.senai.br.mercapli.classes.Compra;

public class CarrinhoBackDialog extends DialogFragment {

    public CarrinhoBackDialog() {}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.voltar_compra).setMessage(R.string.voltar_compra_desc)
                .setPositiveButton(R.string.sim, (dialogInterface, i) -> {
                    getActivity().onBackPressed();
                })
                .setNegativeButton(R.string.nao, ((dialogInterface, i) -> {
                    this.dismiss();
                }));
        return builder.create();
    }
}
