package ch.bbbaden.m335.rezepteverwaltung.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ch.bbbaden.m335.rezepteverwaltung.R;
import ch.bbbaden.m335.rezepteverwaltung.objects.Rezept;
import ch.bbbaden.m335.rezepteverwaltung.services.DatabaseConector;
import ch.bbbaden.m335.rezepteverwaltung.services.FirebaseConector;
import ch.bbbaden.m335.rezepteverwaltung.tools.DataHolder;

public class MenuActivity extends AppCompatActivity {
    FirebaseAuth auth = FirebaseAuth.getInstance();

    Button btnAlleRezepte;
    Button btnSuche;
    Button btnGluck;
    Button btnNeuesRezept;
    Button btnBackup;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnAlleRezepte = findViewById(R.id.btnMenuAlleRezepte);
        btnSuche = findViewById(R.id.btnMenuGoToSuche);
        btnGluck = findViewById(R.id.btnMenuGluck);
        btnNeuesRezept = findViewById(R.id.btnMenuNeuesRezept);
        btnBackup = findViewById(R.id.btnMenuBackup);
        btnLogout = findViewById(R.id.btnMenuLogout);

        btnAlleRezepte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataHolder.getInstance().setRezepteListe(DatabaseConector.getRezepteCombined());
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

        btnBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillDB();
                //TODO remove fillDB()
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();

// this listener will be called when there is change in firebase user session
                FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user == null) {
                            // user auth state is changed - user is null
                            // launch login activity
                            startActivity(new Intent(MenuActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                };
            }
        });
    }


    public void goToNewActivity(Class goToClass) {
        startActivity(new Intent(getApplicationContext(), goToClass));
    }

    public void fillDB() {
        DatabaseConector.deleteRezepte();
        for (int i = 0; i < 15; i++) {
            Rezept fillRezept = new Rezept();
            fillRezept.setRezeptName("Rezept" + i);
            fillRezept.setRezeptZubereitung("Zubereitung " + i + " " + getResources().getString(R.string.large_text));
            fillRezept.setRezeptAuthor("1701");

            if (i < 5) {
                fillRezept.setRezeptOnline(false);
            } else {
                if (i < 10) {
                    fillRezept.setRezeptOnline(true);
                    fillRezept.setRezeptPublic(false);
                } else {
                    fillRezept.setRezeptOnline(true);
                    fillRezept.setRezeptPublic(true);
                }
            }
            fillRezept.setRezeptId(DatabaseConector.generateId(fillRezept));

            if (fillRezept.isRezeptOnline()) {
                new FirebaseConector().addRezeptToFirebase(fillRezept);
                DatabaseConector.addRezept(fillRezept);
            } else {
                DatabaseConector.addRezept(fillRezept);
            }
            DataHolder.getInstance().setRezept(fillRezept);

        }

    }

}
