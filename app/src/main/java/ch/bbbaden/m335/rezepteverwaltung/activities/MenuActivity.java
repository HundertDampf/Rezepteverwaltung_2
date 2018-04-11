package ch.bbbaden.m335.rezepteverwaltung.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.bbbaden.m335.rezepteverwaltung.R;
import ch.bbbaden.m335.rezepteverwaltung.objects.Recipe;
import ch.bbbaden.m335.rezepteverwaltung.services.DatabaseConector;
import ch.bbbaden.m335.rezepteverwaltung.services.FirebaseConector;
import ch.bbbaden.m335.rezepteverwaltung.tools.DataHolder;
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
                new VariousMethods().goToNewActivity(RecipeListActivity.class, MenuActivity.this);
            }
        });

        btnSuche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new VariousMethods().goToNewActivity(SearchRecipesActivity.class, MenuActivity.this);
            }
        });
        btnNeuesRezept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new VariousMethods().goToNewActivity(AddRecipeMethodsActivity.class, MenuActivity.this);
            }
        });
        btnGluck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recipe random = getRandomRezept();

                DataHolder.getInstance().setRecipe(random);
                new VariousMethods().goToNewActivity(RecipeActivity.class, MenuActivity.this);

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
            Recipe fillRecipe = new Recipe();

            fillRecipe.setRecipeAuthor(DatabaseConector.getUserByMail(auth.getCurrentUser().getEmail()).getUserName());
            fillRecipe.setRecipeInstructions("Author: " + fillRecipe.getRecipeAuthor() + " Zubereitung " + i + " " + getResources().getString(R.string.large_text));

            if (i < 5) {
                fillRecipe.setRecipeName("Recipe lokal" + " " + i + fillRecipe.getRecipeAuthor());
                fillRecipe.setRecipeIsOnline(false);
            } else {
                if (i < 10) {
                    fillRecipe.setRecipeName("Recipe public" + i + " " + fillRecipe.getRecipeAuthor());
                    fillRecipe.setRecipeIsOnline(true);
                    fillRecipe.setRecipeIsPublic(false);
                } else {
                    fillRecipe.setRecipeName("Recipe private" + i + " " + fillRecipe.getRecipeAuthor());
                    fillRecipe.setRecipeIsOnline(true);
                    fillRecipe.setRecipeIsPublic(true);
                }
            }
            fillRecipe.setRecipeId(new VariousMethods().generateRezeptId((fillRecipe)));

            List<String> zutaten = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                zutaten.add(i * 11 + "" + i * 11);
            }
            fillRecipe.setRecipeIngredientsAsList(zutaten);
            if (fillRecipe.isRecipeIsOnline()) {
                new FirebaseConector().addRezeptToFirebase(fillRecipe);
                DatabaseConector.addRezept(fillRecipe);
            } else {
                DatabaseConector.addRezept(fillRecipe);
            }
            DataHolder.getInstance().setRecipe(fillRecipe);

        }

    }

    public Recipe getRandomRezept() {
        List<Recipe> rezepte = DatabaseConector.getRezepte();
        Random rand = new Random();
        return rezepte.get(rand.nextInt(rezepte.size()));
    }
}
