package com.example.push_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.fingerpush.android.FingerPushManager;
import com.fingerpush.android.NetworkUtility;
import com.fingerpush.android.dataset.DeviceInfo;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TargetingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TargetingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TargetingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TargetingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TargetingFragment newInstance(String param1, String param2) {
        TargetingFragment fragment = new TargetingFragment();
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
    EditText idset;
    Button register;
    ImageButton buttonx;
    TextView idview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_targeting, container, false);
        //태그와 비슷한 형태, 1개 이상 저장 불가
        idset = view.findViewById(R.id.idset);
        register = view.findViewById(R.id.button);
        buttonx = view.findViewById(R.id.x);
        idview = view.findViewById(R.id.idview);
        FingerPushManager.getInstance(view.getContext()).getDeviceInfo(
                new NetworkUtility.ObjectListener() { // 비동기 이벤트 리스너

                    @Override
                    public void onError(String code, String message) {

                    }

                    @Override
                    public void onComplete(String code, String message, JSONObject ObjectData) {
                        String identity = ObjectData.optString(DeviceInfo.IDENTITY);
                        Log.e("식별자", identity+" ");
                        if(identity.equals("")){

                        }else {
                            idview.setText(identity);
                            buttonx.setVisibility(View.VISIBLE);
                        }
                    }

                }
        );
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idset.getText().toString();
                idset.setText("");

                FingerPushManager.getInstance(view.getContext()).setIdentity(
                        id,  // 식별자 값
                        new NetworkUtility.ObjectListener() { // 비동기 이벤트 리스너

                            @Override
                            public void onError(String code, String message) {

                            }

                            @Override
                            public void onComplete(String code, String message, JSONObject ObjectData) {
                                idview.setText(id);
                                buttonx.setVisibility(View.VISIBLE);
                            }

                        }
                );

            }
        });

        buttonx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("삭제하시겠습니까?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        FingerPushManager.getInstance(view.getContext()).removeIdentity(
                                new NetworkUtility.ObjectListener() { // 비동기 이벤트 리스너

                                    @Override
                                    public void onError(String code, String message) {

                                    }

                                    @Override
                                    public void onComplete(String code, String message, JSONObject ObjectData) {
                                        idview.setText("식별자를 등록하세요.");
                                        buttonx.setVisibility(View.INVISIBLE);
                                    }

                                }
                        );
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });


        return view;

    }
}