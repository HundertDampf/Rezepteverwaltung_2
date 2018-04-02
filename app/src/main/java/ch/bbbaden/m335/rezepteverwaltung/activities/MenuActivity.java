package ch.bbbaden.m335.rezepteverwaltung.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.Random;

import ch.bbbaden.m335.rezepteverwaltung.R;
import ch.bbbaden.m335.rezepteverwaltung.objects.Rezept;
import ch.bbbaden.m335.rezepteverwaltung.objects.User;
import ch.bbbaden.m335.rezepteverwaltung.services.AppDatabase;
import ch.bbbaden.m335.rezepteverwaltung.services.DatabaseConector;
import ch.bbbaden.m335.rezepteverwaltung.services.FirebaseConector;
import ch.bbbaden.m335.rezepteverwaltung.tools.DataHolder;
import ch.bbbaden.m335.rezepteverwaltung.tools.Toaster;
import ch.bbbaden.m335.rezepteverwaltung.tools.VariousMethods;

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
                DataHolder.getInstance().setRezepteListe(DatabaseConector.getRezepte());
                new VariousMethods().goToNewActivity(RezepteListActivity.class, MenuActivity.this);
            }
        });

        btnSuche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new VariousMethods().goToNewActivity(SearchRezepteActivity.class, MenuActivity.this );
            }
        });
        btnNeuesRezept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new VariousMethods().goToNewActivity(AddRezepteAuswahlActivity.class, MenuActivity.this);
            }
        });
        btnGluck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rezept random = getRandomRezept();

                DataHolder.getInstance().setRezept(random);
                new VariousMethods().goToNewActivity(RezeptActivity.class, MenuActivity.this);

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

                FirebaseUser user = auth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MenuActivity.this, MainActivity.class));
                    finish();
                }

            }
        });
    }


    public void fillDB() {
        DatabaseConector.deleteRezepte();
        for (int i = 0; i < 15; i++) {
            Rezept fillRezept = new Rezept();

            fillRezept.setRezeptAuthor(DatabaseConector.getUserByMail(auth.getCurrentUser().getEmail()).getUserName());
            fillRezept.setRezeptZubereitung("Author: " + fillRezept.getRezeptAuthor() + " Zubereitung " + i + " " + getResources().getString(R.string.large_text));

            if (i < 5) {
                fillRezept.setRezeptName("Rezept lokal" + " " + i + fillRezept.getRezeptAuthor());
                fillRezept.setRezeptOnline(false);
            } else {
                if (i < 10) {
                    fillRezept.setRezeptName("Rezept public" + i + " " + fillRezept.getRezeptAuthor());
                    fillRezept.setRezeptOnline(true);
                    fillRezept.setRezeptPublic(false);
                } else {
                    fillRezept.setRezeptName("Rezept private" + i + " " + fillRezept.getRezeptAuthor());
                    fillRezept.setRezeptOnline(true);
                    fillRezept.setRezeptPublic(true);
                }
            }
            fillRezept.setRezeptId(new VariousMethods().generateRezeptId((fillRezept)));

            if (fillRezept.isRezeptOnline()) {
                new FirebaseConector().addRezeptToFirebase(fillRezept);
                DatabaseConector.addRezept(fillRezept);
            } else {
                DatabaseConector.addRezept(fillRezept);
            }
            DataHolder.getInstance().setRezept(fillRezept);

        }

    }

    public Rezept getRandomRezept() {
        List<Rezept> rezepte = DatabaseConector.getRezepte();
        Random rand = new Random();
        return rezepte.get(rand.nextInt(rezepte.size()));
    }
}
