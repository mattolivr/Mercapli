package sp.senai.br.mercapli.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;

import sp.senai.br.mercapli.R;

public class FotoProdutoDialog extends DialogFragment {

    public FotoProdutoDialog(){}
    AlertDialog alerta;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Escolha a foto do Produto")
                .setNegativeButton("Sair", ((dialogInterface, i) -> {
                    this.dismiss();
                }));
        LayoutInflater atual = getLayoutInflater();
        View visual = atual.inflate(R.layout.alert_dialog_fotos, null);
        builder.setView(visual);
        return builder.create();
    }

}
