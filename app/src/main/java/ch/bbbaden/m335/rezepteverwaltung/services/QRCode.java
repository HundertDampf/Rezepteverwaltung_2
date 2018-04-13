package ch.bbbaden.m335.rezepteverwaltung.services;

import ch.bbbaden.m335.rezepteverwaltung.objects.Recipe;

/**
 * Created by Noah on 02.03.2018.
 */

public class QRCode {
    Recipe returnRecipe;

    public Recipe interpretQr(String qrResultAsString) {
        if (qrResultAsString.contains("Firebase1Rezepteverwaltung31415%")) {
            String[] qrResultAsArray = qrResultAsString.split("%");
            returnRecipe = DatabaseConector.getRezepteById(qrResultAsArray[1]);
            for (int i = 0; i < qrResultAsArray.length; i++) {
                System.out.println(qrResultAsArray[i]);
            }
        }

        return returnRecipe;
    }


}
