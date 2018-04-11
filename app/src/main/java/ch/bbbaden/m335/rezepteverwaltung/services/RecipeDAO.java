package ch.bbbaden.m335.rezepteverwaltung.services;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import ch.bbbaden.m335.rezepteverwaltung.objects.Recipe;

/**
 * Created by Noah on 02.03.2018.
 */

@Dao
public interface RecipeDAO {
    @Query("SELECT * FROM Recipes")
    List<Recipe> getAllRecipes();

    @Query("SELECT * FROM Recipes WHERE recipeId IN (:rezeptId)")
    Recipe loadAllRecipesByIds(int rezeptId);

    @Query("SELECT * FROM Recipes WHERE recipeName LIKE :name LIMIT 1")
    Recipe findRecipesByName(String name);

    @Query("SELECT * FROM Recipes WHERE recipeAuthor LIKE :author")
    List<Recipe> findRecipeByAuthor(String author);

    @Insert
    void insertAll(Recipe... rezepte);

    @Delete
    void delete(Recipe recipe);

    @Query("DELETE FROM Recipes")
    void deleteAll();


}