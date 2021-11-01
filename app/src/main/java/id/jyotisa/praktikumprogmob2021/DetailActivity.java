package id.jyotisa.praktikumprogmob2021;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.zip.Inflater;

import id.jyotisa.praktikumprogmob2021.model.Job;

public class DetailActivity extends AppCompatActivity {

    TextView tvJobTitle, tvJobDesc, tvLocation, tvSalary, tvJobType, tvBenefits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_PraktikumProgmob2021);
        setContentView(R.layout.activity_detail);

        Job job = getIntent().getParcelableExtra("JOB");

        LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.title_format, null);
        TextView titleActionBar = (TextView) view.findViewById(R.id.titleActionBar);
        titleActionBar.setText(job.getCompanyName());
        titleActionBar.setGravity(Gravity.CENTER);

        getSupportActionBar().setTitle(job.getCompanyName());
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(view);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color2 = generator.getColor(String.valueOf(job.getCompanyName().charAt(0)));

        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                    .bold()
                .endConfig()
                .buildRound(String.valueOf(job.getCompanyName().charAt(0)), color2);

        ImageView image = (ImageView) findViewById(R.id.logo);
        image.setImageDrawable(drawable);

        tvJobTitle = (TextView) findViewById(R.id.jobName);
        tvJobDesc = (TextView) findViewById(R.id.jobDesc);
        tvLocation = (TextView) findViewById(R.id.location);
        tvSalary = (TextView) findViewById(R.id.jobSalary);
        tvJobType = (TextView) findViewById(R.id.jobType);
        tvBenefits = (TextView) findViewById(R.id.benefits);

        tvJobTitle.setText(job.getJobTitle());
        tvJobDesc.setText(job.getJobDesc());
        tvLocation.setText(job.getLocation());
        tvJobType.setText(job.getJobType());
        tvSalary.setText("Salary: $"+ job.getSalary()  +"/month");
        tvBenefits.setText(job.getBenefits());
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
    protected void onDestroy() {
        super.onDestroy();

        Toast.makeText(DetailActivity.this, "You are leaving detail page", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(DetailActivity.this, "Your job detail page no longer visible", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.owner_detail_menu, menu);
        return true;
    }
}
