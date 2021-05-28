package sp.senai.br.mercapli.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import sp.senai.br.mercapli.R;

public class FotoProdutoDialog extends DialogFragment {

    public FotoProdutoDialog(){}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Escolha a foto do Produto")
                .setPositiveButton(R.string.sim, (dialogInterface, i) -> {

                })
                .setNegativeButton(R.string.nao, ((dialogInterface, i) -> {
                    this.dismiss();
                }));
        return builder.create();
    }

}
