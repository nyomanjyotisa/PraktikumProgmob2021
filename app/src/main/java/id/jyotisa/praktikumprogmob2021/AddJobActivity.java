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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AddJobActivity extends AppCompatActivity {

    private Button btnSubmit;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    TextInputLayout etCompanyName, etJobTitle, etJobDesc, etLocation;
    EditText etSalary;
    ArrayList<String> benefits;
    TextView tvCompanyName, tvJobTitle, tvJobDesc, tvLocation, tvSalary, tvJobType, tvBenefits;
    RadioGroup radioJobType;
    CheckBox benefit1, benefit2, benefit3, benefit4;
    RadioButton radioButtonJobType;
    SeekBar seekBarSalary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_PraktikumProgmob2021);
        setContentView(R.layout.activity_add);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        getSupportActionBar().setTitle("Create a Job");

        seekBarSalary = findViewById(R.id.salarySeekBar);
        etCompanyName = findViewById(R.id.companyNameEditText);
        etJobTitle = findViewById(R.id.jobTitleEditText);
        etJobDesc = findViewById(R.id.jobDescEditText);
        etLocation = findViewById(R.id.locationEditText);
        etSalary = findViewById(R.id.salaryEditText);

        Integer companyNameLimit = 30;
        Integer jobTitleLimit = 50;
        Integer jobDescLimit = 1000;
        Integer locationLimit = 15;

        onEditValidationInput(etCompanyName, companyNameLimit);
        onEditValidationInput(etJobTitle, jobTitleLimit);
        onEditValidationInput(etJobDesc, jobDescLimit);
        onEditValidationInput(etLocation, locationLimit);

        etCompanyName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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

        benefit1 = (CheckBox)findViewById(R.id.benefit1);
        benefit2 = (CheckBox)findViewById(R.id.benefit2);
        benefit3 = (CheckBox)findViewById(R.id.benefit3);
        benefit4 = (CheckBox)findViewById(R.id.benefit4);
        benefits = new ArrayList<>();

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

        btnSubmit = (Button) findViewById(R.id.createButton);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//
//
//
//
                boolean valid = true;


                if(!onSubmitValidationInput(etCompanyName, companyNameLimit))
                    valid = false;

                if(!onSubmitValidationInput(etJobTitle, jobTitleLimit))
                    valid = false;

                if(!onSubmitValidationInput(etJobDesc, jobDescLimit))
                    valid = false;

                if(!onSubmitValidationInput(etLocation, locationLimit))
                    valid = false;

                if(valid)
                    showDialog(benefits);
            }
        });
    }

    private void showDialog(ArrayList<String> benefits){
        dialog = new AlertDialog.Builder(AddJobActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_data, null);
        dialog.setView(dialogView);

        dialog.setTitle("Job Preview");

        radioJobType = findViewById(R.id.jobTypeRadio);

        int selectedId = radioJobType.getCheckedRadioButtonId();
        radioButtonJobType = (RadioButton) findViewById(selectedId);

        StringBuilder stringBenefits = new StringBuilder();
        for (String s : benefits)
            stringBenefits.append(" - "+s).append("\n");

        etCompanyName = findViewById(R.id.companyNameEditText);
        etJobTitle = findViewById(R.id.jobTitleEditText);
        etJobDesc = findViewById(R.id.jobDescEditText);
        etLocation = findViewById(R.id.locationEditText);
        etSalary = findViewById(R.id.salaryEditText);

        tvCompanyName = (TextView) dialogView.findViewById(R.id.companyName);
        tvJobTitle = (TextView) dialogView.findViewById(R.id.jobTitle);
        tvJobDesc = (TextView) dialogView.findViewById(R.id.jobDesc);
        tvLocation = (TextView) dialogView.findViewById(R.id.location);
        tvSalary = (TextView) dialogView.findViewById(R.id.salary);
        tvJobType = (TextView) dialogView.findViewById(R.id.jobType);
        tvBenefits = (TextView) dialogView.findViewById(R.id.benefits);

        tvCompanyName.setText(etCompanyName.getEditText().getText().toString());
        tvJobTitle.setText(etJobTitle.getEditText().getText().toString());
        tvJobDesc.setText(etJobDesc.getEditText().getText().toString());
        tvLocation.setText(etLocation.getEditText().getText().toString());


        Integer salary = Integer.parseInt(etSalary.getText().toString());


//        String formattedSalary = String.format("%,d", salary);
        String formattedSalary = NumberFormat.getNumberInstance(Locale.US).format(salary);
        tvSalary.setText("Salary: $"+ formattedSalary  +"/month");
        tvJobType.setText(radioButtonJobType.getText());
        tvBenefits.setText(stringBenefits);

        dialog.setPositiveButton("POST", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(AddJobActivity.this, DetailActivity.class));
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


}