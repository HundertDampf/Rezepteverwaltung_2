package ch.bbbaden.m335.rezepteverwaltung.tools;

import android.content.Context;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;

import ch.bbbaden.m335.rezepteverwaltung.objects.Recipe;
import ch.bbbaden.m335.rezepteverwaltung.objects.User;
import ch.bbbaden.m335.rezepteverwaltung.services.DatabaseConector;


public class VariousMethods {

    public User getCurrentUserData() {
        return DatabaseConector.getUserByMail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }

    static public String generateRezeptId(Recipe recipe) {
        String returnId;
        String userId = new VariousMethods().getCurrentUserData().getUserShortId() + "";

        if (recipe.isRecipeIsOnline()) {
            if (recipe.isRecipeIsPublic()) {
                returnId = "10";
            } else {
                returnId = "20";
            }
        } else {
            returnId = "30";
        }

        returnId += "" + String.valueOf(userId).length() + userId + DatabaseConector.getRezepte().size();
        System.out.println("returnId " + returnId);
        return returnId;
    }

    public void goToNewActivity(Class goToClass, Context context) {
        context.startActivity(new Intent(context, goToClass));
    }
}
