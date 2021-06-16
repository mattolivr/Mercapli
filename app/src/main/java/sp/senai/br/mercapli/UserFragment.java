package sp.senai.br.mercapli;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

import sp.senai.br.mercapli.adapters.HistoricoAdapter;
import sp.senai.br.mercapli.components.ProgressBarMeta;
import sp.senai.br.mercapli.database.CriarBD;
import sp.senai.br.mercapli.dialogs.MetaDialog;
import sp.senai.br.mercapli.dialogs.UsernameDialog;

import static sp.senai.br.mercapli.GlobalVariables.GASTO_TOTAL;
import static sp.senai.br.mercapli.GlobalVariables.META_GASTOS;
import static sp.senai.br.mercapli.GlobalVariables.USER_NAME;

public class UserFragment extends Fragment {

    private TextView tvUsername;

    private TextView tvGastos, tvMeta;
    private ProgressBarMeta pbMeta;
    private TextView tvStatus;
    private Button btnAlterarMeta;
    private LinearLayout llHistorico;

    private HistoricoAdapter historicoAdapter;
    private LinearLayoutManager historicoLayout;
    private RecyclerView rvHistorico;

    private SQLiteDatabase database;

    public UserFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        tvUsername = view.findViewById(R.id.tvUserUsername);

        tvGastos        = view.findViewById(R.id.tvUserMetaValorGasto);
        tvMeta          = view.findViewById(R.id.tvUserMetaValorTotal);
        tvStatus        = view.findViewById(R.id.tvUserMetaStatus);
        btnAlterarMeta  = view.findViewById(R.id.btnUserMeta);
        rvHistorico     = view.findViewById(R.id.rvUserHistorico);
        llHistorico     = view.findViewById(R.id.llUserHistorico);

        database    = new CriarBD(this.getContext()).getWritableDatabase();
        pbMeta      = new ProgressBarMeta(view, R.id.pbUserMeta);

        historicoAdapter    = new HistoricoAdapter(database);
        historicoLayout     = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, true);

        rvHistorico.setAdapter(historicoAdapter);
        rvHistorico.setLayoutManager(historicoLayout);

        btnAlterarMeta.setOnClickListener(alterarMeta -> {
            DialogFragment dfAlterarMeta = new MetaDialog(database);
            dfAlterarMeta.show(this.getParentFragmentManager(), "alterarMeta");
        });

        tvUsername.setOnClickListener(alterarNome -> {
            DialogFragment dfAlterarNome = new UsernameDialog();
            dfAlterarNome.show(this.getParentFragmentManager(), "alterarNome");
        });

        setComponentsValues();
        atualizarProgressoMeta();
        atualizarHistorico();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setComponentsValues();
        atualizarProgressoMeta();
        atualizarHistorico();
    }

    private void setComponentsValues() {
        tvUsername.setText("Olá, Usuário ");

        tvGastos.setText(DecimalFormat.getCurrencyInstance().format(GASTO_TOTAL));
        tvMeta.setText(String.valueOf(DecimalFormat.getCurrencyInstance().format(META_GASTOS.getValor())));
        tvStatus.setText(META_GASTOS.getStatus());
    }

    private void atualizarProgressoMeta(){
        int valorRestante = META_GASTOS.getValorRestantePorcentagem();
        pbMeta.atualizar(valorRestante);
    }

    private void atualizarHistorico(){
        if(historicoAdapter.getItemCount() == 0){
            llHistorico.setVisibility(View.INVISIBLE);
        }
    }
}