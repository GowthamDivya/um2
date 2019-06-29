package com.cutein.usermanagement.fragments;


import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.cutein.usermanagement.R;
import com.cutein.usermanagement.util.GmailSender;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;
import java.util.Queue;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {

    String emailsCommaSeparated;
    Queue query;
    String list;
    String email_list;
    private final static  String TAG = "NotificationFragment";
    private DatabaseReference mdatabase;
    private ArrayList<String>  useremails = new ArrayList<>();
    EditText  sendToEmail, subject, text;
    Button sendEmailButton;
    String myEmailString, passString, sendToEmailString, subjectString, textString;
    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_notification, container, false);


        sendToEmail = (EditText) v.findViewById(R.id.sendToEmaileditText);
        subject = (EditText) v.findViewById(R.id.subjectEditText);
        text = (EditText) v.findViewById(R.id.textEditText);
        sendEmailButton = (Button) v.findViewById(R.id.button);
        final SendEmailTask sendEmailTask = new SendEmailTask();
        sendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myEmailString = "gowthamandriod81431@gmail.com";
                passString ="Passw0rd@2019";
                sendToEmailString = sendToEmail.getText().toString();
                subjectString = subject.getText().toString();
                textString = text.getText().toString();
                sendEmailTask.execute();
            }
        });

        return  v;
    }
    class SendEmailTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("Email sending", "sending start");
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                GmailSender sender = new GmailSender(myEmailString, passString);
                //subject, body, sender, to
                sender.sendMail(subjectString,
                        textString,
                        myEmailString,
                        emailsCommaSeparated);

                Log.i("Email sending", "send");
            } catch (Exception e) {
                Log.i("Email sending", "cannot send");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
          mdatabase = FirebaseDatabase.getInstance().getReference().child("Employees");
          mdatabase.addChildEventListener(new ChildEventListener() {
              @RequiresApi(api = Build.VERSION_CODES.O)
              @Override
              public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                  Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    String email = (String) map.get("Email");
                  Log.d(TAG, "emails all: "+email);
                    useremails.add(email);
                    Log.d(TAG, "Value is: " + useremails);
                     email_list  =  useremails.toString();
                  Log.d(TAG, "EmailList:"+email_list);
                    //String csv = map.join(",",map);
                 // User user = dataSnapshot.getValue(User.class);
                  //useremails.add(value);
                   emailsCommaSeparated = String.join(",", useremails);

              }
              @Override
              public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

              }

              @Override
              public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

              }

              @Override
              public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

              }

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {

              }
          });



    }
}
