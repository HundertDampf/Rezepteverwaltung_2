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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import ch.bbbaden.m335.rezepteverwaltung.R;
import ch.bbbaden.m335.rezepteverwaltung.services.DatabaseConector;
import ch.bbbaden.m335.rezepteverwaltung.services.FirebaseConector;
import ch.bbbaden.m335.rezepteverwaltung.tools.DataHolder;
import ch.bbbaden.m335.rezepteverwaltung.tools.FileMaker;
import ch.bbbaden.m335.rezepteverwaltung.tools.Toaster;

public class MainActivity extends AppCompatActivity {
    public static Context context;


    EditText editMail;
    EditText editPassword;
    Button btnEnter;
    Button btnSignUp;
    ProgressBar progressBar;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        auth = FirebaseAuth.getInstance();


        btnEnter = findViewById(R.id.btnMainLogin);
        editMail = findViewById(R.id.editMainMail);
        editPassword = findViewById(R.id.editMainPasswort);
        progressBar = findViewById(R.id.progressBar);
        btnSignUp = findViewById(R.id.btnMainSignUp);

        if (auth.getCurrentUser() != null) {
            getUserFromFiles(auth.getUid());
            startActivity(new Intent(this, MenuActivity.class));
            finish();
        }

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseConector conector = new FirebaseConector();
                conector.getAllUsers();
                startActivity(new Intent(context, SignupActivity.class));
            }
        });
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputEmail = editMail.getText().toString();
                final String inputPassword = editPassword.getText().toString();

                if (TextUtils.isEmpty(inputEmail)) {
                    new Toaster(getApplicationContext(), "Enter Email address!", 1);
                    return;
                }

                if (TextUtils.isEmpty(inputPassword)) {
                    new Toaster(getApplicationContext(), "Enter Password!", 1);
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(inputEmail, inputPassword).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        //progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (inputPassword.length() < 6) {
                                editPassword.setError("Passwort zu kurz");
                            } else {
                                new Toaster(getApplicationContext(), "Login Fehler", 1);
                            }
                        } else {
                            getUserFromFiles(auth.getCurrentUser().getEmail());
                            startActivity(new Intent(context, MenuActivity.class));
                            finish();
                        }
                    }
                });
            }
        });


    }

    private void getUserFromFiles(String userMail) {
        DataHolder.getInstance().setUser(DatabaseConector.getUserByMail(userMail));

    }
}