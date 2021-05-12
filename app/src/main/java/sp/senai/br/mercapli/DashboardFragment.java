package sp.senai.br.mercapli;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DecimalFormat;

import static sp.senai.br.mercapli.Constant.GASTO_TOTAL;

public class DashboardFragment extends Fragment {

    public DashboardFragment() {

    }

    public static DashboardFragment newInstance() {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    DrawerLayout drawerLayoutDashboard;
    ImageButton imageMenuDashboard;

    private TextView tvGastoTotal;

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

        imageMenuDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayoutDashboard.openDrawer(GravityCompat.START);
            }
        });

        // TODO: Descobrir pq o TextView ValorDashboard não atualiza ao criar a View
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        atualizarGasto();
    }

    private void atualizarGasto() {
        tvGastoTotal.setText(DecimalFormat.getCurrencyInstance().format(GASTO_TOTAL));
    }
}