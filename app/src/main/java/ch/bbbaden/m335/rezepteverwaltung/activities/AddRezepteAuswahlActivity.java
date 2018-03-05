package ch.bbbaden.m335.rezepteverwaltung.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ch.bbbaden.m335.rezepteverwaltung.R;

public class AddRezepteAuswahlActivity extends AppCompatActivity {

    Button btnQr;
    Button btnManuel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rezepte_auswahl);

        btnQr = findViewById(R.id.btnArsQr);
        btnManuel = findViewById(R.id.btnArsManuel);

        btnManuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNewActivity(AddRezeptActivity.class);
            }
        });

        btnQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
//                startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
            }
        });
    }


    public void goToNewActivity(Class goToClass) {
        startActivity(new Intent(getApplicationContext(), goToClass));
    }
}

