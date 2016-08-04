package com.example.admin.hackuapp;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

public class Recording extends AppCompatActivity {

    WavRec wr=new WavRec();
    Api api=new Api(this);

    private DBAccesser dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);

        } else {
            Toast toast = Toast.makeText(this, "許可されないとアプリが実行できません", Toast.LENGTH_SHORT);
            toast.show();

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO,}, 1);
        }

        dba = new DBAccesser(this);

        CompoundButton toggle = (CompoundButton)findViewById(R.id.rec_switch);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 状態が変更された isChecked
                Toast.makeText(Recording.this, "isChecked : " + isChecked, Toast.LENGTH_SHORT).show();
                if(isChecked){
                    wr=new WavRec();
                    wr.rec_start();
                }else{
                    wr.rec_stop();
                    wr.convert();
                }
            }
        });

        findViewById(R.id.new_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // クリック時の処理
                wr.convert();
                api.setSubkey("705b6408172b465ebe0579742f062214");
                api.setFileName("rec.wav");
                api.newaccount();
//                api.enroll("https://api.projectoxford.ai/spid/v1.0/identificationProfiles/"+api.result.get(0)+"/enroll");
            }
        });


        findViewById(R.id.check_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // クリック時の処理
                api.setUrl("https://api.projectoxford.ai/spid/v1.0/identify");

                DBLine[] dbLines = dba.getAll();
                String[] IDs;

                if(dbLines != null) {
                    IDs =  new String[dbLines.length];
                    int i = 0;
                    for (DBLine row : dbLines) {
                        IDs[i++] = row.MS_PROFILE_ID;
                    }
                    api.setId(IDs);
                    //                api.setId(new String[]{ "9f5eb3d8-f0d9-40cf-83b6-e4f4870ca1a9","c9d4d5e5-708c-4f3c-9ddd-6c67b4b6a6cd","c9aad336-f824-49a3-be4e-c0aa5efa709d"});
                    api.setSubkey("705b6408172b465ebe0579742f062214");
                    api.setFileName("rec.wav");
                    api.identification();
                }else{
                    Toast.makeText(Recording.this, "DB is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
