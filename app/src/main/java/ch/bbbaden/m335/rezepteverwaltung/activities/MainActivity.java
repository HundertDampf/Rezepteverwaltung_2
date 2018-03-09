package ch.bbbaden.m335.rezepteverwaltung.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

import ch.bbbaden.m335.rezepteverwaltung.R;
import ch.bbbaden.m335.rezepteverwaltung.tools.Toaster;

public class MainActivity extends AppCompatActivity {
    public static Context context;

    private String inputName;
    private String inputPassword;

    EditText editName;
    EditText editPassword;
    Button btnEnter;
    ProgressBar progressBar;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        auth = FirebaseAuth.getInstance();

        btnEnter = findViewById(R.id.btnMainLogin);
        editName = findViewById(R.id.editMainName);
        editPassword = findViewById(R.id.editMainPasswort);
        progressBar = findViewById(R.id.progressBar);

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(this, MenuActivity.class));
            finish();
        }

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editName.getText().toString();
                final String password = editPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    new Toaster(getApplicationContext(), "Enter email address!", 1);
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    new Toaster(getApplicationContext(), "Enter password!", 1);
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        //progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (password.length() < 6) {
                                editPassword.setError("Passwort zu kurz");
                            } else {
                                new Toaster(getApplicationContext(), "Lgin Fehler", 1);
                            }
                        } else {
                            Intent intent = new Intent(context, MenuActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        });
    }
}