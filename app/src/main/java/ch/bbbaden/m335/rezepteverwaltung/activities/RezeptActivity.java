package ch.bbbaden.m335.rezepteverwaltung.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import ch.bbbaden.m335.rezepteverwaltung.R;
import ch.bbbaden.m335.rezepteverwaltung.objects.Rezept;
import ch.bbbaden.m335.rezepteverwaltung.tools.*;

public class RezeptActivity extends AppCompatActivity {
    Rezept rezeptToShow;

    TextView viewDauer;
    TextView viewZubereitung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezept);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rezeptToShow = DataHolder.getInstance().getRezept();
        viewDauer = findViewById(R.id.viewRezeptDauer);
        viewZubereitung = findViewById(R.id.viewRezeptZubereitung);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(getApplicationContext(),ShareRezepteActivity.class));
            }
        });

        setTitle(rezeptToShow.getRezeptName());
        viewDauer.setText(rezeptToShow.getRezeptDauer());
        viewZubereitung.setText(rezeptToShow.getRezeptZubereitung());

        new Toaster(getApplicationContext(), rezeptToShow.getRezeptId() + " rezeptID", 1);

    }
}
