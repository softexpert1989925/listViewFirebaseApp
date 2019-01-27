package com.example.admin.listviewapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.SingleLineTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.model.CellModel;
import com.mycomponents.UserAdapter;

import java.lang.reflect.Array;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;

public class ListViewActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView listView;
    private EditText editText;
    private Button btn;

    private FirebaseAuth mAuth;
    public FirebaseFirestore db;

    public String userId;

    public ArrayList<CellModel> arrayOfUsers;

    private int count=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        btn=(Button)findViewById(R.id.btn_action_add);
        btn.setOnClickListener(this);

        editText=(EditText)findViewById(R.id.edt_action_item);

        mAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();

        listView=(ListView)findViewById(R.id.listview);


    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        userId=intent.getStringExtra("userId");
          updateUI();

    }



    @Override
    public void onClick(View v) {
      String name=editText.getText().toString();
      if(name!=null){
          Calendar calendar=Calendar.getInstance();
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
          String  date = dateFormat.format(calendar.getTime());

          Map<String, Object> docData = new HashMap<>();
          docData.put("name", name);
          docData.put("date", date);

          CollectionReference colRef = db.collection("Users").document(userId).collection("actions");
          colRef.document("action"+Integer.toString(count++)).set(docData)
                  .addOnSuccessListener(new OnSuccessListener<Void>() {
                          @Override
                          public void onSuccess(Void aVoid) {
                              updateUI();

                          }
                      })
                  .addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception e) {

                      }
                  });



      }
    }

    public void updateUI(){

        CollectionReference colRef = db.collection("Users").document(userId).collection("actions");

        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    // create Array of CellModel from firebase

                    arrayOfUsers = new ArrayList<CellModel>();
                    String name, date ;


                    for (QueryDocumentSnapshot document : task.getResult()) {

                        count++;

                        name=document.getString("name");
                        date=document.getString("date");

                        CellModel model=new CellModel(date, name);
                        arrayOfUsers.add(model);
                    }

                    // my UserAdapter initialize.

                    UserAdapter userAdapter =new UserAdapter(ListViewActivity.this,  arrayOfUsers);

                    // set listview with UserAdapter.

                    listView.setAdapter(userAdapter);

                    editText.setText("");

                } else {
                    //Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }
}
