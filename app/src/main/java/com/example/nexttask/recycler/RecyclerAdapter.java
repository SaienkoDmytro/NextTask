package com.example.nexttask.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nexttask.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    ArrayList<DataModel> dataHolder;

    public RecyclerAdapter(ArrayList<DataModel> dataHolder) {
        this.dataHolder = dataHolder;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view1,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.img.setImageResource(dataHolder.get(position).getImage());
        holder.status.setText(dataHolder.get(position).getStatus());
        holder.temp.setText(dataHolder.get(position).getTemp());
    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView status,temp;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img1);
            status=itemView.findViewById(R.id.t1);
            temp=itemView.findViewById(R.id.t2);
        }
    }
}
