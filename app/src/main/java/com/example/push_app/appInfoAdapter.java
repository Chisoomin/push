package com.example.push_app;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class appInfoAdapter extends RecyclerView.Adapter<appInfoAdapter.appInfoViewHolder>{
    ArrayList<appinfo_getset> list = new ArrayList<appinfo_getset>();

    public appInfoAdapter(ArrayList<appinfo_getset> list) {
        this.list = list;
        Log.e("dkdkdkdkddkdddkdkkd", String.valueOf(list));
    }

    @NonNull
    @NotNull
    @Override
    public appInfoViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appinfo_recycler, parent, false);
        return new appInfoAdapter.appInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull appInfoAdapter.appInfoViewHolder holder, int position) {
        holder.infoname.setText(list.get(position).getAppinfoname());
        holder.infovalue.setText(list.get(position).getAppinfovalue());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class appInfoViewHolder extends RecyclerView.ViewHolder{
        TextView infoname, infovalue;
        public appInfoViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            infoname = itemView.findViewById(R.id.infoname);
            infovalue = itemView.findViewById(R.id.infovalue);
        }
    }
}
