package com.cmps121.sam.androidfirebasedbrealtimenew;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class TeacherTopicActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_topic);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("EDMT_FIREBASE");

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeTeacherFragment()).commit();

    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    android.support.v4.app.Fragment selectedFragment = null;
                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new HomeTeacherFragment();
                            break;
                        case R.id.nav_add:
                            selectedFragment = new AddTeacherFragment();
                            break;
                        case R.id.nav_list:
                            selectedFragment = new ListTeacherFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
    };

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
            //ListTeacherFragment.displayComment();
            Toast.makeText(this,"Updated",Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.ClearAllBtn) {
            //databaseReference.removeValue();
            //Toast.makeText(TeacherClassroom.this,"All Cleared",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteAllQues(View view) {
        databaseReference.removeValue();
        Toast.makeText(getApplicationContext(),"All Cleared",Toast.LENGTH_SHORT).show();
    }
}
