package com.example.push_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GuideFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuideFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GuideFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GuideFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GuideFragment newInstance(String param1, String param2) {
        GuideFragment fragment = new GuideFragment();
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
    TextView app, guide;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_guide, container, false);
        //아마도 settext 사용해서 입력
        app = view.findViewById(R.id.textView6);
        guide = view.findViewById(R.id.textView8);

        app.setText("\'핑거푸시 라이브\'를 통해 푸시 알림을 직접 발송해 보고, 라이브 앱에서 수신된 푸시 알림을 확인하실 수 있습니다. 라이브 앱에서는 \'Pro\' 등급의 기능이 제공되어, 전체 발송, 그룹발송, 타겟발송을 이용하실 수 있습니다.\n\n");
        guide.setText("① 핑거푸시 홈페이지에서 라이브 앱 계정으로 로그인\n·URL : www.fingerpush.com\n·ID : partner@kissoft.co.kr\n·PW : partner1234\n②우측 상단 사용자 아이디 클릭 ➔ SERVICE ➔ SEND PUSH 메뉴에서 푸시 발송\n③ 라이브 앱 내 \'알림\' 메뉴에서 수신된 푸시 알림 확인\n\n");

        return view;
    }
}