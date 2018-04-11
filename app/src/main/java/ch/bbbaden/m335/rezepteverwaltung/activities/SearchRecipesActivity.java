package ch.bbbaden.m335.rezepteverwaltung.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import ch.bbbaden.m335.rezepteverwaltung.R;
import ch.bbbaden.m335.rezepteverwaltung.objects.Recipe;
import ch.bbbaden.m335.rezepteverwaltung.services.SearchRecipes;
import ch.bbbaden.m335.rezepteverwaltung.tools.DataHolder;
import ch.bbbaden.m335.rezepteverwaltung.tools.Toaster;
import ch.bbbaden.m335.rezepteverwaltung.tools.VariousMethods;

public class SearchRecipesActivity extends AppCompatActivity {

    Button btnSearch;
    EditText editName;
    EditText editAuthor;
    EditText editIngredients;

    String queryName;
    String queryAuthor;
    List<String> queryIngredients;
    List<Recipe> searchResultsRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_rezepte);

        editName = findViewById(R.id.editSuchName);
        editAuthor = findViewById(R.id.editSuchAuthor);
        editIngredients = findViewById(R.id.editSuchZutaten);
        btnSearch = findViewById(R.id.btnSearch);

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
    }
}
