package com.cmps121.sam.androidfirebasedbrealtimenew;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StudentLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
    }

    public void gotoMainActivity(View view) {
        Intent gotoClassroom = new Intent(StudentLogin.this, StudentMainActivity.class);
        startActivity(gotoClassroom);
    }
}
