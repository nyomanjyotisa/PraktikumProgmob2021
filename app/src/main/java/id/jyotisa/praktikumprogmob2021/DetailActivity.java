package id.jyotisa.praktikumprogmob2021;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.text.NumberFormat;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import id.jyotisa.praktikumprogmob2021.auth.LoginActivity;
import id.jyotisa.praktikumprogmob2021.auth.SignupActivity;
import id.jyotisa.praktikumprogmob2021.helper.DBHelper;
import id.jyotisa.praktikumprogmob2021.model.Job;

public class DetailActivity extends AppCompatActivity {
    public final static String JOB = "id.jyotisa.praktikumprogmob2021.JOB";
    private TextView tvJobTitle, tvJobDesc, tvLocation, tvSalary, tvJobType, tvBenefits, tvCompanyName;
    private Job job;
    private DBHelper db;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_PraktikumProgmob2021);
        setContentView(R.layout.activity_detail);
        setCustomActionBar();

        job = getIntent().getParcelableExtra(JOB);

        tvCompanyName = (TextView) findViewById(R.id.companyName);
        tvJobTitle = (TextView) findViewById(R.id.jobName);
        tvJobDesc = (TextView) findViewById(R.id.jobDesc);
        tvLocation = (TextView) findViewById(R.id.location);
        tvSalary = (TextView) findViewById(R.id.jobSalary);
        tvJobType = (TextView) findViewById(R.id.jobType);
        tvBenefits = (TextView) findViewById(R.id.benefits);

        tvCompanyName.setText(job.getCompanyName());
        tvJobTitle.setText(job.getJobTitle());
        tvJobDesc.setText(job.getJobDesc());
        tvLocation.setText(job.getCountry());
        tvJobType.setText(job.getJobType());
        tvSalary.setText("Salary: $"+ NumberFormat.getNumberInstance(Locale.US).format(job.getSalary())+"/month");
        tvBenefits.setText(job.getBenefits());

        setTextDrawable(job.getCompanyName());
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        ImageView logo = findViewById(R.id.logo);

        Animation animation = AnimationUtils.loadAnimation(DetailActivity.this, R.anim.bounce);
        logo.startAnimation(animation);
    }

    @Override
    protected void onResume() {
        super.onResume();

        TextView jobType = findViewById(R.id.jobType);
        TextView location = findViewById(R.id.location);

        jobType.startAnimation(AnimationUtils.loadAnimation(DetailActivity.this,R.anim.lefttoright));
        location.startAnimation(AnimationUtils.loadAnimation(DetailActivity.this,R.anim.lefttoright));
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toasty.info(DetailActivity.this, "Your job detail page no longer visible", Toast.LENGTH_SHORT, true).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toasty.warning(DetailActivity.this, "You are leaving detail page", Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.owner_detail_menu, menu);
        return true;
    }

    private void setTextDrawable(String companyName){
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color2 = generator.getColor(String.valueOf(companyName.charAt(0)));

        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .bold()
                .endConfig()
                .buildRound(String.valueOf(companyName.charAt(0)), color2);

        ImageView image = (ImageView) findViewById(R.id.logo);
        image.setImageDrawable(drawable);
    }

    private void setCustomActionBar(){
        getSupportActionBar().setTitle("Job Details");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.edit:
                Job jobNew = new Job(job.getCompanyName(),
                        job.getJobTitle(),
                        job.getJobDesc(),
                        job.getCountry(),
                        job.getJobType(),
                        job.getSalary(),
                        job.getBenefits(),
                        job.getId());
                Intent intent = new Intent(DetailActivity.this, UpdateActivity.class);
                intent.putExtra("JOB", jobNew);
                startActivity(intent);
                return true;
            case R.id.delete:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetailActivity.this);
                alertDialog.setTitle("Delete Confirmation");
                alertDialog.setMessage("Are you sure to delete this job?");
                alertDialog.setCancelable(false);
                alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        db = new DBHelper(DetailActivity.this);
                        db.deleteData(job.getId());
                        Log.v("cuy", "masukkk" + job.getId());
                        startActivity(new Intent(DetailActivity.this, ListActivity.class));
                    }
                });

                AlertDialog dialog = alertDialog.create();
                dialog.show();
                return true;
            case R.id.share:
                onBackPressed();
                return true;
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DetailActivity.this, ListActivity.class));
    }
}
