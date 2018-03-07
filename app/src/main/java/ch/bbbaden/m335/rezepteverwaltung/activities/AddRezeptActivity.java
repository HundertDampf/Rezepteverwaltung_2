package ch.bbbaden.m335.rezepteverwaltung.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ch.bbbaden.m335.rezepteverwaltung.R;
import ch.bbbaden.m335.rezepteverwaltung.objects.Rezept;
import ch.bbbaden.m335.rezepteverwaltung.tools.DataHolder;

public class AddRezeptActivity extends AppCompatActivity {

    Button btnAdd;
    EditText[] editTexts;
    Rezept addRezept;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rezept);

        int[] editTextsIds = {R.id.editAddRezeptName, R.id.editAddZubereitung, R.id.editAddZutat, R.id.editAddDauer};
        final String[] editTextsStrings = {getResources().getString(R.string.rezeptName), getResources().getString(R.string.rezeptZubereitung), getResources().getString(R.string.rezeptZutaten), getResources().getString(R.string.rezeptDauer)};


        editTexts = new EditText[4];
        for (int i = 0; i < editTexts.length; i++) {
            editTexts[i] = findViewById(editTextsIds[i]);
            final int y = i;
            System.out.println(y + " <-- y + i--> " + i);

//            if (i < 3) {
//                editTexts[i].setOnKeyListener(new View.OnKeyListener() {
//
//                    @Override
//                    public boolean onKey(View v, int keyCode, KeyEvent event) {
//
//                        if ((keyCode == KeyEvent.KEYCODE_ENTER)) {
//                            editTexts[y + 1].requestFocus();
//                            return true;
//                        } else{
//                            return false;
//                    }}
//                });
//            }
            if (i == 3) {
                editTexts[i].setOnKeyListener(new View.OnKeyListener() {

                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {

                        if ((keyCode == KeyEvent.KEYCODE_ENTER)) {
                            saveRezept();
                            return true;
                        } else
                            return false;
                    }
                });
            }
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
            btnAdd = findViewById(R.id.btnAdd);
        }
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRezept();
            }
        });
    }

    public void saveRezept() {
        addRezept = new Rezept();
        addRezept.setRezeptName(editTexts[0].getText().toString());
        addRezept.setRezeptDauer(editTexts[3].getText().toString());
        addRezept.setRezeptZubereitung(editTexts[1].getText().toString());
        isOnline();
        addRezept.setRezeptId(generateId());
        finish();
        // addRezept(AppDatabase.getAppDatabase(MainActivity.context), mDatabase, addRezept);
        DataHolder.getInstance().setRezept(addRezept);
        goToNewActivity(RezeptActivity.class);
    }


    public void goToNewActivity(Class goToClass) {
        startActivity(new Intent(getApplicationContext(), goToClass));
    }

    public void isOnline() {
        if (DataHolder.getInstance().getSaveId() == 1) {
            addRezept.setRezeptOnline(false);
        } else if (DataHolder.getInstance().getSaveId() == 2) {
            addRezept.setRezeptOnline(true);
            addRezept.setRezeptPublic(false);
        } else if (DataHolder.getInstance().getSaveId() == 3) {
            addRezept.setRezeptOnline(true);
            addRezept.setRezeptPublic(true);
        }
    }

    public String generateId() {
        String id;
        //TODO userId & RezeptId
        if (addRezept.isRezeptOnline()) {
            if (addRezept.isRezeptPublic()) {
                id = "10";
            } else {
                id = "20";
            }
        } else {
            id = "30";
        }

        return id;
    }

}
