package id.jyotisa.praktikumprogmob2021;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.android.material.textfield.TextInputLayout;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import id.jyotisa.praktikumprogmob2021.model.Job;

public class AddJobActivity extends AppCompatActivity {

    private Button btnSubmit;
    private AlertDialog.Builder dialog;
    private LayoutInflater inflater;
    private View dialogView;
    private TextInputLayout etCompanyName, etJobTitle, etJobDesc, etLocation;
    private EditText etSalary;
    private ArrayList<String> benefits;
    private TextView tvCompanyName, tvJobTitle, tvJobDesc, tvLocation, tvSalary, tvJobType, tvBenefits;
    private RadioGroup radioJobType;
    private CheckBox benefit1, benefit2, benefit3, benefit4;
    private RadioButton radioButtonJobType;
    private SeekBar seekBarSalary;
    private String formattedSalary;
    private StringBuilder stringBenefits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_PraktikumProgmob2021);
        setContentView(R.layout.activity_add);
        setCustomActionBar();

        seekBarSalary = findViewById(R.id.salarySeekBar);
        etCompanyName = findViewById(R.id.companyNameEditText);
        etJobTitle = findViewById(R.id.jobTitleEditText);
        etJobDesc = findViewById(R.id.jobDescEditText);
        etLocation = findViewById(R.id.locationEditText);
        etSalary = findViewById(R.id.salaryEditText);

        formValidation("onEdit");
        seekBarAndEditTextIntegration();

        benefit1 = (CheckBox)findViewById(R.id.benefit1);
        benefit2 = (CheckBox)findViewById(R.id.benefit2);
        benefit3 = (CheckBox)findViewById(R.id.benefit3);
        benefit4 = (CheckBox)findViewById(R.id.benefit4);
        benefits = new ArrayList<>();
        benefitOnClick();

        btnSubmit = (Button) findViewById(R.id.createButton);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(formValidation("onSubmit")){
                    showDialog(benefits);
                }else{
                    Toasty.error(AddJobActivity.this, "Some fields contain error", Toast.LENGTH_SHORT, true).show();
                }
            }
        });
    }

    private void showDialog(ArrayList<String> benefits){
        dialog = new AlertDialog.Builder(AddJobActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_data, null);
        dialog.setView(dialogView);
        dialog.setTitle("Job Preview");

        //benefits array to string
        stringBenefits = new StringBuilder();
        for (String s : benefits)
            stringBenefits.append(" - "+s).append("\n");

        //set format for salary
        int salary;
        if(etSalary.getText().toString().isEmpty()){
            salary = 0;
        }else{
            salary = Integer.parseInt(etSalary.getText().toString());
        }
        formattedSalary = NumberFormat.getNumberInstance(Locale.US).format(salary);

        //jobtype
        radioJobType = findViewById(R.id.jobTypeRadio);
        int selectedId = radioJobType.getCheckedRadioButtonId();
        radioButtonJobType = (RadioButton) findViewById(selectedId);

        tvCompanyName = (TextView) dialogView.findViewById(R.id.companyName);
        tvJobTitle = (TextView) dialogView.findViewById(R.id.jobName);
        tvJobDesc = (TextView) dialogView.findViewById(R.id.jobDesc);
        tvLocation = (TextView) dialogView.findViewById(R.id.location);
        tvSalary = (TextView) dialogView.findViewById(R.id.jobSalary);
        tvJobType = (TextView) dialogView.findViewById(R.id.jobType);
        tvBenefits = (TextView) dialogView.findViewById(R.id.benefits);

        tvCompanyName.setText(etCompanyName.getEditText().getText().toString());
        tvJobTitle.setText(etJobTitle.getEditText().getText().toString());
        tvJobDesc.setText(etJobDesc.getEditText().getText().toString());
        tvLocation.setText(etLocation.getEditText().getText().toString());
        tvSalary.setText("Salary: $"+ formattedSalary  +"/month");
        tvJobType.setText(radioButtonJobType.getText());
        tvBenefits.setText(stringBenefits);

        setTextDrawable(etCompanyName.getEditText().getText().toString(), dialogView);

        dialog.setPositiveButton("POST", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Job job = new Job(etCompanyName.getEditText().getText().toString(), etJobTitle.getEditText().getText().toString(),
                        etJobDesc.getEditText().getText().toString(), etLocation.getEditText().getText().toString(),
                        radioButtonJobType.getText().toString(), formattedSalary, stringBenefits.toString());
                Intent intent = new Intent(AddJobActivity.this, DetailActivity.class);
                intent.putExtra("JOB", job);
                startActivity(intent);
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
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
            onEditValidationInput(etLocation, locationLimit);
        }else if(when.equals("onSubmit")){
            boolean valid = true;
            if(!onSubmitValidationInput(etCompanyName, companyNameLimit))
                valid = false;

            if(!onSubmitValidationInput(etJobTitle, jobTitleLimit))
                valid = false;

            if(!onSubmitValidationInput(etJobDesc, jobDescLimit))
                valid = false;

            if(!onSubmitValidationInput(etLocation, locationLimit))
                valid = false;

            if(!valid)
                return false;
        }

        return true;
    }

    private void setCustomActionBar(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        getSupportActionBar().setTitle("Create a Job");
    }

    private void setTextDrawable(String companyName, View view){
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color2 = generator.getColor(String.valueOf(companyName.charAt(0)));

        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .bold()
                .endConfig()
                .buildRound(String.valueOf(companyName.charAt(0)), color2);

        ImageView image = (ImageView) view.findViewById(R.id.logo);
        image.setImageDrawable(drawable);
    }
}