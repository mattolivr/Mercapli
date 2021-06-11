package sp.senai.br.mercapli;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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

import sp.senai.br.mercapli.database.CriarBD;

import static sp.senai.br.mercapli.GlobalVariables.GASTO_TOTAL;
import static sp.senai.br.mercapli.GlobalVariables.META_GASTOS;

public class DashboardFragment extends Fragment {

    private TextView tvGastoTotal;
    private DrawerLayout drawerLayoutDashboard;
    private ImageButton imageMenuDashboard;
    private NavigationView navigationViewDashboard;
    private NavController navControllerDashboard;
    private ProgressBar pbMeta;

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

        drawerLayoutDashboard = view.findViewById(R.id.drawerLayoutDashboard);
        imageMenuDashboard = view.findViewById(R.id.ibDashMenu);
        tvGastoTotal = view.findViewById(R.id.tvDashValorTotal);
        navigationViewDashboard = view.findViewById(R.id.navigationViewDashboard);
        pbMeta = view.findViewById(R.id.pbDashMeta);

        navControllerDashboard = Navigation.findNavController(getActivity(), R.id.fragment);

        database = new CriarBD(view.getContext()).getReadableDatabase();

        imageMenuDashboard.setOnClickListener(view1 -> drawerLayoutDashboard.openDrawer(GravityCompat.START));
        NavigationUI.setupWithNavController(navigationViewDashboard, navControllerDashboard);

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
        pbMeta.setProgress(valorRestante);

        if(valorRestante > 0){
            if(valorRestante < 60)
                pbMeta.getProgressDrawable().setColorFilter(getResources().getColor(R.color.blue_300), android.graphics.PorterDuff.Mode.SRC_IN);
            else if(valorRestante >= 60 && valorRestante < 85)
                pbMeta.getProgressDrawable().setColorFilter(getResources().getColor(R.color.yellow_warn), android.graphics.PorterDuff.Mode.SRC_IN);
            else
                pbMeta.getProgressDrawable().setColorFilter(getResources().getColor(R.color.red_warn), android.graphics.PorterDuff.Mode.SRC_IN);
        }
    }
}