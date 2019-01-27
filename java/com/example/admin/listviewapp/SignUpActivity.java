
package com.example.admin.listviewapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.*;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    public EditText edtEmail;
    public EditText edtPwd;
    public EditText edtActionName;
    private Button btnRegister;
    public FirebaseAuth mAuth;
    public FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtEmail=(EditText)findViewById(R.id.edt_email_for_signup);
        edtPwd=(EditText)findViewById(R.id.edt_pwd_for_signup);
        edtActionName=(EditText)findViewById(R.id.edt_action_name);

        btnRegister=(Button)findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);

        mAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();

    }
    @Override
    protected void onStart(){
        super.onStart();

    }

    @Override
    public void onClick(View v) {
        String email=edtEmail.getText().toString();
        String pwd=edtPwd.getText().toString();

                mAuth.createUserWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                          // add action name into firestore
                            Map<String, Object> user = new HashMap<>();
                            String actionName=edtActionName.getText().toString();
                            String email=edtEmail.getText().toString();
                            String pwd=edtPwd.getText().toString();
                            user.put("actionName", actionName);
                            user.put("email", email);
                            user.put("password", pwd);

                            String doc=mAuth.getUid();

                            if(doc!=null){
                                db.collection("Users").document(doc)
                                        .set(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                finish();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(SignUpActivity.this, "Add data Failed!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            makeText(SignUpActivity.this, "Authentication failed.",
                                    LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
