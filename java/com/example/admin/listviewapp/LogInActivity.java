package com.example.admin.listviewapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogIn;
    private Button btnSignUp;
    private EditText edtEmail;
    private EditText edtPwd;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private String email, pwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_activityy);
        btnLogIn=(Button)findViewById(R.id.btn_login);
        btnLogIn.setOnClickListener(this);
        btnSignUp=(Button)findViewById(R.id.btn_signup);
        btnSignUp.setOnClickListener(this);

        edtEmail=(EditText)findViewById(R.id.edt_email_for_login);
        edtPwd=(EditText)findViewById(R.id.edt_pwd_for_login);

        mAuth=FirebaseAuth.getInstance();
    }

    protected void onStart(){
        super.onStart();
        // mUser=mAuth.getCurrentUser();
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.btn_login:

               email=edtEmail.getText().toString();
               pwd=edtPwd.getText().toString();

               if(!email.equals("")&& !pwd.equals("")){
                   mAuth.signInWithEmailAndPassword(email, pwd)
                           .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                               @Override
                               public void onComplete(@NonNull Task<AuthResult> task) {
                                   if (task.isSuccessful()) {
                                       edtPwd.setText("");
                                       edtEmail.setText("");
                                       mUser = mAuth.getCurrentUser();
                                       Intent intent =new Intent(LogInActivity.this, ListViewActivity.class);
                                      intent.putExtra("userId", mUser.getUid());

                                       startActivity(intent);

                                   } else {
                                       // If sign in fails, display a message to the user.
                                       Toast.makeText(LogInActivity.this, "Authentication failed.",
                                               Toast.LENGTH_SHORT).show();
                                   }
                               }
                           });
               }


                    break;
            case R.id.btn_signup:
                 edtPwd.setText("");
                 edtEmail.setText("");
                 Intent intent = new Intent(LogInActivity.this, SignUpActivity.class );
                 startActivity(intent);
                 break;
        }
    }

}
