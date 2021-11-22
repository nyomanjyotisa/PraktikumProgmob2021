package id.jyotisa.praktikumprogmob2021.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import id.jyotisa.praktikumprogmob2021.AddJobActivity;
import id.jyotisa.praktikumprogmob2021.Constant;
import id.jyotisa.praktikumprogmob2021.DetailActivity;
import id.jyotisa.praktikumprogmob2021.ListActivity;
import id.jyotisa.praktikumprogmob2021.R;
import id.jyotisa.praktikumprogmob2021.UpdateActivity;
import id.jyotisa.praktikumprogmob2021.helper.DBHelper;
import id.jyotisa.praktikumprogmob2021.model.Job;

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.ViewHolder>{

    private ArrayList<Job> jobHolder = new ArrayList<>();
    private Context context;

    public JobsAdapter(ArrayList<Job> jobHolder, Context context, SQLiteDatabase sqLiteDatabase) {
        this.jobHolder = jobHolder;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.companyName.setText(jobHolder.get(position).getCompanyName());
        holder.country.setText(jobHolder.get(position).getCountry());
        holder.jobTitle.setText(jobHolder.get(position).getJobTitle());
        holder.salary.setText("Salary: $" + NumberFormat.getNumberInstance(Locale.US).format(jobHolder.get(position).getSalary()) + "/month");

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
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
                        DBHelper db = new DBHelper(context);
                        db.deleteData(jobHolder.get(position).getId());

                        jobHolder.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, jobHolder.size());
                        notifyDataSetChanged();
                    }
                });

                AlertDialog dialog = alertDialog.create();
                dialog.show();
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Job job = new Job(jobHolder.get(position).getCompanyName(),
                        jobHolder.get(position).getJobTitle(),
                        jobHolder.get(position).getJobDesc(),
                        jobHolder.get(position).getCountry(),
                        jobHolder.get(position).getJobType(),
                        jobHolder.get(position).getSalary(),
                        jobHolder.get(position).getBenefits(),
                        jobHolder.get(position).getId());
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra(Constant.JOB, job);
                context.startActivity(intent);
            }
        });

        holder.section.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Job job = new Job(jobHolder.get(position).getCompanyName(),
                        jobHolder.get(position).getJobTitle(),
                        jobHolder.get(position).getJobDesc(),
                        jobHolder.get(position).getCountry(),
                        jobHolder.get(position).getJobType(),
                        jobHolder.get(position).getSalary(),
                        jobHolder.get(position).getBenefits(),
                        jobHolder.get(position).getId());
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(Constant.JOB, job);
                context.startActivity(intent);
            }
        });

        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color2 = generator.getColor(String.valueOf(jobHolder.get(position).getCompanyName().charAt(0)));
        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .bold()
                .endConfig()
                .buildRound(String.valueOf(jobHolder.get(position).getCompanyName().charAt(0)), color2);
        holder.logo.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return (jobHolder != null) ? jobHolder.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView jobTitle, country, companyName, salary;
        ImageView delete, edit, logo, section;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            jobTitle = (TextView) itemView.findViewById(R.id.jobTitle);
            country = (TextView) itemView.findViewById(R.id.country);
            companyName = (TextView) itemView.findViewById(R.id.companyName);
            salary = (TextView) itemView.findViewById(R.id.salary);
            edit = (ImageView) itemView.findViewById(R.id.edit);
            delete = (ImageView) itemView.findViewById(R.id.delete);
            logo = (ImageView) itemView.findViewById(R.id.heroLeft);
            section = (ImageView) itemView.findViewById(R.id.hero);
        }
    }
}
