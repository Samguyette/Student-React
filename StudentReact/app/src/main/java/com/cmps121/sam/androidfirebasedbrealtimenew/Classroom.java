package com.cmps121.sam.androidfirebasedbrealtimenew;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Classroom extends AppCompatActivity {

    EditText edt_title;
    EditText edt_content;
    Button btn_post;
    RecyclerView recyclerView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classroom);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        edt_content = (EditText) findViewById(R.id.edt_content);
        edt_title = (EditText) findViewById(R.id.edt_title);
        btn_post = (Button) findViewById(R.id.btn_post);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        LinearLayoutManager layoutManager = new LinearLayoutManager(Classroom.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("EDMT_FIREBASE");

        Query myTopPostsQuery = databaseReference.orderByChild("upVotes");
        //myTopPostsQuery.addValueEventListener(new ValueEventListener() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                displayComment();
            }

            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //result for button pressed
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_content.setText("");
                edt_title.setText("");
                postComment();
            }
        });


        displayComment();
    }

    protected void onStop(){
        if(adapter != null){
            adapter.stopListening();
        }
        super.onStop();
    }



    private void postComment(){
        String title = edt_title.getText().toString();
        String content = edt_content.getText().toString();

        Post post  = new Post(title,content,0);

        databaseReference.push().setValue(post); //Use this method to create unique id of comment

        adapter.notifyDataSetChanged();
    }

    private void displayComment(){

        options = new FirebaseRecyclerOptions.Builder<Post>().setQuery(databaseReference, Post.class).build();
        adapter = new FirebaseRecyclerAdapter<Post, MyRecyclerViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull MyRecyclerViewHolder holder, final int position, @NonNull final Post model) {
                holder.txt_title.setText(model.getTitle());
                holder.txt_comment.setText(model.getContent());
                final String tempTitle = holder.txt_title.getText().toString();
                final String tempQuestion = holder.txt_comment.getText().toString();
                final int tempUpVotes = holder.upVotes;

                holder.upVoteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectedPost = model;

                        //what i changed
                        //currUpVoteValue = model.getUpVotes();
                        //currKey = adapter.getRef(position).getKey();
                        //databaseReference.child(currKey).child("upVotes").setValue(currUpVoteValue+1);

                        //what you had
                        selectedKey = getSnapshots().getSnapshot(position).toString();
                        databaseReference.child(selectedKey).setValue(new Post(tempTitle, tempQuestion, tempUpVotes+1));
                        adapter.getRef(position).removeValue();

                        Toast.makeText(Classroom.this,"Up Voted",Toast.LENGTH_SHORT).show();
                        displayComment();
                    }
                });
            }


                    @NonNull
                    @Override
                    public MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View itemView = LayoutInflater.from(getBaseContext()).inflate(R.layout.post_item, viewGroup, false);
                        return new MyRecyclerViewHolder(itemView);

                    }
                };


        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.UpdateBtn){
            displayComment();
            Toast.makeText(Classroom.this,"Updated",Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.ClearAllBtn){
            Toast.makeText(Classroom.this,"You can't do that",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
