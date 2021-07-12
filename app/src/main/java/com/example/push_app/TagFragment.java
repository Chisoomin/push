package com.example.push_app;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fingerpush.android.FingerPushManager;
import com.fingerpush.android.NetworkUtility;
import com.fingerpush.android.dataset.TagList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TagFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TagFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TagFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TagFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TagFragment newInstance(String param1, String param2) {
        TagFragment fragment = new TagFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    EditText tagset;
    Button register, alltag, mytag;
    String edittag;
    RecyclerView recyclerView;
    ArrayList<tag_getset> listt = new ArrayList<tag_getset>();
    ArrayList<mytag_getset> list = new ArrayList<mytag_getset>();
    TagAdapter adapter;
    myTagAdapter myTagAdapter;
    TextView notag;
    boolean res;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tag, container, false);
        //edittext에서 값 받아오고, 다이얼로그로 삭제 구현
        //태그 손보고 수신 동의 스위치들 값 받아서 구현
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String getTime = sdf.format(date);

        tagset = view.findViewById(R.id.tagset);
        register = view.findViewById(R.id.button);
        alltag = view.findViewById(R.id.button2);
        mytag = view.findViewById(R.id.button3);
        notag = view.findViewById(R.id.textView4);

        recyclerView = view.findViewById(R.id.recyclerView222);

        FingerPushManager.getInstance(view.getContext()).getAllTag(
                new NetworkUtility.ObjectListener() { // 비동기 이벤트 리스너

                    @Override
                    public void onError(String code, String message) {

                    }

                    @Override
                    public void onComplete(String code, String message, JSONObject data) {
                        try {
                            JSONArray ArrayData = data.getJSONArray(TagList.TAGLIST);
                            if (ArrayData.length() > 0) {
                                ArrayList<TagList> dataList = new ArrayList<TagList>();
                                TagList list = null;
                                for (int i = 0; i < ArrayData.length(); i++) {
                                    list = new TagList();
                                    list.date = ArrayData.getJSONObject(i).optString("date");
                                    list.tag = ArrayData.getJSONObject(i).optString("tag");
                                    String date = ArrayData.getJSONObject(i).optString("date");
                                    String tag = ArrayData.getJSONObject(i).optString("tag");
                                    dataList.add(list);

                                    Log.e("tag", date + tag);
                                    listt.add(new tag_getset(tag, date));
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        alltag.setBackgroundResource(R.drawable.button_after);
                        adapter = new TagAdapter(listt, tagset);
                        adapter.notifyDataSetChanged();

                        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                        recyclerView.setAdapter(adapter);
                        res = true;
                    }


                }
        );

        alltag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                res = true;

                notag.setVisibility(View.GONE);
                alltag.setBackgroundResource(R.drawable.button_after);
                mytag.setBackgroundResource(R.drawable.button_before);

                FingerPushManager.getInstance(view.getContext()).getAllTag(
                        new NetworkUtility.ObjectListener() { // 비동기 이벤트 리스너

                            @Override
                            public void onError(String code, String message) {

                            }

                            @Override
                            public void onComplete(String code, String message, JSONObject data) {
                                try {
                                    JSONArray ArrayData = data.getJSONArray(TagList.TAGLIST);
                                    if (ArrayData.length() > 0) {
                                        ArrayList<TagList> dataList = new ArrayList<TagList>();
                                        TagList list = null;
                                        if (listt.size() > 0) {
                                            listt.clear();
                                        }
                                        for (int i = 0; i < ArrayData.length(); i++) {
                                            list = new TagList();
                                            list.date = ArrayData.getJSONObject(i).optString("date");
                                            list.tag = ArrayData.getJSONObject(i).optString("tag");
                                            String date = ArrayData.getJSONObject(i).optString("date");
                                            String tag = ArrayData.getJSONObject(i).optString("tag");
                                            dataList.add(list);

                                            Log.e("tag", date + tag);
                                            listt.add(new tag_getset(tag, date));
                                        }
                                        adapter = new TagAdapter(listt, tagset);
                                        adapter.notifyDataSetChanged();

                                        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                                        recyclerView.setAdapter(adapter);

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                );


            }
        });

        mytag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                res = false;

                listt.clear();
                adapter = new TagAdapter(listt, tagset);
                adapter.notifyDataSetChanged();

                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                recyclerView.setAdapter(adapter);
                alltag.setBackgroundResource(R.drawable.button_before);
                mytag.setBackgroundResource(R.drawable.button_after);
                if (list.size() > 0) {
                    list.clear();
                }

                //th.start();

                FingerPushManager.getInstance(view.getContext()).getDeviceTag(
                        new NetworkUtility.ObjectListener() { // 비동기 이벤트 리스너
                            @Override
                            public void onError(String code, String message) {
                            }

                            @Override
                            public void onComplete(String code, String message, JSONObject data) {
                                JSONArray ArrayData = null;
                                try {
                                    ArrayData = data.getJSONArray(TagList.TAGLIST);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                ArrayList<TagList> dataList = new ArrayList<TagList>();
                                //TagList list = null;
                                if(ArrayData==null){
                                    notag.setVisibility(View.VISIBLE);
                                }else {
                                    notag.setVisibility(View.GONE);
                                    for (int i = 0; i < ArrayData.length(); i++) {
                                        //list = new TagList();
                                        try {
                                            //list.date = ArrayData.getJSONObject(i).optString("date");
                                            //list.tag = ArrayData.getJSONObject(i).optString("tag");
                                            String date = ArrayData.getJSONObject(i).optString("date");
                                            String tagname = ArrayData.getJSONObject(i).optString("tag");
                                            list.add(new mytag_getset(tagname, date));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        //dataList.add(list);

                                    }
                                }

                                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                                recyclerView.setAdapter(new myTagAdapter(list, view.getContext(),notag));
                            }

                        }
                );
                /*tagDBHelper dbHelper2 = new tagDBHelper(view.getContext());
                SQLiteDatabase db2 = dbHelper2.getReadableDatabase();
                Cursor cursor = db2.rawQuery("select tagname, tagdate from tagsave;", null);
                while (cursor.moveToNext()) {
                    String tagname = cursor.getString(0);
                    String tagdate = cursor.getString(1);
                    Log.e("데이터 불러오기 확인", tagname + tagdate + " ");
                    list.add(new mytag_getset(tagname, tagdate));
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                recyclerView.setAdapter(new myTagAdapter(list, view.getContext()));*/
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edittag = tagset.getText().toString();
                tagset.setText("");

                if(res==false){
                    FingerPushManager.getInstance(view.getContext()).setTag(
                            edittag, // 태그값
                            new NetworkUtility.ObjectListener() { // 비동기 이벤트 리스너
                                @Override
                                public void onError(String code, String message) {
                                    Log.e("왜 에러가 날까유", code + message);
                                }

                                @Override
                                public void onComplete(String code, String message, JSONObject data) {
                                    list.clear();
                                    notag.setVisibility(View.GONE);

                                    FingerPushManager.getInstance(view.getContext()).getDeviceTag(
                                            new NetworkUtility.ObjectListener() { // 비동기 이벤트 리스너
                                                @Override
                                                public void onError(String code, String message) {
                                                }

                                                @Override
                                                public void onComplete(String code, String message, JSONObject data) {
                                                    JSONArray ArrayData = null;
                                                    try {
                                                        ArrayData = data.getJSONArray(TagList.TAGLIST);
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    ArrayList<TagList> dataList = new ArrayList<TagList>();
                                                    //TagList list = null;
                                                    if(ArrayData==null){
                                                        notag.setVisibility(View.VISIBLE);
                                                    }else {
                                                        notag.setVisibility(View.GONE);
                                                        for (int i = 0; i < ArrayData.length(); i++) {
                                                            //list = new TagList();
                                                            try {
                                                                //list.date = ArrayData.getJSONObject(i).optString("date");
                                                                //list.tag = ArrayData.getJSONObject(i).optString("tag");
                                                                String date = ArrayData.getJSONObject(i).optString("date");
                                                                String tagname = ArrayData.getJSONObject(i).optString("tag");
                                                                list.add(new mytag_getset(tagname, date));
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                            //dataList.add(list);

                                                        }
                                                    }

                                                    recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                                                    recyclerView.setAdapter(new myTagAdapter(list, view.getContext(),notag));
                                                }

                                            }
                                    );
                                }
                            }
                    );
                } else {
                    FingerPushManager.getInstance(view.getContext()).setTag(
                            edittag, // 태그값
                            new NetworkUtility.ObjectListener() { // 비동기 이벤트 리스너
                                @Override
                                public void onError(String code, String message) {
                                    Log.e("왜 에러가 날까유", code + message);
                                }

                                @Override
                                public void onComplete(String code, String message, JSONObject data) {
                                    list.clear();
                                    notag.setVisibility(View.GONE);

                                }
                            }
                    );
                }

            }
        });


        return view;
    }
}