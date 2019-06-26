package com.cutein.usermanagement.fragments;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cutein.usermanagement.R;

import com.cutein.usermanagement.models.Users;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class BlankFragment extends Fragment {
    private int mMonth, mYear, mDay;
    String value,startdate,enddate;
    Spinner spinner;
    RecyclerView recyclerView;
    private DatabaseReference UsersRef;
    Query query;
    EditText choice,date1,date2;
    Button search;



    public BlankFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_blank, container, false);
        spinner = rootView.findViewById(R.id.spinner1);
        choice = rootView.findViewById(R.id.choice);
        search = rootView.findViewById(R.id.search_btn);
        date1 = rootView.findViewById(R.id.date1);
        date2 = rootView.findViewById(R.id.date2);

        date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                final DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                date2.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
                int coMonth = c.get(Calendar.MONTH);
                int coDay = c.get(Calendar.DAY_OF_MONTH);



            }
        });
        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                final DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                date1.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
                int coMonth = c.get(Calendar.MONTH);
                int coDay = c.get(Calendar.DAY_OF_MONTH);

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                search();

            }
        });
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerviewid);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }



    private void search() {

        value = choice.getText().toString();
        startdate =date1.getText().toString();
        enddate =date2.getText().toString();
        if (TextUtils.isEmpty(value)) {
            Toast.makeText(getActivity(),"Please enter Employee id",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(startdate)) {
            Toast.makeText(getActivity(),"Please enter start date",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(enddate)) {
            Toast.makeText(getActivity(),"Please enter End date",Toast.LENGTH_SHORT).show();
            return;
        }
        employee_serch(value);
    }

    private void employee_serch(String value) {


      //  Query firebaseSearchQuery = mUserDatabase.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff");
        query = FirebaseDatabase.getInstance().getReference().child("Employees")
                .orderByChild("Date")
                .startAt(startdate)
                .endAt(enddate);

        FirebaseRecyclerOptions<Users> options =
                new FirebaseRecyclerOptions.Builder<Users>()
                        .setQuery(query, Users.class)
                        .build();

        FirebaseRecyclerAdapter<Users, FindFriendViewHolder> adapter =
                new FirebaseRecyclerAdapter<Users, FindFriendViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull FindFriendViewHolder holder, final int position, @NonNull Users model) {
                        holder.userName.setText(" id:"+model.getId());
                        holder.userStatus.setText(model.getName());
                        holder.userdate.setText("date:"+model.getDate());
                        holder.usersalary.setText("Amount:"+model.getSalary());
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                String visit_user_id = getRef(position).getKey();
//
//                                Intent profileIntent = new Intent(getContext(), StartActivity.class);
//                                profileIntent.putExtra("visit_user_id", visit_user_id);
//                                startActivity(profileIntent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public FindFriendViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout, viewGroup, false);
                        FindFriendViewHolder viewHolder = new FindFriendViewHolder(view);
                        return viewHolder;
                    }
                };

        recyclerView.setAdapter(adapter);

        adapter.startListening();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // UsersRef = FirebaseDatabase.getInstance().getReference().child("Employees");


        List<String> list = new ArrayList<String>();
        list.add("id ");
        list.add("Name");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.item_spinnergreen, R.id.spinner_text1, list);
        spinner.setAdapter(dataAdapter);

    }

//    @Override
//    public void onStart() {
//        super.onStart();
//
//
//
//    }


    public static class FindFriendViewHolder extends RecyclerView.ViewHolder
    {
        TextView userName, userStatus,userdate,usersalary;
        public FindFriendViewHolder(@NonNull View itemView)
        {
            super(itemView);
            userName = itemView.findViewById(R.id.status_text);
            userStatus = itemView.findViewById(R.id.name_text);
            userdate = itemView.findViewById(R.id.user_date);
            usersalary = itemView.findViewById(R.id.user_salary);
        }
    }


}