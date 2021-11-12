package id.jyotisa.praktikumprogmob2021.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import id.jyotisa.praktikumprogmob2021.R;
import id.jyotisa.praktikumprogmob2021.model.Job;

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.ViewHolder>{

    ArrayList<Job> data;
    private OnEditListener onEditListener;
    private OnDeleteListener onDeleteListener;

    public JobsAdapter(ArrayList<Job> data, OnEditListener onEditListener, OnDeleteListener onDeleteListener){
        this.data = data;
        this.onEditListener = onEditListener;
        this.onDeleteListener = onDeleteListener;
    }

    @NonNull
    @Override
    public JobsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_job, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return (data != null) ? data.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public interface OnEditListener{
        void onEditClick(Job job);
    }

    public interface OnDeleteListener{
        void onDeleteClick(Job job);
    }
}
