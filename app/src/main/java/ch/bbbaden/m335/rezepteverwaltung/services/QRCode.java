package ch.bbbaden.m335.rezepteverwaltung.services;

import ch.bbbaden.m335.rezepteverwaltung.activities.MainActivity;
import ch.bbbaden.m335.rezepteverwaltung.objects.Rezept;

/**
 * Created by Noah on 02.03.2018.
 */

public class QRCode {
    public Rezept interpretQr(String qrResult) {
        Rezept tempReturnRezept = new Rezept();
      //  tempReturnRezept.setRezeptId(AppDatabase.getAppDatabase(MainActivity.context).rezeptDAO().getAll().size() + 1);
        tempReturnRezept.setRezeptName("QR Scaner comming soon");
        tempReturnRezept.setRezeptZubereitung(qrResult);
        tempReturnRezept.setRezeptDauer("Soon(tm)");


        return tempReturnRezept;
    }
}
