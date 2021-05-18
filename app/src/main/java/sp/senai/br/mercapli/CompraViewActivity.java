package sp.senai.br.mercapli;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class CompraViewActivity extends AppCompatActivity {

    private int CompraID;

    ImageButton btnCloseActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra_view);
        getSupportActionBar().hide();

        btnCloseActivity = findViewById(R.id.ibCompraViewVoltar);

        btnCloseActivity.setOnClickListener(this::closeActivity);

        CompraID = getIntent().getIntExtra("ItemID", 1);
        System.out.println("Cuzão : " + CompraID);
    }

    private void closeActivity(View view) {
        super.finish();
    }
}