package com.example.push_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.fingerpush.android.FingerPushManager;
import com.fingerpush.android.NetworkUtility;
import com.fingerpush.android.dataset.DeviceInfo;
import com.fingerpush.android.dataset.PushList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Alert2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Alert2Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Alert2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Alert2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Alert2Fragment newInstance(String param1, String param2) {
        Alert2Fragment fragment = new Alert2Fragment();
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
    ViewPager2 viewPager2;
    Switch sw;
    boolean s;
    Alert_Adapter_vp alert_adapter;
    ArrayList<alert_getset> list = new ArrayList<alert_getset>();
    TextView nomsg;
    BroadcastReceiver broadcastReceiver;
    public static final String MY_ACTION = "com.example.push_app.action.ACTION_MY_BROADCAST";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alert2, container, false);
        viewPager2 = view.findViewById(R.id.viewPager2);
        sw = view.findViewById(R.id.switch1);
        nomsg = view.findViewById(R.id.textView5);

        FingerPushManager.getInstance(view.getContext()).getDeviceInfo(
                new NetworkUtility.ObjectListener() { // 비동기 이벤트 리스너

                    @Override
                    public void onError(String code, String message) {

                    }

                    @Override
                    public void onComplete(String code, String message, JSONObject ObjectData) {
                        String activity = ObjectData.optString(DeviceInfo.ACTIVITY);
                        String ad_activity = ObjectData.optString(DeviceInfo.AD_ACTIVITY);
                        Log.e("뭘로 뜨지", activity);
                        if(activity.equals("A")){
                            s = true;
                            sw.setChecked(s);

                        }else {
                            s = false;
                            sw.setChecked(s);

                        }
                    }

                }
        );

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    s = true;
                    FingerPushManager.getInstance(view.getContext()).setPushEnable(
                            s, // 푸시 활성화 여부 (true : 활성화, false : 비활성화)
                            new NetworkUtility.ObjectListener() { // 비동기 이벤트 리스너

                                @Override
                                public void onError(String code, String message) {

                                }

                                @Override
                                public void onComplete(String code, String message, JSONObject data) {
                                    Log.e("성공여부", "설정 성공");
                                }

                            }
                    );
                } else {
                    s = false;
                    FingerPushManager.getInstance(view.getContext()).setPushEnable(
                            s, // 푸시 활성화 여부 (true : 활성화, false : 비활성화)
                            new NetworkUtility.ObjectListener() { // 비동기 이벤트 리스너

                                @Override
                                public void onError(String code, String message) {

                                }

                                @Override
                                public void onComplete(String code, String message, JSONObject data) {
                                    Log.e("성공여부", "설정x 성공");
                                }

                            }
                    );
                }
            }
        });

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (MY_ACTION.equals(intent.getAction())) {
                    list.clear();
                    FingerPushManager.getInstance(view.getContext()).getPushList(
                            new NetworkUtility.ObjectListener() { // 비동기 이벤트 리스너

                                @Override
                                public void onError(String code, String message) {

                                }

                                @Override
                                public void onComplete(String code, String message, JSONObject object) {
                                    JSONArray jsonArray = null;
                                    try {
                                        jsonArray = object.getJSONArray(PushList.PUSHLIST);

                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            String msgTag = "메시지 태그 : " + jsonArray.getJSONObject(i).optString(PushList.MSGTAG);
                                            String date = "발송 날짜 : " + jsonArray.getJSONObject(i).optString(PushList.DATE);
                                            String title = "메시지 제목 : " + jsonArray.getJSONObject(i).optString(PushList.TITLE);
                                            String content = "메시지 내용 : " + jsonArray.getJSONObject(i).optString(PushList.CONTENT);
                                            String opend = "확인 여부 : " + jsonArray.getJSONObject(i).optString(PushList.OPENED);
                                            String mode = "발송 모드 : " + jsonArray.getJSONObject(i).optString(PushList.MODE);
                                            String imgCheck = "이미지 유무 : " + jsonArray.getJSONObject(i).optString(PushList.IMGCHECK);
                                            String imgUrl = "이미지 주소 : " + jsonArray.getJSONObject(i).optString(PushList.IMGURL);
                                            String labelCode = "라벨 코드 : " + jsonArray.getJSONObject(i).optString(PushList.LABELCODE);
                                            String link = "웹 링크 : " + jsonArray.getJSONObject(i).optString(PushList.LINK);
                                            String customKeyCheck = "커스텀 키 사용 여부 : " + jsonArray.getJSONObject(i).optString(PushList.CODE);
                                            // ※ 커스텀 데이터가 있을 경우만 노출

                                            //String customValue1 = jsonArray.getJSONObject(i).optString("custom_key_1");
                                            //String customValue2 = jsonArray.getJSONObject(i).optString("custom_key_2");
                                            //String customValue3 = jsonArray.getJSONObject(i).optString("custom_key_3");
                                            Log.e("메시지 어케 들어오는지 확인", msgTag + "==" + date + "==" + title + "==" + content + "==" + opend + "==" + mode + "==" + imgCheck + "==" + imgUrl + "==" + labelCode + "==" + link + "==" + customKeyCheck);
                                            list.add(new alert_getset(msgTag, date, title, content, opend, mode, imgCheck, imgUrl, labelCode, link, customKeyCheck));
                                        }
                                        if(jsonArray==null){
                                            nomsg.setVisibility(View.VISIBLE);
                                        }else {
                                            nomsg.setVisibility(View.GONE);
                                            alert_adapter = new Alert_Adapter_vp(list);
                                            alert_adapter.notifyDataSetChanged();

                                            viewPager2.setAdapter(alert_adapter);
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }

                            }
                    );
                }
            }
        };

        FingerPushManager.getInstance(view.getContext()).getPushList(
                new NetworkUtility.ObjectListener() { // 비동기 이벤트 리스너

                    @Override
                    public void onError(String code, String message) {

                    }

                    @Override
                    public void onComplete(String code, String message, JSONObject object) {
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = object.getJSONArray(PushList.PUSHLIST);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                String msgTag = "메시지 태그 : " + jsonArray.getJSONObject(i).optString(PushList.MSGTAG);
                                String date = "발송 날짜 : " + jsonArray.getJSONObject(i).optString(PushList.DATE);
                                String title = "메시지 제목 : " + jsonArray.getJSONObject(i).optString(PushList.TITLE);
                                String content = "메시지 내용 : " + jsonArray.getJSONObject(i).optString(PushList.CONTENT);
                                String opend = "확인 여부 : " + jsonArray.getJSONObject(i).optString(PushList.OPENED);
                                String mode = "발송 모드 : " + jsonArray.getJSONObject(i).optString(PushList.MODE);
                                String imgCheck = "이미지 유무 : " + jsonArray.getJSONObject(i).optString(PushList.IMGCHECK);
                                String imgUrl = "이미지 주소 : " + jsonArray.getJSONObject(i).optString(PushList.IMGURL);
                                String labelCode = "라벨 코드 : " + jsonArray.getJSONObject(i).optString(PushList.LABELCODE);
                                String link = "웹 링크 : " + jsonArray.getJSONObject(i).optString(PushList.LINK);
                                String customKeyCheck = "커스텀 키 사용 여부 : " + jsonArray.getJSONObject(i).optString(PushList.CODE);
                                // ※ 커스텀 데이터가 있을 경우만 노출

                                //String customValue1 = jsonArray.getJSONObject(i).optString("custom_key_1");
                                //String customValue2 = jsonArray.getJSONObject(i).optString("custom_key_2");
                                //String customValue3 = jsonArray.getJSONObject(i).optString("custom_key_3");
                                Log.e("메시지 어케 들어오는지 확인", msgTag + "==" + date + "==" + title + "==" + content + "==" + opend + "==" + mode + "==" + imgCheck + "==" + imgUrl + "==" + labelCode + "==" + link + "==" + customKeyCheck);
                                list.add(new alert_getset(msgTag, date, title, content, opend, mode, imgCheck, imgUrl, labelCode, link, customKeyCheck));

                            }

                            if(jsonArray!=null){
                                nomsg.setVisibility(View.GONE);
                                alert_adapter = new Alert_Adapter_vp(list);
                                alert_adapter.notifyDataSetChanged();

                                viewPager2.setAdapter(alert_adapter);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                }
        );



        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(MY_ACTION);
        getContext().registerReceiver(broadcastReceiver, filter);
    }
    @Override
    public void onPause() {
        super.onPause();
        getContext().unregisterReceiver(broadcastReceiver);
    }
}