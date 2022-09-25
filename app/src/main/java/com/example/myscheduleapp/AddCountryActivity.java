package com.example.myscheduleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddCountryActivity extends Activity implements View.OnClickListener {

    // widgets
    private Button cmdAdd;
    private EditText txtTitle;
    private EditText txtDesc;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Add record");
        setContentView(R.layout.activity_add_country);

        // instantiate the widgets
        txtTitle = findViewById(R.id.txtTitle);
        txtDesc = findViewById(R.id.txtDesc);
        cmdAdd = findViewById(R.id.cmdAdd);

        dbManager = new DBManager(this);
        dbManager.open();
        cmdAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cmdAdd:
                final String title = txtTitle.getText().toString();
                final String desc = txtDesc.getText().toString();
                dbManager.insert(title, desc);

                Intent main = new Intent(
                        AddCountryActivity.this,
                        CountryListActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(main);
                break;
        }
    }
}