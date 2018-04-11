package ch.bbbaden.m335.rezepteverwaltung.objects;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Noah on 02.03.2018.
 */
@Entity(tableName = "Recipes")
public class Recipe {

    @PrimaryKey
    @NonNull
    @Getter
    @Setter
    private String recipeId;

    @Getter
    @Setter
    private String recipeName;

    @Getter
    @Setter
    private String recipeAuthor;

    @Getter
    @Setter
    private String recipeIngredientsAsString;

    @Exclude
    @Ignore
    private List<String> recipeIngredientsAsList;

    @Getter
    @Setter
    private String recipeInstructions;

    @Getter
    @Setter
    private String recipeDuration;

    @Getter
    @Setter
    private boolean recipeIsOnline;

    @Getter
    @Setter
    private boolean recipeIsPublic;

    public Recipe() {
    }

    public Recipe(String recipeId, String recipeName, String recipeInstructions, String recipeIngredientsAsString, String recipeDuration, boolean rezeptStatus, boolean rezeptDbMethod) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.recipeInstructions = recipeInstructions;
        this.recipeIngredientsAsString = recipeIngredientsAsString;
        this.recipeDuration = recipeDuration;
        this.recipeIsOnline = rezeptStatus;
        this.recipeIsPublic = rezeptDbMethod;
    }

    public void setRecipeIngredientsAsList(List<String> ingredients) {  //formt aus einer ArrayList einen String und speichert diesen
        System.out.println("Setter Zutaten List Grösse: " + ingredients.size());
        recipeIngredientsAsList = ingredients;
        String serializedIndredients = "";
        for (int i = 0; i < ingredients.size(); i++) {
            System.out.println("serializer loop EDFAGRREAAREDYGRAGZHXSDHT     " + i);
            serializedIndredients += ingredients.get(i) + ",";
        }
        setRecipeIngredientsAsString(serializedIndredients);
    }

    public List<String> getRecipeIngredientsAsList() {      //formt eine ArrayList aus einem String
        if (recipeIngredientsAsList == null) {
            if (getRecipeIngredientsAsString() != null || getRecipeIngredientsAsString() == "") {
                recipeIngredientsAsList = new ArrayList<>();
                String[] ingredientsAsArray = getRecipeIngredientsAsString().split(",");
                for (int i = 0; i < ingredientsAsArray.length; i++) {
                    recipeIngredientsAsList.add(ingredientsAsArray[i]);
                }
            }
        }
        return recipeIngredientsAsList;
    }
}

