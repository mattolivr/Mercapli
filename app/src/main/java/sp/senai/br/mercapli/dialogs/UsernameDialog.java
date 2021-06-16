package sp.senai.br.mercapli.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import sp.senai.br.mercapli.R;
import sp.senai.br.mercapli.exceptions.UsernameException;

import static sp.senai.br.mercapli.GlobalVariables.USER_NAME;

public class UsernameDialog extends DialogFragment {

    public UsernameDialog () {}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_username, null);

        EditText etNome     = (EditText)    view.findViewById(R.id.etUsernameDialogNome);
        Button btCancelar   = (Button)      view.findViewById(R.id.btnUsernameDialogCancelar);
        Button btEnviar     = (Button)      view.findViewById(R.id.btnUsernameDialogEnviar);

        btCancelar  .setOnClickListener(cancelar -> this.dismiss());
        btEnviar    .setOnClickListener(enviar -> {
            try{
                if(!etNome.getText().equals("")){
                    USER_NAME = etNome.getText().toString();

                    this.dismiss();
                } else {
                    throw new UsernameException("O nome de usuário não pode ser vazio!");
                }
            } catch (UsernameException e){
                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        builder.setView(view);
        return builder.create();
    }
}
