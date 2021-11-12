package id.jyotisa.praktikumprogmob2021;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.jyotisa.praktikumprogmob2021.adapter.JobsAdapter;
import id.jyotisa.praktikumprogmob2021.helper.DBHelper;
import id.jyotisa.praktikumprogmob2021.model.Job;

public class ListActivity extends AppCompatActivity implements JobsAdapter.OnEditListener, JobsAdapter.OnDeleteListener{


    RecyclerView recyclerView;
    SQLiteDatabase sqLiteDatabase;
    TextView valid;
    private ArrayList<Job> jobHolder = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_PraktikumProgmob2021);
        setContentView(R.layout.activity_list);
        DBHelper db = new DBHelper(this);

        recyclerView = (RecyclerView) findViewById(R.id.rvJobs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Cursor cursor = new DBHelper(this).readJobs();

        while(cursor.moveToNext()){
            Job obj = new Job(cursor.getString(1), cursor.getString(3), cursor.getString(4), cursor.getString(2), cursor.getString(5), cursor.getInt(7), cursor.getString(6));
            jobHolder.add(obj);
        }

        JobsAdapter adapter = new  JobsAdapter(jobHolder, ListActivity.this, sqLiteDatabase);
        recyclerView.setAdapter((RecyclerView.Adapter) adapter);
    }

    @Override
    public void onEditClick(Job job) {

    }

    @Override
    public void onDeleteClick(Job job) {

    }
}
