package ch.bbbaden.m335.rezepteverwaltung.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ch.bbbaden.m335.rezepteverwaltung.R;
import ch.bbbaden.m335.rezepteverwaltung.objects.Recipe;
import ch.bbbaden.m335.rezepteverwaltung.services.SearchRecipes;
import ch.bbbaden.m335.rezepteverwaltung.tools.DataHolder;
import ch.bbbaden.m335.rezepteverwaltung.tools.Toaster;
import ch.bbbaden.m335.rezepteverwaltung.tools.VariousMethods;

public class SearchRecipesActivity extends AppCompatActivity {

    Button btnSearch;
    ImageButton btnVoiceName;
    EditText editName;
    EditText editAuthor;
    EditText editIngredients;

    String queryName;
    String queryAuthor;
    List<String> queryIngredients;
    List<Recipe> searchResultsRecipes;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_rezepte);

        editName = findViewById(R.id.editSuchName);
        editAuthor = findViewById(R.id.editSuchAuthor);
        editIngredients = findViewById(R.id.editSuchZutaten);
        btnSearch = findViewById(R.id.btnSearch);
        btnVoiceName = findViewById(R.id.btnSearchVoiceName);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryName = editName.getText().toString();
                queryAuthor = editAuthor.getText().toString();

                System.out.println("Angebene Zutaten: " + editIngredients.getText().toString());
                queryIngredients = new ArrayList<>();
                String[] queryIngredientsAsArray = editIngredients.getText().toString().split(",");         //Die angeben Zutaten werden nach Kommas getrennt
                System.out.println("lenght queryIngredientsAsArray " + queryIngredientsAsArray.length);

                for (int i = 0; i < queryIngredientsAsArray.length; i++) {
                    queryIngredients.add(queryIngredientsAsArray[i].trim());
                }

                searchResultsRecipes = new SearchRecipes().doSearch(queryName, queryAuthor, queryIngredients);
                queryIngredients = null;

                if (searchResultsRecipes != null) {
                    if (searchResultsRecipes.size() == 1) {         //Wenn nur ein Recipe gefunden wird, wird dieses sofort in der RecipeActivity angezeigt
                        DataHolder.getInstance().setRecipe(searchResultsRecipes.get(0));
                        startActivity(new Intent(getApplicationContext(), RecipeActivity.class));
                    } else if (searchResultsRecipes.size() > 1) {       //Bei mehreren Rezepten werden die Resultate in der Listen Activity angezeigt
                        DataHolder.getInstance().setRezepteListe(searchResultsRecipes);
                        new VariousMethods().goToNewActivity(RecipeListActivity.class, SearchRecipesActivity.this);
                    } else {
                        new Toaster(getWindow().getDecorView().findViewById(android.R.id.content), "Keine der Suche entsprechenden Rezepte gefunden", -2);

                    }
                } else {
                    new Toaster(getWindow().getDecorView().findViewById(android.R.id.content), "Keine Rezepte in der Datebank gefunden", -2);
                }
            }
        });

        btnVoiceName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askSpeechInput(0);
            }
        });
    }

    //TODO Voice recognigen für alle Felder
    private void askSpeechInput(int btnId) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi speak something");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    // Receiving speech input

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    editName.setText(result.get(0));
                }
                break;
            }

        }
    }
}