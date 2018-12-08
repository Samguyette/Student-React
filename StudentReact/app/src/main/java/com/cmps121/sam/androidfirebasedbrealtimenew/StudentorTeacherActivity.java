package com.cmps121.sam.androidfirebasedbrealtimenew;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StudentorTeacherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentor_teacher);
    }

    public void gotoStudentLogin(View view) {
        Intent gotoStudentL = new Intent(StudentorTeacherActivity.this, StudentLogin.class);
        startActivity(gotoStudentL);
    }

    public void gotoTeacherLogin(View view) {
        Intent gotoTeacherL = new Intent(StudentorTeacherActivity.this, TeacherLogin.class);
        startActivity(gotoTeacherL);
    }

}
