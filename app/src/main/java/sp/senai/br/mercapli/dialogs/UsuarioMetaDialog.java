package sp.senai.br.mercapli.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import sp.senai.br.mercapli.R;

import static sp.senai.br.mercapli.Constant.META_GASTOS;

public class UsuarioMetaDialog extends DialogFragment {

    public UsuarioMetaDialog() {

    }

    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_meta, null);

        EditText etValor = (EditText) view.findViewById(R.id.etMetaValor);
        Button btCancelar = (Button) view.findViewById(R.id.btnMetaCancelar);
        Button btEnviar   = (Button) view.findViewById(R.id.btnMetaEnviar);

        btCancelar.setOnClickListener(cancelar -> this.dismiss());
        btEnviar.setOnClickListener(enviar -> {
            if(!etValor.getText().equals("") && Double.parseDouble(etValor.getText().toString()) > 0.0){
                META_GASTOS.setValor(Double.parseDouble(etValor.getText().toString()));
            }
            this.dismiss();
        });

        builder.setView(view);
        return builder.create();
    }
}
