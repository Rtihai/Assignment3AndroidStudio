package com.derrick.park.assignment3_contacts.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.derrick.park.assignment3_contacts.R;

public class AddContact extends AppCompatActivity {
    public static final String EXTRA_REPLY = "com.derrick.park.assignment3_contacts.activities.extra.REPLY";
    private EditText nameLast;
    private EditText phoneCell;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        nameLast = findViewById(R.id.firstLastName);
        phoneCell = findViewById(R.id.phoneNumber);

        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String nameValue = nameLast.getText().toString();
                String phoneValue = phoneCell.getText().toString();
                Intent intent = new Intent(AddContact.this, MainActivity.class);
                intent.putExtra(EXTRA_REPLY, nameValue);
                intent.putExtra(EXTRA_REPLY, phoneValue);
                setResult(RESULT_OK, intent);
                finish();


//                startActivity(intent);
            }
        });




    }
}
