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

public class HomeStudentFragment extends Fragment {
    //Firebase declerations
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    View v;
    TextView studentTopicTextView;
    Button reviewBtn;


    FirebaseRecyclerOptions<Post> options;
    FirebaseRecyclerAdapter<Post,MyRecyclerViewHolder> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_studenthome, container, false);
        studentTopicTextView = (TextView) v.findViewById(R.id.studentTopicTextView);
        reviewBtn = (Button) v.findViewById(R.id.reviewBtn);

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
                studentTopicTextView.setText(newTopic);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("NoUnderstand").setValue("1");
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
