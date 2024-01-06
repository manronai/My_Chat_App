package com.example.mychatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mychatapp.Adapters.MessageAdapter;
import com.example.mychatapp.Model.Chats;
import com.example.mychatapp.Model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    Toolbar toolbar;

    String friendid, message, myid;
    CircleImageView imageViewOnToolbar;
    TextView usernameonToolbar;

    FirebaseUser firebaseUser;

    EditText et_message;
    Button send;

    DatabaseReference reference;

    ArrayList<Chats> chatsList;
    MessageAdapter messageAdapter;
    RecyclerView recyclerView;
    ValueEventListener seenlistener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        toolbar = findViewById(R.id.toolbar_message);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerview_messages);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);


        send = findViewById(R.id.send_messsage_btn);
        et_message = findViewById(R.id.edit_message_text);

        imageViewOnToolbar = findViewById(R.id.profile_image_toolbar_message);
        usernameonToolbar = findViewById(R.id.username_ontoolbar_message);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        myid = firebaseUser.getUid(); // my id or the one who is loggedin

        friendid = getIntent().getStringExtra("friendid"); // retreive the friendid when we click on the item
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(friendid);
         reference.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 Users users = snapshot.getValue(Users.class);

                 assert users != null;
                 usernameonToolbar.setText(users.getUsername()); // set the text of the user on textivew in toolbar

                 if (users.getImageURL().equals("default")) {

                     imageViewOnToolbar.setImageResource(R.drawable.user);
                 }
                 readMessages(myid, friendid, users.getImageURL());

             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });


        et_message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                if (s.toString().length() > 0) {

                    send.setEnabled(true);

                } else {

                    send.setEnabled(false);


                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String text = et_message.getText().toString();

                if (!text.startsWith(" ")) {
                    et_message.getText().insert(0, " ");

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                message = et_message.getText().toString();

                sendMessage(myid, friendid, message);

                et_message.setText(" ");


            }
        });
    }

    private void readMessages(final String myid, final String friendid, final String imageURL) {

        chatsList = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatsList.clear();

                for (DataSnapshot ds: snapshot.getChildren()) {

                    Chats chats = ds.getValue(Chats.class);

                    assert chats != null;
                    if (chats.getSender().equals(myid) && chats.getReciever().equals(friendid) ||
                            chats.getSender().equals(friendid) && chats.getReciever().equals(myid)) {

                        chatsList.add(chats);
                    }



                }
                if(chatsList.size() != 0){
                messageAdapter = new MessageAdapter(MessageActivity.this, chatsList, "Default");
                if(messageAdapter != null){
                recyclerView.setAdapter(messageAdapter);}}

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void sendMessage(final String myid, final String friendid, final String message) {


        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();



        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", myid);
        hashMap.put("reciever", friendid);
        hashMap.put("message", message);
        hashMap.put("isseen", false);

        reference.child("Chats").push().setValue(hashMap);



    }



}