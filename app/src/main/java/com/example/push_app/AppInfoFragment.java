package com.example.push_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fingerpush.android.FingerPushManager;
import com.fingerpush.android.NetworkUtility;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppInfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AppInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AppInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AppInfoFragment newInstance(String param1, String param2) {
        AppInfoFragment fragment = new AppInfoFragment();
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
    ArrayList<appinfo_getset> list = new ArrayList<appinfo_getset>();
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_info, container, false);
        recyclerView = view.findViewById(R.id.recyclerView10);
        //앱 정보 파싱 후 화면에 출력
        FingerPushManager.getInstance(view.getContext()).getAppReport(
                new NetworkUtility.ObjectListener() { // 비동기 이벤트 리스너

                    @Override
                    public void onError(String code, String message) {

                    }

                    @Override
                    public void onComplete(String code, String message, JSONObject data) {
                        String AppKey = data.optString("appid");
                        String AppName = data.optString("app_name");
                        String User_Id = data.optString("user_id");
                        String Icon = data.optString("icon");
                        String Category = data.optString("category");
                        String Environments = data.optString("environments");
                        String BeAndroid = data.optString("beandroid");
                        String VersionName = data.optString("android_version");
                        int VersionCode = data.optInt("android_int_version");
                        String AndroidUpdateLink = data.optString("android_upd_link");
                        String BeUpdateLink = data.optString("beupdalert_a");
                        String UpdateDate = data.optString("ver_update_date_a");

                        list.add(new appinfo_getset("App Key", AppKey));
                        list.add(new appinfo_getset("App Name", AppName));
                        list.add(new appinfo_getset("User ID", User_Id));
                        list.add(new appinfo_getset("Icon", Icon));
                        list.add(new appinfo_getset("Category", Category));
                        list.add(new appinfo_getset("Environments", Environments));
                        list.add(new appinfo_getset("Be Android", BeAndroid));
                        list.add(new appinfo_getset("Version Name", VersionName));
                        list.add(new appinfo_getset("Version", String.valueOf(VersionCode)));
                        list.add(new appinfo_getset("Android Update Link", AndroidUpdateLink));
                        list.add(new appinfo_getset("iOS Update Link", BeUpdateLink));
                        list.add(new appinfo_getset("Version Update Date", UpdateDate));

                        Log.e("리스트 보여주세요", String.valueOf(list));
                        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                        recyclerView.setAdapter(new appInfoAdapter(list));
                    }

                }
        );


        return view;
    }
}