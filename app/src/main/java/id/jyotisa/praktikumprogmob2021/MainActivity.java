package id.jyotisa.praktikumprogmob2021;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import id.jyotisa.praktikumprogmob2021.adapter.JobsAdapter;
import id.jyotisa.praktikumprogmob2021.helper.DBHelper;
import id.jyotisa.praktikumprogmob2021.model.Job;

public class MainActivity extends AppCompatActivity implements JobsAdapter.TombolAdapterDitekan {

    private RecyclerView recyclerView;
    private SQLiteDatabase sqLiteDatabase;
    private ArrayList<Job> jobHolder = new ArrayList<>();
    private long pressedTime;
    private TextView emptyView;
    private JobsAdapter jobsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_PraktikumProgmob2021);
        setContentView(R.layout.activity_list);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        getSupportActionBar().setTitle("Your Job Vacancies");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddJobActivity.class));
            }
        });

        DBHelper db = new DBHelper(this);

        emptyView = (TextView) findViewById(R.id.empty_view);
        recyclerView = (RecyclerView) findViewById(R.id.rvJobs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Cursor cursor = new DBHelper(this).readJobs();

        if (cursor.getCount() > 0) {
            emptyView.setVisibility(View.GONE);
        }
        else {
            emptyView.setVisibility(View.VISIBLE);
        }

        while(cursor.moveToNext()){
            Job obj = new Job(cursor.getString(1),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(2),
                    cursor.getString(5),
                    cursor.getInt(7),
                    cursor.getString(6),
                    cursor.getInt(0));
            jobHolder.add(obj);
        }

        JobsAdapter adapter = new JobsAdapter(jobHolder, MainActivity.this, sqLiteDatabase);
        adapter.setClickEvent((JobsAdapter.TombolAdapterDitekan) this);
        recyclerView.setAdapter((RecyclerView.Adapter) adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (pressedTime + 4000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finishAffinity();
        } else {
            Toasty.info(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT, true).show();
        }
        pressedTime = System.currentTimeMillis();
    }

    @Override
    public void OperasiAdapter() {
        Cursor cursor = new DBHelper(this).readJobs();

        if (cursor.getCount() > 0) {
            emptyView.setVisibility(View.GONE);
        }
        else {
            emptyView.setVisibility(View.VISIBLE);
        }
    }
}
