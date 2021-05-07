package sp.senai.br.mercapli;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static sp.senai.br.mercapli.Constant.META_GASTOS;

public class UserFragment extends Fragment {

    private Button btnAlterarMeta;

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

        btnAlterarMeta = view.findViewById(R.id.btnUserMeta);


        btnAlterarMeta.setOnClickListener(alterarMeta -> {

        });
        return view;
    }
}