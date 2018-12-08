package com.cmps121.sam.androidfirebasedbrealtimenew;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class HomeTeacherFragment extends Fragment {
    //Firebase declerations
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    View v;
    TextView teacherTopicTextView;
    TextView reviewClicksTextView;
    Button removeTopicBtn;


    FirebaseRecyclerOptions<Post> options;
    FirebaseRecyclerAdapter<Post,MyRecyclerViewHolder> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_teacherhome, container, false);
        teacherTopicTextView = (TextView) v.findViewById(R.id.teacherTopicTextView);
        reviewClicksTextView = (TextView) v.findViewById(R.id.reviewCkicksTextView);
        removeTopicBtn = (Button) v.findViewById(R.id.removeTopicBtn);

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
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String newTopic = dataSnapshot.child("NewTopic").getValue(String.class);
                teacherTopicTextView.setText(newTopic);
                String reviewClicks = dataSnapshot.child("NoUnderstand").getValue(String.class);
                reviewClicksTextView.setText(reviewClicks);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        removeTopicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("NewTopic").setValue("No Topic Added");
                databaseReference.child("NoUnderstand").setValue("");
            }
        });
    }

    public void onStop(){
        if(adapter != null){
            adapter.stopListening();
        }
        super.onStop();
    }
}
