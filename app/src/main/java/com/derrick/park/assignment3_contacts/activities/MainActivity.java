package com.derrick.park.assignment3_contacts.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.derrick.park.assignment3_contacts.R;
import com.derrick.park.assignment3_contacts.models.Contact;
import com.derrick.park.assignment3_contacts.models.ContactList;
import com.derrick.park.assignment3_contacts.network.ContactClient;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final int TEXT_REQUEST = 1;
    private ArrayList<Contact> mContactList;
    public static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private ContactListAdapter mAdapter;
    private EditText mNameReply;
    private EditText mPhoneReply;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Call<ContactList> call = ContactClient.getContacts(10);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        call.enqueue(new Callback<ContactList>() {
            @Override
            public void onResponse(Call<ContactList> call, Response<ContactList> response) {
                if (response.isSuccessful()) {
                     mContactList = response.body().getContactList();
                     mAdapter = new ContactListAdapter(getApplicationContext(), mContactList);
                     recyclerView.setAdapter(mAdapter);
                     for(Contact contact: mContactList) {
                         Log.d(TAG, "onResponse: " + mContactList.size());
                         Log.d(TAG, "onResponse: " + contact);
                     }
                }
            }

            @Override
            public void onFailure(Call<ContactList> call, Throwable t) {
                // Error Handling

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        mNameReply = findViewById(R.id.firstLastName);
        mPhoneReply = findViewById(R.id.phoneNumber);
        if (id == R.id.add_menu) {
            Intent intent = new Intent(MainActivity.this, AddContact.class);
//            startActivity(intent);//
            startActivityForResult(intent, TEXT_REQUEST);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TEXT_REQUEST) {
            if (resultCode == RESULT_OK) {
                String reply = data.getStringExtra(AddContact.EXTRA_REPLY);
                mPhoneReply.setText(View.VISIBLE);
                mNameReply.setText(View.VISIBLE);
            }
        }
    }
}
