package com.example.admin.hackuapp;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class EditCard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card);

        //値の受け取り
        Intent intent = getIntent();
        String oldName = intent.getStringExtra( "oldName" );
        String oldPhone = intent.getStringExtra( "oldPhone" );
        String oldEmail = intent.getStringExtra( "oldEmail" );
        String oldComp = intent.getStringExtra( "oldComp" );
        String oldDep = intent.getStringExtra( "oldDep" );
        String oldPos = intent.getStringExtra( "oldPos" );

        String profileId = intent.getStringExtra( "profileId" );

        EditText input = (EditText)this.findViewById(R.id.editName);
        input.setText(oldName);
        input = (EditText)this.findViewById(R.id.editPhone);
        input.setText(oldPhone);
        input = (EditText)this.findViewById(R.id.editEmail);
        input.setText(oldEmail);
        input = (EditText)this.findViewById(R.id.editCompany);
        input.setText(oldComp);
        input = (EditText)this.findViewById(R.id.editDepart);
        input.setText(oldDep);
        input = (EditText)this.findViewById(R.id.editPosit);
        input.setText(oldPos);

        System.out.println("PROFILEID: "+profileId);
    }
}
