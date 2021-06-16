package sp.senai.br.mercapli;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;

import sp.senai.br.mercapli.components.ProgressBarMeta;
import sp.senai.br.mercapli.database.CriarBD;
import sp.senai.br.mercapli.dialogs.MetaDialog;

import static sp.senai.br.mercapli.GlobalVariables.GASTO_TOTAL;
import static sp.senai.br.mercapli.GlobalVariables.META_GASTOS;

public class DashboardFragment extends Fragment {

    private TextView tvGastoTotal;
    private DrawerLayout drawerLayoutDashboard;
    private ImageButton imageMenuDashboard;
    private ImageButton ibAtalhoCompra, ibAtalhoLista, ibAtalhoMeta, ibAtalhoConfig;
    private NavigationView navigationViewDashboard;
    private NavController navControllerDashboard;
    private ProgressBarMeta pbMeta;

    private SQLiteDatabase database;

    public DashboardFragment() {}

    public static DashboardFragment newInstance() {
        DashboardFragment fragment = new DashboardFragment();
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

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        drawerLayoutDashboard   = view.findViewById(R.id.drawerLayoutDashboard);
        imageMenuDashboard      = view.findViewById(R.id.ibDashMenu);
        tvGastoTotal            = view.findViewById(R.id.tvDashValorTotal);
        navigationViewDashboard = view.findViewById(R.id.navigationViewDashboard);
        ibAtalhoCompra          = view.findViewById(R.id.ibDashAtalhoAddCompra);
        ibAtalhoLista           = view.findViewById(R.id.ibDashAtalhoAddLista);
        ibAtalhoMeta            = view.findViewById(R.id.ibDashAtalhoAddMeta);
        ibAtalhoConfig          = view.findViewById(R.id.ibDashAtalhoConfig);

        navControllerDashboard = Navigation.findNavController(getActivity(), R.id.fragment);

        database = new CriarBD(view.getContext()).getReadableDatabase();
        pbMeta = new ProgressBarMeta(view, R.id.pbDashMeta);

        NavigationUI.setupWithNavController(navigationViewDashboard, navControllerDashboard);

        imageMenuDashboard.setOnClickListener(menu -> drawerLayoutDashboard.openDrawer(GravityCompat.START));
        ibAtalhoCompra.setOnClickListener(compra -> callCompraActivity());
        ibAtalhoLista .setOnClickListener(lista  -> callListaActivity());
        ibAtalhoMeta  .setOnClickListener(meta   -> callMetaDialog());
        ibAtalhoConfig.setOnClickListener(cnfg   -> callConfigActivity());

        atualizarProgressoMeta();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        atualizarGasto();
        atualizarProgressoMeta();
    }

    private void atualizarGasto() {
        tvGastoTotal.setText(DecimalFormat.getCurrencyInstance().format(GASTO_TOTAL));
    }

    private void atualizarProgressoMeta(){
        int valorRestante = META_GASTOS.getValorRestantePorcentagem();
        pbMeta.atualizar(valorRestante);
    }

    private void callCompraActivity(){
        Intent it = new Intent(this.getContext(), CarrinhoActivity.class);
        startActivity(it);
    }

    private void callListaActivity(){
        Intent it = new Intent(this.getContext(), ListaActivity.class);
        startActivity(it);
    }

    private void callMetaDialog(){
        DialogFragment dfMeta = new MetaDialog(database);
        dfMeta.show(getParentFragmentManager(), "metaAtalho");
    }

    private void callConfigActivity(){
        Intent it = new Intent(this.getContext(), ConfigFragment.class);
        startActivity(it);
    }
}