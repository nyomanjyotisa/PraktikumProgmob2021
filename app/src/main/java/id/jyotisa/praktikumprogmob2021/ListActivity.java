package id.jyotisa.praktikumprogmob2021;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;

import id.jyotisa.praktikumprogmob2021.adapter.JobsAdapter;
import id.jyotisa.praktikumprogmob2021.helper.DBHelper;
import id.jyotisa.praktikumprogmob2021.model.Job;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SQLiteDatabase sqLiteDatabase;
    private ArrayList<Job> jobHolder = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_PraktikumProgmob2021);
        setContentView(R.layout.activity_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        getSupportActionBar().setTitle("Your Job Vacancies");
        DBHelper db = new DBHelper(this);

        recyclerView = (RecyclerView) findViewById(R.id.rvJobs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Cursor cursor = new DBHelper(this).readJobs();

        while(cursor.moveToNext()){
            Job obj = new Job(cursor.getString(1), cursor.getString(3), cursor.getString(4), cursor.getString(2), cursor.getString(5), cursor.getInt(7), cursor.getString(6), cursor.getInt(0));
            jobHolder.add(obj);
        }

        JobsAdapter adapter = new  JobsAdapter(jobHolder, ListActivity.this, sqLiteDatabase);
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
        super.onBackPressed();
        Intent intent = new Intent(ListActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
