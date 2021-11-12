package id.jyotisa.praktikumprogmob2021.adapter;

import android.content.Context;
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
import id.jyotisa.praktikumprogmob2021.model.Job;

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.ViewHolder>{

    ArrayList<Job> jobHolder = new ArrayList<>();
    Context context;
    private OnEditListener onEditListener;
    private OnDeleteListener onDeleteListener;

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
    }

    @Override
    public int getItemCount() {
        return (jobHolder != null) ? jobHolder.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView jobTitle, country, companyName, salary;
        Button btnDelete, btnEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            jobTitle = (TextView) itemView.findViewById(R.id.jobTitle);
            country = (TextView) itemView.findViewById(R.id.country);
            companyName = (TextView) itemView.findViewById(R.id.companyName);
            salary = (TextView) itemView.findViewById(R.id.salary);
        }
    }

    public interface OnEditListener{
        void onEditClick(Job job);
    }

    public interface OnDeleteListener{
        void onDeleteClick(Job job);
    }
}
