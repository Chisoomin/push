package com.example.push_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

class Alert_Adapter_vp extends RecyclerView.Adapter<Alert_Adapter_vp.AlertViewHolder>{
    Context c;
    ArrayList<alert_getset> list = new ArrayList<alert_getset>();

    Alert_Adapter_vp(ArrayList<alert_getset> list){
        this.c = c;
        this.list = list;
    }



    @Override
    public Alert_Adapter_vp.AlertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_layout, parent, false);
        return new Alert_Adapter_vp.AlertViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Alert_Adapter_vp.AlertViewHolder holder, int position) {
        holder.msgTag.setText(list.get(position).getMsgTag());
        holder.tv_date.setText(list.get(position).getDate());
        holder.tv_title.setText(list.get(position).getTitle());
        holder.tv_content.setText(list.get(position).getContent());
        holder.opend.setText(list.get(position).getOpend());
        holder.mode.setText(list.get(position).getMode());
        holder.imgCheck.setText(list.get(position).getImgCheck());
        holder.imgUrl.setText(list.get(position).getImgUrl());
        holder.labelCode.setText(list.get(position).getLabelCode());
        holder.link.setText(list.get(position).getLink());
        holder.customKeyCheck.setText(list.get(position).getCustomKeyCheck());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AlertViewHolder extends RecyclerView.ViewHolder{
        TextView msgTag, tv_date, tv_title, tv_content, opend, mode, imgCheck, imgUrl, labelCode, link, customKeyCheck;
        public AlertViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            msgTag = itemView.findViewById(R.id.msgtag);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_content);
            opend = itemView.findViewById(R.id.opend);
            mode = itemView.findViewById(R.id.mode);
            imgCheck = itemView.findViewById(R.id.imgCheck);
            imgUrl = itemView.findViewById(R.id.imgUrl);
            labelCode = itemView.findViewById(R.id.labelCode);
            link = itemView.findViewById(R.id.link);
            customKeyCheck = itemView.findViewById(R.id.customKeyCheck);
        }
    }
}
