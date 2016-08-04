package com.example.admin.hackuapp;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AndroidException;
import android.widget.EditText;

import java.util.ArrayList;

public class EditCard extends AppCompatActivity {

    private EditText editName;
    private EditText editPhone;
    private EditText editEmail;
    private EditText editCompany;
    private EditText editDepart;

    private DBAccesser dba;

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

    private Uri bookRegistration() throws AndroidException, OperationApplicationException {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        int rawContactInsertIndex = ops.size();

        ops.add(ContentProviderOperation.newInsert(ContactsContract.Contacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null).build());
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,
                        rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, editPhone.getText()).build());
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,
                        rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, editName.getText()).build());
        ContentProviderResult[]  res = getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);

        if (res != null) {
            return res[2].uri;
        }else{
            return null;
        }
    }
}
