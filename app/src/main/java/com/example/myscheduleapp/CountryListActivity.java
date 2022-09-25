package com.example.myscheduleapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class CountryListActivity extends AppCompatActivity {

    private DBManager dbManager;
    private ListView listView;
    private SimpleCursorAdapter adapter;

    final String[] from = new String[]{
            DatabaseHelper._ID,
            DatabaseHelper.SUBJECT,
            DatabaseHelper.DESC
    };

    final int[] to = new int[]{
            R.id.lblId,
            R.id.lblTitle,
            R.id.lblDesc
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_emp_list);

        dbManager = new DBManager(this);
        dbManager.open();

        final Cursor[] cursor = {dbManager.fetchBySubject("")};

        listView = findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));

        adapter = new SimpleCursorAdapter(
                this,
                R.layout.activity_view_record,
                cursor[0],
                from,
                to,
                0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                TextView lblId = view.findViewById(R.id.lblId);
                TextView lblTitle = view.findViewById(R.id.lblTitle);
                TextView lblDesc = view.findViewById(R.id.lblDesc);

                String id = lblId.getText().toString();
                String title = lblTitle.getText().toString();
                String desc = lblDesc.getText().toString();

                // panggil form-child
                Intent modify_intent = new Intent(
                        getApplicationContext(),
                        ModifyCountryActivity.class
                );

                // passing parameter kirim ke form-child
                modify_intent.putExtra("title", title);
                modify_intent.putExtra("desc", desc);
                modify_intent.putExtra("id", id);
                startActivity(modify_intent);
            }
        });

        SearchView searchView = (SearchView) findViewById(R.id.srcAgenda);
        searchView.setQueryHint("Cari agendamu di sini");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                cursor[0] = dbManager.fetchBySubject(s);
                adapter = new SimpleCursorAdapter(
                        CountryListActivity.this,
                        R.layout.activity_view_record,
                        cursor[0],
                        from,
                        to,
                        0);
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                cursor[0] = dbManager.fetchBySubject(s);
                adapter = new SimpleCursorAdapter(
                        CountryListActivity.this,
                        R.layout.activity_view_record,
                        cursor[0],
                        from,
                        to,
                        0);
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);

                return false;
            }
        });
    }

    // screnario tambah data baru
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.cmdAdd) {
            Intent add_mem = new Intent(this, AddCountryActivity.class);
            startActivity(add_mem);
        }

        return super.onOptionsItemSelected(item);
    }

}