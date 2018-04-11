package ch.bbbaden.m335.rezepteverwaltung.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import ch.bbbaden.m335.rezepteverwaltung.R;
import ch.bbbaden.m335.rezepteverwaltung.objects.Recipe;
import ch.bbbaden.m335.rezepteverwaltung.tools.*;

public class RecipeActivity extends AppCompatActivity {
    Recipe recipeToShow;

    ListView listZutaten;
    TextView viewDauer;
    TextView viewZubereitung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezept);
        //TODO ersetzen der ListView
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recipeToShow = DataHolder.getInstance().getRecipe();
        viewDauer = findViewById(R.id.viewRezeptDauer);
        viewZubereitung = findViewById(R.id.viewRezeptZubereitung);
        listZutaten = findViewById(R.id.listRezeptZutaten);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ShareRecipesActivity.class));
            }
        });

        if (recipeToShow.getRecipeName() != null) {
            setTitle(recipeToShow.getRecipeName());
        } else {
            setTitle("undefined");
        }
        String[] zutatenListItems;
        if (recipeToShow.getRecipeIngredientsAsString() != null) {
            System.out.println("Size recipeToShow.getRecipeIngredientsAsList====================== " + recipeToShow.getRecipeIngredientsAsList().size());
            zutatenListItems= new String[recipeToShow.getRecipeIngredientsAsList().size()];

            for (int i = 0; i < recipeToShow.getRecipeIngredientsAsList().size(); i++) {
                System.out.println("getZutaten loop " + i);
                zutatenListItems[i] = recipeToShow.getRecipeIngredientsAsList().get(i);
                System.out.println(zutatenListItems[i]);
            }
        }else{
            zutatenListItems = new String[1];
            zutatenListItems[0]="undefined";
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, zutatenListItems);
        listZutaten.setAdapter(adapter);

        if(recipeToShow.getRecipeDuration()!=null) {
            viewDauer.setText(recipeToShow.getRecipeDuration());
        }else{
            viewDauer.setText("undefined");
        }

        if(recipeToShow.getRecipeInstructions()!=null) {
            viewZubereitung.setText(recipeToShow.getRecipeInstructions());
        }else{
            viewZubereitung.setText("undefined");
        }

        new Toaster(getApplicationContext(), recipeToShow.getRecipeId() + " rezeptID", 1);

    }
}
