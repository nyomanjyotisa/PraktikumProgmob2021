package id.jyotisa.praktikumprogmob2021;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.android.material.textfield.TextInputLayout;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import id.jyotisa.praktikumprogmob2021.fragment.NewFragment;
import id.jyotisa.praktikumprogmob2021.helper.DBHelper;
import id.jyotisa.praktikumprogmob2021.model.Job;

public class UpdateActivity extends AppCompatActivity {
    private Button btnSubmit, btnPost;
    private AlertDialog.Builder dialog;
    private LayoutInflater inflater;
    private View dialogView;
    private TextInputLayout etCompanyName, etJobTitle, etJobDesc, etCountry;
    private EditText etSalary;
    private ArrayList<String> benefits;
    private TextView tvCompanyName, tvJobTitle, tvJobDesc, tvCountry, tvSalary, tvJobType, tvBenefits;
    private RadioGroup radioJobType;
    private CheckBox benefit1, benefit2, benefit3, benefit4;
    private RadioButton radioButtonJobType, radioJobType1, radioJobType2, radioJobType3;
    private SeekBar seekBarSalary;
    private String formattedSalary;
    private StringBuilder stringBenefits;
    private Job job;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_PraktikumProgmob2021);
        setContentView(R.layout.activity_add);
        setCustomActionBar();

        job = getIntent().getParcelableExtra(DetailActivity.JOB);

        seekBarSalary = findViewById(R.id.salarySeekBar);
        etCompanyName = findViewById(R.id.companyNameEditText);
        etJobTitle = findViewById(R.id.jobTitleEditText);
        etJobDesc = findViewById(R.id.jobDescEditText);
        etCountry = findViewById(R.id.countryEditText);
        etSalary = findViewById(R.id.salaryEditText);

        etCompanyName.getEditText().setText(job.getCompanyName());
        etJobTitle.getEditText().setText(job.getJobTitle());
        etJobDesc.getEditText().setText(job.getJobDesc());
        etCountry.getEditText().setText(job.getCountry());
        etSalary.setText(job.getSalary().toString());
        seekBarSalary.setProgress(job.getSalary());

        formValidation("onEdit");
        seekBarAndEditTextIntegration();

        radioJobType = findViewById(R.id.jobTypeRadio);
        radioJobType1 = findViewById(R.id.jobType1);
        radioJobType2 = findViewById(R.id.jobType2);
        radioJobType3 = findViewById(R.id.jobType3);
        if(job.getJobType().equals(radioJobType1.getText().toString())){
            radioJobType.check(R.id.jobType1);
        }else if(job.getJobType().equals(radioJobType2.getText().toString())){
            radioJobType.check(R.id.jobType2);
        }else if(job.getJobType().equals(radioJobType3.getText().toString())){
            radioJobType.check(R.id.jobType3);
        }

        benefit1 = (CheckBox)findViewById(R.id.benefit1);
        benefit2 = (CheckBox)findViewById(R.id.benefit2);
        benefit3 = (CheckBox)findViewById(R.id.benefit3);
        benefit4 = (CheckBox)findViewById(R.id.benefit4);
        benefits = new ArrayList<>();

        String[] benefit = job.getBenefits().split("- ");
        if(benefit.length > 1){
            Log.v("babi0", benefit[0]);
            Log.v("babi1", benefit[1].replaceAll("\\s+$", ""));
            benefit1.setChecked(true);
            benefits.add(benefit1.getText().toString());
        }
        if(benefit.length > 2){
            Log.v("babi2", benefit[2].replaceAll("\\s+$", ""));
            benefit2.setChecked(true);
            benefits.add(benefit2.getText().toString());
        }
        if(benefit.length > 3){
            Log.v("babi3", benefit[3].replaceAll("\\s+$", ""));
            benefit3.setChecked(true);
            benefits.add(benefit3.getText().toString());
        }
        if(benefit.length > 4){
            Log.v("babi4", benefit[4].replaceAll("\\s+$", ""));
            benefit4.setChecked(true);
            benefits.add(benefit4.getText().toString());
        }
        
        benefitOnClick();

        btnSubmit = (Button) findViewById(R.id.createButton);
        btnSubmit.setText("UPDATE JOB");
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(formValidation("onSubmit")){
                    btnUpdate(benefits);
                }else{
                    Toasty.error(UpdateActivity.this, "Some fields contain error", Toast.LENGTH_SHORT, true).show();
                }
            }
        });
    }

    private void onEditValidationInput(TextInputLayout editText, Integer limit){
        editText.setCounterMaxLength(limit);

        editText.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(editText.getEditText().getText().toString().trim().length() > limit) {
                    editText.setError("Input reach limit");
                }else if(editText.getEditText().getText().toString().trim().length() > 0) {
                    editText.setErrorEnabled(false);
                    editText.getEditText().setSelection(editText.getEditText().getText().length());
                }else{
                    editText.setError("Required");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private boolean onSubmitValidationInput(TextInputLayout editText, Integer limit) {
        if(editText.getEditText().getText().toString().trim().isEmpty()){
            editText.setError("Required");
            return false;
        }else if(editText.getEditText().getText().toString().trim().length() > limit){
            editText.setError("Input reach limit");
            return false;
        }else{
            return true;
        }
    }

    private void benefitOnClick() {
        benefit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (benefit1.isChecked())
                    benefits.add(benefit1.getText().toString());
                else
                    benefits.remove(benefit1.getText().toString());
            }
        });

        benefit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (benefit2.isChecked())
                    benefits.add(benefit2.getText().toString());
                else
                    benefits.remove(benefit2.getText().toString());
            }
        });

        benefit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (benefit3.isChecked())
                    benefits.add(benefit3.getText().toString());
                else
                    benefits.remove(benefit3.getText().toString());
            }
        });

        benefit4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (benefit4.isChecked())
                    benefits.add(benefit4.getText().toString());
                else
                    benefits.remove(benefit4.getText().toString());
            }
        });
    }

    private void seekBarAndEditTextIntegration(){
        etSalary.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(etSalary.getText().toString().trim().length() > 0) {
                    seekBarSalary.setProgress(Integer.parseInt(etSalary.getText().toString()));
                    etSalary.setSelection(etSalary.getText().length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        seekBarSalary.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                etSalary.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private boolean formValidation(String when) {
        Integer companyNameLimit = 30;
        Integer jobTitleLimit = 50;
        Integer jobDescLimit = 1000;
        Integer locationLimit = 15;

        if(when.equals("onEdit")){
            onEditValidationInput(etCompanyName, companyNameLimit);
            onEditValidationInput(etJobTitle, jobTitleLimit);
            onEditValidationInput(etJobDesc, jobDescLimit);
            onEditValidationInput(etCountry, locationLimit);
        }else if(when.equals("onSubmit")){
            boolean valid = true;
            if(!onSubmitValidationInput(etCompanyName, companyNameLimit))
                valid = false;

            if(!onSubmitValidationInput(etJobTitle, jobTitleLimit))
                valid = false;

            if(!onSubmitValidationInput(etJobDesc, jobDescLimit))
                valid = false;

            if(!onSubmitValidationInput(etCountry, locationLimit))
                valid = false;

            if(!valid)
                return false;
        }

        return true;
    }

    private void setCustomActionBar(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        getSupportActionBar().setTitle("Update Job");
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
        Intent intent = new Intent(UpdateActivity.this, ListActivity.class);
        startActivity(intent);
        finish();
    }

    private void btnUpdate(ArrayList<String> benefits){
        //benefits array to string
        stringBenefits = new StringBuilder();
        for (String s : benefits)
            stringBenefits.append(" - "+s).append("\n");

        //set format for salary
        Integer salary;
        if(etSalary.getText().toString().isEmpty()){
            salary = 0;
        }else{
            salary = Integer.parseInt(etSalary.getText().toString());
        }
        formattedSalary = NumberFormat.getNumberInstance(Locale.US).format(salary);

        //job type
        radioJobType = findViewById(R.id.jobTypeRadio);
        int selectedId = radioJobType.getCheckedRadioButtonId();
        radioButtonJobType = (RadioButton) findViewById(selectedId);

        db = new DBHelper(this);
        db.updateJob(job.getId(),
                etCompanyName.getEditText().getText().toString(),
                etCountry.getEditText().getText().toString(),
                etJobTitle.getEditText().getText().toString(),
                etJobDesc.getEditText().getText().toString(),
                radioButtonJobType.getText().toString(),
                stringBenefits.toString(),
                salary);
    }
}
