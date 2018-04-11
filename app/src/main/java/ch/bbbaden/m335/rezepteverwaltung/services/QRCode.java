package ch.bbbaden.m335.rezepteverwaltung.services;

import ch.bbbaden.m335.rezepteverwaltung.objects.Recipe;

/**
 * Created by Noah on 02.03.2018.
 */

public class QRCode {
    public Recipe interpretQr(String qrResult) {
        Recipe tempReturnRecipe = new Recipe();
        //  tempReturnRecipe.setRecipeId(AppDatabase.getAppDatabase(MainActivity.context).recipeDAO().getAllRecipes().size() + 1);
        tempReturnRecipe.setRecipeName("QR Scaner comming soon");
        tempReturnRecipe.setRecipeInstructions(qrResult);
        tempReturnRecipe.setRecipeDuration("Soon(tm)");


        return tempReturnRecipe;
    }


}
