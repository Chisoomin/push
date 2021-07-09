package com.example.push_app;

import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fingerpush.android.dataset.TagList;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.TagViewHolder>{
    ArrayList<tag_getset> list = new ArrayList<tag_getset>();
    EditText editText;

    public TagAdapter(ArrayList<tag_getset> listt, EditText editText) {
        this.list = listt;
        this.editText = editText;
    }



    @NonNull
    @NotNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_recycle, parent, false);
        return new TagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TagViewHolder holder, int position) {
        holder.tagname.setText(list.get(position).getTag());
        holder.tagdate.setText(list.get(position).getDate());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TagViewHolder extends RecyclerView.ViewHolder{
        TextView tagname, tagdate;
        public TagViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tagname = itemView.findViewById(R.id.tagname);
            tagdate = itemView.findViewById(R.id.tagdate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        String item = list.get(pos).getTag();
                        editText.setText(item);
                    }
                }
            });
        }

        
    }
}
