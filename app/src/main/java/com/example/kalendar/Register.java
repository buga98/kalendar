package com.example.kalendar;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Register extends AppCompatActivity {
    EditText mimeiprezime, memail, mlozinka, mbrojmobitela;
    Button mregistracija;
    TextView mlogin;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mimeiprezime = findViewById(R.id.imeiprezime);
        memail = findViewById(R.id.email);
        mlozinka = findViewById(R.id.lozinka);
        mbrojmobitela = findViewById(R.id.brojmobitela);
        mregistracija = findViewById(R.id.registracija);
        mlogin = findViewById(R.id.login);
        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null )
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        mregistracija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = memail.getText().toString().trim();
                String lozinka = mlozinka.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    memail.setError("Email adresa je obavezna");
                    return;
                }
                if (TextUtils.isEmpty(lozinka)) {
                    mlozinka.setError("Lozinka je obavezna");
                    return;
                }
                if (lozinka.length() < 6) {
                    mlozinka.setError("Lozinka mora sadrzavati vise od 6 znakova");
                }
fAuth.createUserWithEmailAndPassword(email,lozinka).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful())
        {
            Toast.makeText(Register.this, "Novi korisnik je napravljen", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        else
        {
            Toast.makeText(Register.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
});
            }

        });

    }
}