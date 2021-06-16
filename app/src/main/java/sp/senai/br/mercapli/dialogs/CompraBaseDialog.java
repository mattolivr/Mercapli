package sp.senai.br.mercapli.dialogs;

import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import sp.senai.br.mercapli.CarrinhoActivity;
import sp.senai.br.mercapli.R;
import sp.senai.br.mercapli.adapters.ListaAdapter;
import sp.senai.br.mercapli.database.CriarBD;

public class CompraBaseDialog extends DialogFragment {

    public CompraBaseDialog() {}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_base_compra, null);

        RecyclerView rvListas   = (RecyclerView) view.findViewById(R.id.rvBaseCompraListas);
        Button btCancelar       = (Button) view.findViewById(R.id.btnBaseCompraNao);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true);;
        ListaAdapter listaAdapter                = new ListaAdapter(getActivity(), ListaAdapter.LISTA_SHORT);
        SQLiteDatabase database                  = new CriarBD(getContext()).getWritableDatabase();

        btCancelar.setOnClickListener(cancelar -> callCarrinhoActivity());

        listaAdapter.setListas(database);

        rvListas.setAdapter(listaAdapter);
        rvListas.setLayoutManager(layoutManager);

        builder.setView(view);

        return builder.create();
    }

    private void callCarrinhoActivity(){
        Intent it = new Intent(super.getContext(), CarrinhoActivity.class);
        it.putExtra("newParam", true);
        startActivity(it);
        this.dismiss();
    }
}
