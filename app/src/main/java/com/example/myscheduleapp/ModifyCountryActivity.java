package com.example.myscheduleapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ModifyCountryActivity extends Activity implements View.OnClickListener {

    // widget declare
    private long _id;
    private EditText txtTitle, txtDesc;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Modify the record");
        setContentView(R.layout.activity_modify_country);

        // instantiate the widget
        dbManager = new DBManager(this);
        dbManager.open();

        txtTitle = findViewById(R.id.txtTitle);
        txtDesc = findViewById(R.id.txtDesc);
        Button cmdUpdate = findViewById(R.id.cmdUpdate);
        Button cmdDelete = findViewById(R.id.cmdDelete);

        // receive and extract the parameter
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String title = intent.getStringExtra("title");
        String desc = intent.getStringExtra("desc");

        _id = Long.parseLong(id);
        txtTitle.setText(title);
        txtDesc.setText(desc);

        cmdUpdate.setOnClickListener(this);
        cmdDelete.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cmdUpdate:
                String title = txtTitle.getText().toString();
                String desc = txtDesc.getText().toString();
                dbManager.update(_id, title, desc);
                this.returnHome();
            break;

            case R.id.cmdDelete:
                dbManager.delete(_id);
                this.returnHome();
                break;
        }
    }

    // kembali ke form utama
    public void returnHome() {
        Intent home_intent = new Intent(
                getApplicationContext(),
                CountryListActivity.class
        ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }
}