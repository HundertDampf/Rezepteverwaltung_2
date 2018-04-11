package ch.bbbaden.m335.rezepteverwaltung.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import ch.bbbaden.m335.rezepteverwaltung.R;
import ch.bbbaden.m335.rezepteverwaltung.objects.Recipe;
import ch.bbbaden.m335.rezepteverwaltung.services.DatabaseConector;
import ch.bbbaden.m335.rezepteverwaltung.services.FirebaseConector;
import ch.bbbaden.m335.rezepteverwaltung.tools.*;

public class AddRecipeActivity extends AppCompatActivity {

    Button btnAdd;
    EditText[] editTexts;
    Recipe addRecipe;

    private boolean rezeptAdded = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rezept);

        int[] editTextsIds = {R.id.editAddRezeptName, R.id.editAddZubereitung, R.id.editAddZutat, R.id.editAddDauer};
        final String[] editTextsStrings = {getResources().getString(R.string.recipeName), getResources().getString(R.string.recipeIngredients), getResources().getString(R.string.recipeIngredients), getResources().getString(R.string.recipeDuration)};


        editTexts = new EditText[4];
        for (int i = 0; i < editTexts.length; i++) {
            editTexts[i] = findViewById(editTextsIds[i]);
            final int y = i;
            System.out.println(y + " <-- y + i--> " + i);

            editTexts[i].setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if (hasFocus) {
                        if (editTexts[y].getText().toString().equals(editTextsStrings[y])) {
                            System.out.println(editTexts[y].getText().toString() + "  --------- " + editTextsStrings[y]);
                            editTexts[y].setText("");
                        }
                    }
                    return;
                }
            });

        }
        editTexts[3].setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((keyCode == KeyEvent.KEYCODE_ENTER)) {
                    System.out.println("On keylistener triggered'???????????????????????????????????????????????????????'");
                    if (!rezeptAdded) {
                        saveRezept();
                    }
                    return true;
                } else
                    return false;
            }
        });

        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Butoon------------------------------------------------------");
                saveRezept();
            }
        });
    }

    public void saveRezept() {
        System.out.println("SafeRezept methode started");
        addRecipe = new Recipe();
        addRecipe.setRecipeName(editTexts[0].getText().toString());
        addRecipe.setRecipeDuration(editTexts[3].getText().toString());
        addRecipe.setRecipeInstructions(editTexts[1].getText().toString());

        //TODO WIP ZUtaten
        List<String> zutaten = new ArrayList<>();
        zutaten.add("Zutat1");
        zutaten.add("Zutat2");
        zutaten.add("Zutat2");

        String[] zutat={"zutat1","zutat2", "zutat3"};
        System.out.println(zutat.toString());
       addRecipe.setRecipeIngredientsAsList(zutaten);

        isOnline();
        addRecipe.setRecipeAuthor(new VariousMethods().getCurrentUserData().getUserName());
        addRecipe.setRecipeId(new VariousMethods().generateRezeptId(addRecipe));

        System.out.println("SaveRezept() " + addRecipe.getRecipeId());

        if (addRecipe.isRecipeIsOnline()) {
            new FirebaseConector().addRezeptToFirebase(addRecipe);
            DatabaseConector.addRezept(addRecipe);
        } else {
            DatabaseConector.addRezept(addRecipe);
        }
        DataHolder.getInstance().setRecipe(addRecipe);
        rezeptAdded = true;   //TODO check if okay
        new VariousMethods().goToNewActivity(RecipeActivity.class, AddRecipeActivity.this);
    }


    public void isOnline() {
        if (DataHolder.getInstance().getSaveId() == 1) {
            addRecipe.setRecipeIsOnline(false);
        } else if (DataHolder.getInstance().getSaveId() == 2) {
            addRecipe.setRecipeIsOnline(true);
            addRecipe.setRecipeIsPublic(false);
        } else if (DataHolder.getInstance().getSaveId() == 3) {
            addRecipe.setRecipeIsOnline(true);
            addRecipe.setRecipeIsPublic(true);
        }
    }


}
