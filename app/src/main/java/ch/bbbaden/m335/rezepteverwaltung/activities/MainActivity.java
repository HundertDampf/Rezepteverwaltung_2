package ch.bbbaden.m335.rezepteverwaltung.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ch.bbbaden.m335.rezepteverwaltung.R;
import ch.bbbaden.m335.rezepteverwaltung.tools.Toaster;

public class MainActivity extends AppCompatActivity {
    public static Context context;

    private String inputName;
    private String inputPassword;

    EditText editName;
    EditText editPassword;
    Button btnEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        btnEnter = findViewById(R.id.btnMainLogin);
        editName = findViewById(R.id.editMainName);
        editPassword = findViewById(R.id.editMainPasswort);


        editName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_ENTER)) {
                    editPassword.requestFocus();
                    return true;
                } else
                    return false;
            }
        });

        editPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_ENTER)) {
                    doLogin();
                    return true;
                } else
                    return false;
            }
        });

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });


    }

    public void doLogin() {
        inputName = editName.getText().toString();
        inputPassword = editPassword.getText().toString();
        //TODO Noah Look Up Firebase Login
        if (inputName.trim().length() > 0 & inputPassword.trim().length() > 0) {
            if (inputName != "Name") {
                try {
                    startActivity(new Intent(MainActivity.this, MenuActivity.class)); //open Menu Acitvity

                } catch (Exception e) {
                }
            } else {
                new Toaster(getApplicationContext(), "Bitte geben Sie eine gültige Klasse ein", 0);
            }
        } else {
            new Toaster(getApplicationContext(), "Bitte geben Sie etwas ein", 0);

        }
    }
}
