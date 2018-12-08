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

public class AddStudentFragment extends Fragment {

    EditText edt_title;
    EditText edt_content;
    Button btn_post;
    View v;
    //Firebase declerations
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_studentadd, container, false);
        edt_content = (EditText) v.findViewById(R.id.edt_content);
        edt_title = (EditText) v.findViewById(R.id.edt_title);
        btn_post = (Button) v.findViewById(R.id.btn_post);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("EDMT_FIREBASE");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //result for button pressed
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postComment();
                edt_title.setText("");
                edt_content.setText("");
            }
        });
    }

    private void postComment(){
        String title = edt_title.getText().toString();
        String content = edt_content.getText().toString();

        Post post  = new Post(title,content,0);

        databaseReference.push().setValue(post); //Use this method to create unique id of comment
        Toast.makeText(getContext(),"Added",Toast.LENGTH_SHORT).show();

    }
}
