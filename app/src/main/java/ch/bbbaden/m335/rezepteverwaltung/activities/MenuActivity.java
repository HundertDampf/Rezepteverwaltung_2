package ch.bbbaden.m335.rezepteverwaltung.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ch.bbbaden.m335.rezepteverwaltung.R;
import ch.bbbaden.m335.rezepteverwaltung.services.AppDatabase;
import ch.bbbaden.m335.rezepteverwaltung.services.DatabaseConector;
import ch.bbbaden.m335.rezepteverwaltung.tools.*;

public class MenuActivity extends AppCompatActivity {

    Button btnAlleRezepte;
    Button btnSuche;
    Button btnGluck;
    Button btnNeuesRezept;
    Button btnBackup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnAlleRezepte = findViewById(R.id.btnMenuAlleRezepte);
        btnSuche = findViewById(R.id.btnMenuGoToSuche);
        btnGluck = findViewById(R.id.btnMenuGluck);
        btnNeuesRezept = findViewById(R.id.btnMenuNeuesRezept);
        btnBackup = findViewById(R.id.btnMenuBackup);

        btnAlleRezepte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataHolder.getInstance().setRezepteListe(DatabaseConector.getRezepte());
                goToNewActivity(RezepteListActivity.class);
            }
        });

        btnSuche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNewActivity(SearchRezepteActivity.class);
            }
        });
        btnNeuesRezept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNewActivity(AddRezepteAuswahlActivity.class);
            }
        });
        btnGluck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Rezept random = getRandomRezept();
//                new Toaster(getApplicationContext(), random.getRezeptName() + " RezeptNamer", 1);
//                DataHolder.getInstance().setRezept(random);
                goToNewActivity(RezeptActivity.class);
            }
        });
    }



    public void goToNewActivity(Class goToClass) {
        startActivity(new Intent(getApplicationContext(), goToClass));
    }
}
