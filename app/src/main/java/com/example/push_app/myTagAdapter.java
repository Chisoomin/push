package com.example.push_app;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fingerpush.android.FingerPushManager;
import com.fingerpush.android.NetworkUtility;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;

public class myTagAdapter extends RecyclerView.Adapter<myTagAdapter.myTagViewHolder>{
    ArrayList<mytag_getset> list = new ArrayList<mytag_getset>();
    Context context;
    TextView notag;

    public myTagAdapter(ArrayList<mytag_getset> list, Context context, TextView notag) {
        this.list = list;
        this.context = context;
        this.notag = notag;
    }

    @NonNull
    @NotNull
    @Override
    public myTagViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mytag_recycle, parent, false);
        return new myTagAdapter.myTagViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull myTagViewHolder holder, int position) {
        holder.xbtn.setVisibility(View.VISIBLE);
        holder.mytag.setText(list.get(position).getMytag());
        holder.tagdate.setText(list.get(position).getTagdate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myTagViewHolder extends RecyclerView.ViewHolder {
        ImageButton xbtn;
        TextView mytag, tagdate;
        public myTagViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            xbtn = itemView.findViewById(R.id.xbtn);
            mytag = itemView.findViewById(R.id.mytagname);
            tagdate = itemView.findViewById(R.id.datetag);


            xbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        String tagitem = list.get(pos).getMytag();
                        String tagdateitem = list.get(pos).getTagdate();

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("삭제하시겠습니까?");
                        builder.setPositiveButton("확인",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        FingerPushManager.getInstance(context).removeTag(
                                                tagitem, // 태그값
                                                new NetworkUtility.ObjectListener() { // 비동기 이벤트 리스너

                                                    @Override
                                                    public void onError(String code, String message) {

                                                    }

                                                    @Override
                                                    public void onComplete(String code, String message, JSONObject data) {
                                                        list.remove(pos);
                                                        notifyItemRemoved(pos);
                                                        notifyItemRangeChanged(pos, list.size());
                                                        if(list.size()==0){
                                                            notag.setVisibility(View.VISIBLE);
                                                        }
                                                    }

                                                }
                                        );

                                        /*tagDBHelper dbHelper = new tagDBHelper(context);
                                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                                        db.delete("tagsave", "tagname = ? and tagdate = ?", new String[]{tagitem, tagdateitem});
                                        db.close();*/
                                    }
                                });
                        builder.setNegativeButton("취소",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        builder.show();


                    }
                }
            });
        }

    }

}
