package id.jyotisa.praktikumprogmob2021;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import id.jyotisa.praktikumprogmob2021.adapter.JobsAdapter;
import id.jyotisa.praktikumprogmob2021.model.Job;

public class ListActivity extends AppCompatActivity implements JobsAdapter.OnEditListener, JobsAdapter.OnDeleteListener{

    private RecyclerView recyclerView;
    private JobsAdapter rvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_PraktikumProgmob2021);
        setContentView(R.layout.activity_list);
        getSupportActionBar().hide();

        recyclerView = (RecyclerView) findViewById(R.id.rvContacts);
        rvAdapter = new JobsAdapter(neracaArrayListPeriode, NeracaActivity.this::onEditClick, data -> NeracaActivity.this.onDeleteClick(data));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(NeracaActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(rvadapter);
    }

    @Override
    public void onEditClick(Job job) {

    }

    @Override
    public void onDeleteClick(Job job) {

    }
}
