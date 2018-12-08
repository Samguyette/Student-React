package com.cmps121.sam.androidfirebasedbrealtimenew;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ListStudentFragment extends Fragment {
    View v;
    RecyclerView recyclerView;

    EditText edt_title;
    EditText edt_content;
    Button btn_post;
    Post selectedPost;
    String currKey;
    String selectedKey;
    Integer currUpVoteValue;
    private LinearLayoutManager mLayoutManager;

    //Firebase declerations
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    FirebaseRecyclerOptions<Post> options;
    FirebaseRecyclerAdapter<Post,MyRecyclerViewHolder> adapter;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_studentlist, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

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
        recyclerView.setAdapter(adapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                displayComment();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        displayComment();

    }

    public void onStop(){
        if(adapter != null){
            adapter.stopListening();
        }
        super.onStop();
    }
    private void displayComment(){

        options = new FirebaseRecyclerOptions.Builder<Post>().setQuery(databaseReference, Post.class).build();
        adapter = new FirebaseRecyclerAdapter<Post, MyRecyclerViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull MyRecyclerViewHolder holder, final int position, @NonNull final Post model) {
                holder.txt_title.setText(model.getTitle());
                holder.txt_comment.setText(model.getContent());
                int temp = model.getUpVotes();
                holder.txt_upVoteCount.setText(String.valueOf(temp));
                final String tempTitle = holder.txt_title.getText().toString();
                final String tempQuestion = holder.txt_comment.getText().toString();
                final int tempUpVotes = holder.upVotes;

                holder.upVoteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectedPost = model;

                        //what i changed
//                        currUpVoteValue = model.getUpVotes();
//                        currKey = adapter.getRef(position).getKey();
//                        databaseReference.child(currKey).child("upVotes").setValue(currUpVoteValue+1);

                        //what you had
                        selectedKey = getSnapshots().getSnapshot(position).toString();
                        databaseReference.child(selectedKey).setValue(new Post(tempTitle, tempQuestion, tempUpVotes));
                        adapter.getRef(position).removeValue();

                        Toast.makeText(getActivity(),"Up Voted",Toast.LENGTH_SHORT).show();
                        displayComment();
                    }
                });
            }


            @NonNull
            @Override
            public MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View itemView = LayoutInflater.from(getContext()).inflate(R.layout.post_item, viewGroup, false);
                return new MyRecyclerViewHolder(itemView);

            }
        };


        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}
