package ch.bbbaden.m335.rezepteverwaltung.activities;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import ch.bbbaden.m335.rezepteverwaltung.R;
import ch.bbbaden.m335.rezepteverwaltung.objects.Recipe;
import ch.bbbaden.m335.rezepteverwaltung.services.QRCode;
import ch.bbbaden.m335.rezepteverwaltung.services.barcode.BarcodeCaptureActivity;
import ch.bbbaden.m335.rezepteverwaltung.tools.DataHolder;
import ch.bbbaden.m335.rezepteverwaltung.tools.Toaster;
import ch.bbbaden.m335.rezepteverwaltung.tools.VariousMethods;

public class AddRecipeMethodsActivity extends AppCompatActivity {

    Button btnQr;
    Button btnManuel;
    private RadioGroup radioGroupSave;
    private RadioButton radioBtnSave;

    private static final int BARCODE_READER_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rezepte_auswahl);

        btnQr = findViewById(R.id.btnArsQr);
        btnManuel = findViewById(R.id.btnArsManuel);
        radioGroupSave = findViewById(R.id.radioArsSave);
        radioBtnSave = findViewById(R.id.radioPublic);

        btnManuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRadio();
                new VariousMethods().goToNewActivity(AddRecipeActivity.class, AddRecipeMethodsActivity.this);
            }
        });

        btnQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRadio();
                Intent intent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
                startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
            }
        });
        System.out.println(radioGroupSave.getCheckedRadioButtonId() + "Radio GroupID");


    }

    public void getRadio() {
        if (radioGroupSave.getCheckedRadioButtonId() == R.id.radioLocal) {
            DataHolder.getInstance().setSaveId(1);
        } else if (radioGroupSave.getCheckedRadioButtonId() == R.id.radioPrivate) {
            DataHolder.getInstance().setSaveId(2);
        } else if (radioGroupSave.getCheckedRadioButtonId() == R.id.radioPublic) {
            DataHolder.getInstance().setSaveId(3);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    Point[] p = barcode.cornerPoints;
                    String qrResultat = barcode.displayValue;
                    System.out.println("QR Code        " + barcode.displayValue);
                    Recipe returnRecipe = new QRCode().interpretQr(qrResultat);
                    if (returnRecipe != null) {
                        DataHolder.getInstance().setRecipe(returnRecipe);
                        new VariousMethods().goToNewActivity(RecipeActivity.class, AddRecipeMethodsActivity.this);
                    }else{
                        new Toaster(getWindow().getDecorView().findViewById(android.R.id.content),"Kein Rezept gefunden",-2);
                    }


                } else {
//                    mResultTextView.setText(R.string.no_barcode_captured);
                }
            } else {
//                Log.e(LOG_TAG, String.format(getString(R.string.barcode_error_format),
//                        CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}

