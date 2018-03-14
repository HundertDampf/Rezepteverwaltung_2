package ch.bbbaden.m335.rezepteverwaltung.activities;

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

import java.util.ArrayList;
import java.util.List;

import ch.bbbaden.m335.rezepteverwaltung.R;
import ch.bbbaden.m335.rezepteverwaltung.objects.User;
import ch.bbbaden.m335.rezepteverwaltung.services.DatabaseConector;
import ch.bbbaden.m335.rezepteverwaltung.services.FirebaseConector;
import ch.bbbaden.m335.rezepteverwaltung.tools.*;

public class SignupActivity extends AppCompatActivity {

    private EditText editEmail, editPassword, editUserName;
    private Button btnSignUp;
    private ProgressBar progressBar;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignUp = findViewById(R.id.btnSupSignup);
        editEmail = findViewById(R.id.editSupEmail);
        editUserName = findViewById(R.id.editSupUserName);
        editPassword = findViewById(R.id.editSupPassword);
        progressBar = findViewById(R.id.progressBar);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = editEmail.getText().toString().trim();
                String password = editPassword.getText().toString().trim();
                final String userName = editUserName.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    new Toaster(getApplicationContext(), "Enter email address!", 1);
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    new Toaster(getApplicationContext(), "Enter password!", 1);
                    return;
                }

                if (password.length() < 6) {
                    new Toaster(getApplicationContext(), "Password too short, enter minimum 6 characters!", 1);
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                new Toaster(SignupActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), 1);
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    new Toaster(SignupActivity.this, "Authentication failed." + task.getException(), 1);
                                } else {

                                    System.out.println("call listerner");
                                    generateUser();

                                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });

            }
        });
    }

    private void generateUser() {
        User user = new User();
        List<User> users = new ArrayList<>();
        FirebaseConector connect = new FirebaseConector();
        System.out.println("vor assign");
        users = DataHolder.getInstance().getUserListe();
        System.out.println("nachher");

        System.out.println("-" + (users.size() + 1));

        user.setUserName(editUserName.getText().toString());
        user.setUserShortId((users.size() + 1));
        user.setUserEmail(editEmail.getText().toString());
        DatabaseConector.addUser(user);
        DataHolder.getInstance().setUser(user);
        connect.addUserToFirebase(user, auth.getUid());
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}