package sp.senai.br.mercapli.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import sp.senai.br.mercapli.R;
import sp.senai.br.mercapli.classes.Meta;
import sp.senai.br.mercapli.exceptions.MetaException;
import sp.senai.br.mercapli.exceptions.MetaInputException;

import static sp.senai.br.mercapli.GlobalVariables.META_GASTOS;

public class MetaDialog extends DialogFragment {

    private SQLiteDatabase database;

    public MetaDialog(SQLiteDatabase database) {
        this.database = database;
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

            META_GASTOS.salvarMeta(database);

            try {
                META_GASTOS = new Meta(Double.parseDouble(etValor.getText().toString()));
                META_GASTOS.salvarMeta(database);
                META_GASTOS.finalizarMetaAnterior(database);
                this.dismiss();
            } catch (MetaInputException e){
                Toast.makeText(super.getContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        });

        builder.setView(view);
        return builder.create();
    }
}
