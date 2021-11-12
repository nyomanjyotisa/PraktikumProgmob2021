package id.jyotisa.praktikumprogmob2021.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import id.jyotisa.praktikumprogmob2021.R;
import id.jyotisa.praktikumprogmob2021.helper.DBHelper;
import id.jyotisa.praktikumprogmob2021.model.Job;

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.ViewHolder>{

    ArrayList<Job> jobHolder = new ArrayList<>();
    Context context;

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
                alertDialog.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
                alertDialog.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        DBHelper db = new DBHelper(context);
                        db.deleteData(jobHolder.get(position).getId().toString());

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
    }

    @Override
    public int getItemCount() {
        return (jobHolder != null) ? jobHolder.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView jobTitle, country, companyName, salary;
        ImageView delete, edit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            jobTitle = (TextView) itemView.findViewById(R.id.jobTitle);
            country = (TextView) itemView.findViewById(R.id.country);
            companyName = (TextView) itemView.findViewById(R.id.companyName);
            salary = (TextView) itemView.findViewById(R.id.salary);
            edit = (ImageView) itemView.findViewById(R.id.edit);
            delete = (ImageView) itemView.findViewById(R.id.delete);
        }
    }
}
