package ch.bbbaden.m335.rezepteverwaltung.services;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import ch.bbbaden.m335.rezepteverwaltung.R;
import ch.bbbaden.m335.rezepteverwaltung.activities.MainActivity;
import ch.bbbaden.m335.rezepteverwaltung.objects.Rezept;

import static ch.bbbaden.m335.rezepteverwaltung.activities.ShareRezepteActivity.QRcodeWidth;

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
