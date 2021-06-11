package sp.senai.br.mercapli;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DecimalFormat;

import sp.senai.br.mercapli.database.CriarBD;
import sp.senai.br.mercapli.dialogs.MetaDialog;

import static sp.senai.br.mercapli.GlobalVariables.GASTO_TOTAL;
import static sp.senai.br.mercapli.GlobalVariables.META_GASTOS;
import static sp.senai.br.mercapli.GlobalVariables.USER_NAME;

public class UserFragment extends Fragment {

    private TextView tvUsername;

    private TextView tvGastos, tvMeta;
    private ProgressBar pbMeta;
    private TextView tvStatus;
    private Button btnAlterarMeta;

    private SQLiteDatabase database;

    public UserFragment() {}

    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        tvUsername = view.findViewById(R.id.tvUserUsername);

        pbMeta = view.findViewById(R.id.pbUserMeta);
        tvGastos = view.findViewById(R.id.tvUserMetaValorGasto);
        tvMeta = view.findViewById(R.id.tvUserMetaValorTotal);
        tvStatus = view.findViewById(R.id.tvUserMetaStatus);
        btnAlterarMeta = view.findViewById(R.id.btnUserMeta);

        database = new CriarBD(this.getContext()).getWritableDatabase();

        btnAlterarMeta.setOnClickListener(alterarMeta -> {
            DialogFragment dfAlterarMeta = new MetaDialog(database);
            dfAlterarMeta.show(this.getParentFragmentManager(), "alterarMeta");
        });

        setComponentsValues();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setComponentsValues();
    }

    private void setComponentsValues() {
        tvUsername.setText("Olá, " + USER_NAME + "!");
        pbMeta.setProgress(META_GASTOS.getValorRestantePorcentagem());

        tvGastos.setText(DecimalFormat.getCurrencyInstance().format(GASTO_TOTAL));
        tvMeta.setText(String.valueOf(DecimalFormat.getCurrencyInstance().format(META_GASTOS.getValor())));

        if(GASTO_TOTAL < META_GASTOS.getValor() * 0.3 && META_GASTOS.getValorRestante() > 0)
            tvStatus.setText("Você está prestes a exceder sua meta de gastos!");
        else if(GASTO_TOTAL > META_GASTOS.getValor())
            tvStatus.setText("Você excedeu sua meta de gastos!");
        else
            tvStatus.setText("");
    }
}