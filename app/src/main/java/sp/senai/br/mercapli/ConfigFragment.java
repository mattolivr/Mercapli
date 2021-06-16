package sp.senai.br.mercapli;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import sp.senai.br.mercapli.database.CriarBD;

public class ConfigFragment extends Fragment {

    private Button btDeletarBanco;

    public ConfigFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_config, container, false);

        CriarBD criarBD = new CriarBD(getContext());

        btDeletarBanco = view.findViewById(R.id.btnConfigDeleteDatabase);

        btDeletarBanco.setOnClickListener(deletarBanco -> criarBD.onUpgrade(criarBD.getWritableDatabase(), 1,1));

        return view;
    }
}