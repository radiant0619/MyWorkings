package com.radiant.myfires.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.radiant.myfires.Model.ChatMessage;
import com.radiant.myfires.R;

public class Chats extends AppCompatActivity {
    private int SIGN_IN_REQUEST_CODE;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseAuth.AuthStateListener authStateListener;
    private AlertDialog.Builder builder;
    private FloatingActionButton fab;
    private FirebaseListAdapter<ChatMessage> adapter;
    private FirebaseDatabase mFireData;
    private EditText input;


    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null)
            auth.removeAuthStateListener(authStateListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = (EditText) findViewById(R.id.msgview);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                builder = new AlertDialog.Builder(getApplicationContext());
//                builder.setTitle("Sign out Confirmation")
//                        .setCancelable(true)
//                        .setMessage("Are you want to Sign out?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                auth.signOut();
//                            }
//                        });
//                AlertDialog dlg = builder.create();
//                dlg.show();


                FirebaseDatabase.getInstance().getReference("Message")
                        .push()
                        .setValue(new ChatMessage(auth.getCurrentUser().getDisplayName(), input.getText().toString()));
                input.setText("");
            }

        });
        auth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(Chats.this, Login.class));
                    finish();
                }
                else {
                    displayChatMessages();
                }
            }
        };
    }

    private void displayChatMessages() {

        ListView listMessages=(ListView) findViewById(R.id.list_of_messages);
        adapter= new FirebaseListAdapter<ChatMessage>(this,ChatMessage.class,
                R.layout.message, FirebaseDatabase.getInstance().getReference("Message")) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {

                TextView txt_user=(TextView)v.findViewById(R.id.txt_user);
                TextView txt_msg=(TextView)v.findViewById(R.id.txt_msg);
                TextView txt_date=(TextView)v.findViewById(R.id.txt_date);
//                Log.i("Value",model.getMsgUser());
                txt_date.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMsgTime()));
                txt_msg.setText(model.getMsgDesc());
                txt_user.setText(model.getMsgUser());
            }
        };
        listMessages.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
//        return super.onOptionsItemSelected(item);
        builder = new AlertDialog.Builder(getApplicationContext());
        AlertDialog dlg = builder.create();
        switch (item.getItemId()) {
            case R.id.action_logOut:
//                builder.setTitle("Sign out Confirmation")
//                        .setCancelable(true)
//                        .setMessage("Are you want to Sign out?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                auth.signOut();
//                            }
//                        });
//                dlg.show();
                auth.signOut();
                break;

            case R.id.action_pwd_change:
                LinearLayout linearLayout = new LinearLayout(Chats.this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                TextInputLayout txtInput = new TextInputLayout(Chats.this);
                final EditText text = new EditText(Chats.this);

                txtInput.addView(txtInput);

                linearLayout.addView(txtInput);
                builder.setTitle("Change Password")
                        .setCancelable(true)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseUser user2 = auth.getCurrentUser();
                                user2.updatePassword(text.getText().toString()).
                                        addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(getApplicationContext(), "Password change successfully. Please login with new Password.",
                                                        Toast.LENGTH_LONG)
                                                        .show();
                                                startActivity(new Intent(getApplicationContext(), Login.class));
                                                finish();
                                            }
                                        });
                            }
                        });
                builder.setView(linearLayout);

                AlertDialog dlg1 = builder.create();
                dlg1.show();
                break;
        }
        return true;
    }
}
