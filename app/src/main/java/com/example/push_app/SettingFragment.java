package com.example.push_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.fingerpush.android.FingerPushManager;
import com.fingerpush.android.NetworkUtility;
import com.fingerpush.android.dataset.DeviceInfo;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
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
    Switch sw1,sw2;
    boolean s1,s2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        //스위치에서 값 받아오기
        sw1 = view.findViewById(R.id.switch2);
        sw2 = view.findViewById(R.id.switch3);

        FingerPushManager.getInstance(view.getContext()).getDeviceInfo(
                new NetworkUtility.ObjectListener() { // 비동기 이벤트 리스너

                    @Override
                    public void onError(String code, String message) {

                    }

                    @Override
                    public void onComplete(String code, String message, JSONObject ObjectData) {
                        String activity = ObjectData.optString(DeviceInfo.ACTIVITY);
                        String ad_activity = ObjectData.optString(DeviceInfo.AD_ACTIVITY);
                        if(activity.equals("A")){
                            s1 = true;
                            sw1.setChecked(s1);

                        }else {
                            s1 = false;
                            sw1.setChecked(s1);

                        }
                        if(ad_activity.equals("A")){
                            s2= true;
                            sw2.setChecked(s2);

                        }else {
                            s2 = false;
                            sw2.setChecked(s2);

                        }
                    }

                }
        );
        sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    s1 = true;
                    FingerPushManager.getInstance(view.getContext()).setPushEnable(
                            s1, // 푸시 활성화 여부 (true : 활성화, false : 비활성화)
                            new NetworkUtility.ObjectListener() { // 비동기 이벤트 리스너

                                @Override
                                public void onError(String code, String message) {

                                }

                                @Override
                                public void onComplete(String code, String message, JSONObject data) {

                                }

                            }
                    );
                }else {
                    s1 = false;
                    FingerPushManager.getInstance(view.getContext()).setPushEnable(
                            s1, // 푸시 활성화 여부 (true : 활성화, false : 비활성화)
                            new NetworkUtility.ObjectListener() { // 비동기 이벤트 리스너

                                @Override
                                public void onError(String code, String message) {

                                }

                                @Override
                                public void onComplete(String code, String message, JSONObject data) {

                                }

                            }
                    );
                }
            }
        });

        sw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    s2 = true;
                    FingerPushManager.getInstance(view.getContext()).setAdvertisePushEnable(
                            s2, // 광고 푸시 활성화 여부 (true : 활성화, false : 비활성화)
                            new NetworkUtility.ObjectListener() {
                                @Override
                                public void onComplete(String s, String s1, JSONObject jsonObject) {

                                }

                                @Override
                                public void onError(String s, String s1) {

                                }
                            }
                    );
                }else {
                    s2 = false;
                    FingerPushManager.getInstance(view.getContext()).setAdvertisePushEnable(
                            s2, // 광고 푸시 활성화 여부 (true : 활성화, false : 비활성화)
                            new NetworkUtility.ObjectListener() {
                                @Override
                                public void onComplete(String s, String s1, JSONObject jsonObject) {

                                }

                                @Override
                                public void onError(String s, String s1) {

                                }
                            }
                    );
                }
            }
        });

        return view;
    }
}