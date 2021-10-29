package id.jyotisa.praktikumprogmob2021;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

public class DetailActivity extends AppCompatActivity {

    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    TextView tvJobTitle, tvJobDesc, tvLocation, tvSalary, tvJobType, tvBenefits;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_PraktikumProgmob2021);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String companyName = intent.getStringExtra(AddJobActivity.COMPANY_NAME);
        String jobTitle = intent.getStringExtra(AddJobActivity.JOB_TITLE);
        String jobDesc = intent.getStringExtra(AddJobActivity.JOB_DESC);
        String location = intent.getStringExtra(AddJobActivity.LOCATION);
        String jobType = intent.getStringExtra(AddJobActivity.JOB_TYPE);
        String salary = intent.getStringExtra(AddJobActivity.SALARY);
        String benefit = intent.getStringExtra(AddJobActivity.BENEFIT);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        getSupportActionBar().setTitle(companyName);

        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color2 = generator.getColor(String.valueOf(companyName.charAt(0)));

        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                    .bold()
                .endConfig()
                .buildRound(String.valueOf(companyName.charAt(0)), color2);

        ImageView image = (ImageView) findViewById(R.id.logo);
        image.setImageDrawable(drawable);

        tvJobTitle = (TextView) findViewById(R.id.jobName);
        tvJobDesc = (TextView) findViewById(R.id.jobDesc);
        tvLocation = (TextView) findViewById(R.id.location);
        tvSalary = (TextView) findViewById(R.id.jobSalary);
        tvJobType = (TextView) findViewById(R.id.jobType);
        tvBenefits = (TextView) findViewById(R.id.benefits);

        tvJobTitle.setText(jobTitle);
        tvJobDesc.setText(jobDesc);
        tvLocation.setText(location);
        tvJobType.setText(jobType);
        tvSalary.setText(salary);
        tvBenefits.setText(benefit);
    }
}
