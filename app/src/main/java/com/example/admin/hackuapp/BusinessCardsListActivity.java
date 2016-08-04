package com.example.admin.hackuapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.admin.hackuapp.dummy.DummyContent;

import java.util.List;

/**
 * An activity representing a list of BusinessCards. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link BusinessCardsDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class BusinessCardsListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    public static final int NEW_ITEM = 0;
    private PhoneBookContent pbContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_businesscards_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            toolbar.setTitle(getTitle());
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClassName("com.example.admin.hackuapp", "com.example.admin.hackuapp.Recording");
                    startActivity(intent);
                }
            });
        }

        requestDBPermission();

        View recyclerView = findViewById(R.id.businesscards_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.businesscards_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // メニューの要素を追加して取得
        MenuItem actionItem = menu.add(0, NEW_ITEM, 0, "Action Button Input Icon");
        // アイコンを設定
        actionItem.setIcon(android.R.drawable.ic_input_add);

        // SHOW_AS_ACTION_ALWAYS:常に表示
        actionItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        intent.setClassName("com.example.admin.hackuapp", "com.example.admin.hackuapp.Registation");
        startActivity(intent);
        return true;
    }
    */

    private void requestDBPermission(){
        // Here, thisActivity is the current activity
        int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {

        pbContent = new PhoneBookContent();
        Cursor content_c = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,}, null, null,null);


        if (content_c != null && content_c.getCount() > 0){
            try {
                content_c.moveToFirst();

                Uri lookupUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, content_c.getString(1));


                do {
                    pbContent.addItem(new PhoneBookContent.PhoneBookItem(
                            content_c.getString(0),
                            content_c.getString(1),
                            getPhoneNumber(content_c.getString(0)),
                            getEmailAddress(content_c.getString(0)),
//                            c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Organization.COMPANY)) + " " +
//                                    c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DEPARTMENT)) + " " +
//                                    c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Organization.COMPANY))
                            ""
                    ));
                    Log.d("DB: ", getOrganization(content_c.getString(0)));
                } while (content_c.moveToNext());
            } catch (Exception e){
                e.printStackTrace();
            } finally{
                content_c.close();
            }
        }


        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(pbContent.getItems()));
    }

    private String getPhoneNumber(String id){
        String phones = "";
        Cursor c = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id,
                null,
                null
        );
        if(c != null && c.getCount() > 0){
            try {
                c.moveToFirst();
                do {
                    phones += c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) + " ";
                } while (c.moveToNext());
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                c.close();
            }
            c.close();
        }

        return phones;
    }

    private String getEmailAddress(String id){
        String addresses = "";
        Cursor c = getContentResolver().query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Email.ADDRESS},
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=" + id,
                null,
                null
        );
        if (c != null && c.getCount() > 0) {
            try {
                c.moveToFirst();
                do {
                    addresses += c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)) + " ";
                } while (c.moveToNext());
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                c.close();
            }
        }
        return addresses;
    }

    private String getOrganization(String id){
        String org = "";
        Cursor c = getContentResolver().query(
                ContactsContract.Data.CONTENT_URI,
                null,
                ContactsContract.Data.RAW_CONTACT_ID + " = " + id + " AND " +
                ContactsContract.Data.MIMETYPE + " = " + ContactsContract.CommonDataKinds.Organization.MIMETYPE,
                //ContactsContract.Data.RAW_CONTACT_ID + "=" + id,
                new String[] {id, ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE},
                null
        );
        if(c != null && c.getCount() > 0){
            try {
                c.moveToFirst();
                org = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DEPARTMENT));
            }catch(Exception e){
                e.printStackTrace();
            }finally {
                c.close();
            }
        }

        return org;
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<PhoneBookContent.PhoneBookItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<PhoneBookContent.PhoneBookItem> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.businesscards_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).name);
            holder.mContentView.setText(mValues.get(position).belongs);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(BusinessCardsDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        BusinessCardsDetailFragment fragment = new BusinessCardsDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.businesscards_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, BusinessCardsDetailActivity.class);
                        intent.putExtra(BusinessCardsDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public PhoneBookContent.PhoneBookItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.name);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
