package com.cmps121.sam.androidfirebasedbrealtimenew;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTeacherFragment extends Fragment {

    EditText teacherAddEditText;
    Button currTopicAddBtn;
    String newTitle;
    View v;

    //Firebase declerations
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_teacheradd, container, false);
        teacherAddEditText = (EditText) v.findViewById(R.id.teacherAddEditText);
        currTopicAddBtn = (Button) v.findViewById(R.id.currTopicAddBtn);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Topics");
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        currTopicAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newTitle = teacherAddEditText.getText().toString();
                teacherAddEditText.setText("");
                PostTopic(newTitle);
            }
        });
    }

    private void PostTopic(String newTitle) {
        databaseReference.child("NewTopic").setValue(newTitle); //Use this method to create unique id of comment
        databaseReference.child("NoUnderstand").setValue("0");
        Toast.makeText(getContext(),"Added",Toast.LENGTH_SHORT).show();

    }
}
