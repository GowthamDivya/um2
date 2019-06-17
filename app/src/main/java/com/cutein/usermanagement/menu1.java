package com.cutein.usermanagement;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.cutein.usermanagement.models.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;



public class menu1  extends Fragment implements View.OnClickListener{


    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;

    private ViewPager mViewPager;
    //private SectionsPagerAdapter mSectionsPagerAdapter;

    private DatabaseReference mUserRef;

    private TabLayout mTabLayout;


    private EditText editTextid;
    private EditText editTextusername;
    private EditText editTextdate;
    private EditText editTextsalary;
    private Button buttonSubmit;
    private ProgressDialog progressDialog;
    FirebaseDatabase database;
    private DatabaseReference mDatabase;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment1,container,false);

    }
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Menu");

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        }
        editTextid = (EditText) view.findViewById(R.id.editText_id);
        editTextusername = (EditText) view.findViewById(R.id.ed_user_name);
        editTextdate = (EditText) view.findViewById(R.id.ed_date);
        editTextsalary = (EditText) view.findViewById(R.id.ed_salary);
        buttonSubmit = (Button) view.findViewById(R.id.proSignup);
        progressDialog = new ProgressDialog(getActivity());
        buttonSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        submitPost();
    }

    private void submitPost() {

        final String id = editTextid.getText().toString().trim();
        final String name = editTextusername.getText().toString().trim();
        final String date = editTextdate.getText().toString().trim();
        final String salary = editTextsalary.getText().toString().trim();

        if (TextUtils.isEmpty(id)) {
//            Toast.makeText(this, "Please enter Employee id", Toast.LENGTH_LONG).show();
            Toast.makeText(getActivity(),"Please enter Employee id",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(name)) {

            Toast.makeText(getActivity(),"Please enter Employee Name",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(date)) {
//            Toast.makeText(this, "Please enter Date", Toast.LENGTH_LONG).show();
            Toast.makeText(getActivity(),"Please enter Date",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(salary)) {

//            Toast.makeText(this, "Please enter Salary", Toast.LENGTH_LONG).show();
            Toast.makeText(getActivity(),"Please enter Salary",Toast.LENGTH_SHORT).show();
            return;
        }
        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Submitting Please Wait...");
        progressDialog.show();
        setEditingEnabled(false);

        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = current_user.getUid();


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Employees").child(id);
        String device_token = FirebaseInstanceId.getInstance().getToken();
        String key = mDatabase.child("posts").push().getKey();
        Post post = new Post(userId,id,name,date,salary);

        Map<String, Object> postValues = post.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + key, postValues);
//        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
        mDatabase.updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(getActivity(),"Submited Sucessfully!",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });


    }

    private void setEditingEnabled(boolean enabled) {
        editTextid.setEnabled(enabled);
        editTextusername.setEnabled(enabled);
        editTextdate.setEnabled(enabled);
        editTextsalary.setEnabled(enabled);
        if (enabled) {
            buttonSubmit.setVisibility(View.VISIBLE);
        } else {
            buttonSubmit.setVisibility(View.GONE);
        }
    }
}