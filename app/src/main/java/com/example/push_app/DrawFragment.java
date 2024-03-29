package com.example.push_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

import yuku.ambilwarna.AmbilWarnaDialog;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DrawFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DrawFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public DrawFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DrawFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DrawFragment newInstance(String param1, String param2) {
        DrawFragment fragment = new DrawFragment();
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

    Button menubtn, colorbtn, eraserbtn, savebtn, resetbtn, callbtn;
    LinearLayout menulay;
    Bitmap callImage;
    //int color;

    static class Point {
        float x;
        float y;
        boolean check;
        int color;

        public Point(float x, float y, boolean check, int color) {
            this.x = x;
            this.y = y;
            this.check = check;
            this.color = color;
        }

    }

    //String mode="MODE_PEN";
    class SignView extends View {

        //public int color;
        //public String mode;
        Bitmap bitmap;

        public SignView(Context context) {
            super(context);
            bitmap = null;
            //mode = "MODE_PEN";
        }

        @Override
        protected void onDraw(Canvas canvas) {

            super.onDraw(canvas);
            //canvas.drawColor(Color.WHITE);
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, 0, 0, null);
            }
            Paint paint = new Paint();
            callImage = null;
            int colorbf = 0;


            paint.setStrokeWidth(15);
            paint.setAntiAlias(true); // enable anti aliasing
            paint.setDither(true); // enable dithering
            paint.setStyle(Paint.Style.STROKE); // set to STOKE
            paint.setStrokeJoin(Paint.Join.ROUND); // set the join to round you want
            paint.setStrokeCap(Paint.Cap.ROUND);  // set the paint cap to round too
            for (int i = 1; i < points.size(); i++) {
                paint.setColor(points.get(i).color);
                if (!points.get(i).check) {
                    continue;
                }
                canvas.drawLine(points.get(i - 1).x, points.get(i - 1).y, points.get(i).x, points.get(i).y, paint);
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    points.add(new Point(x, y, false, colorr));
                case MotionEvent.ACTION_MOVE:
                    points.add(new Point(x, y, true, colorr));
                    break;
                case MotionEvent.ACTION_UP:
                    break;

            }
            invalidate();
            return true;

        }


    }

    ArrayList<Point> points = new ArrayList<Point>();
    //ArrayList<Point> erase = new ArrayList<Point>();
    ConstraintLayout pan;
    int colorr = Color.BLACK;
    static int cnt = 0;
    int mSelect=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_draw, container, false);
        final SignView signView = new SignView(view.getContext());
        menubtn = view.findViewById(R.id.menubtn);
        colorbtn = view.findViewById(R.id.colorbtn);
        //eraserbtn = view.findViewById(R.id.eraserbtn);
        savebtn = view.findViewById(R.id.savebtn);
        resetbtn = view.findViewById(R.id.resetbtn);
        callbtn = view.findViewById(R.id.callbtn);
        menulay = view.findViewById(R.id.linearLayout);
        pan = view.findViewById(R.id.pan);


        menubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int state = menulay.getVisibility();
                Log.d("숫자로 뜬다고?", state + " ");
                if (state == 8) {
                    menulay.setVisibility(View.VISIBLE);
                } else {
                    menulay.setVisibility(View.GONE);
                }
            }
        });

        int[] initialColor = {Color.BLACK};
        //signView.color = Color.BLACK;
        colorbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AmbilWarnaDialog dialog = new AmbilWarnaDialog(view.getContext(), initialColor[0], new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        // color is the color selected by the user.
                        initialColor[0] = color;
                        colorr = color;
                        //signView.color = color;
                    }

                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                        // cancel was selected by the user
                    }
                });
                dialog.show();
            }
        });
        /*eraserbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signView.mode = "MODE_ERASER";
                Log.e("뭐지", signView.mode);
                //signView.bitmap = null;
            }
        });*/
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*signView.invalidate();
                signView.setDrawingCacheEnabled(true);
                Bitmap save = Bitmap.createBitmap(signView.getDrawingCache());
                signView.setDrawingCacheEnabled(false);

                File savePic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

                if(!savePic.exists()){
                    savePic.mkdirs();
                }
                FileOutputStream fos;
                try {
                    fos = new FileOutputStream(new File(savePic, "sign.png"));
                    save.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.close();
                    Toast.makeText(view.getContext(), "저장 성공"+savePic, Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Log.e("photo","그림저장오류",e);
                    Toast.makeText(view.getContext(), "저장 실패", Toast.LENGTH_SHORT).show();
                }*/
                File savePic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                /*File file = null;
                if(cnt>=0){
                    file = new File(savePic + "/sign"+cnt+".png"); //임의로 sdcard에 test.png로 저장
                    cnt++;
                }*/
                File file = new File(savePic + "/sign.png"); //임의로 sdcard에 test.png로 저장
                OutputStream outputStream = null;

                try {
                    file.createNewFile();
                    outputStream = new FileOutputStream(file);

                    signView.buildDrawingCache();
                    Bitmap bitmap = signView.getDrawingCache();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    signView.destroyDrawingCache();
                    Toast.makeText(view.getContext(), "저장 성공", LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(view.getContext(), "저장 실패", LENGTH_SHORT).show();
                } /*finally {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }*/
            }
        });
        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                points.clear();
                signView.bitmap = null;
                signView.invalidate();
            }
        });
        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File savePic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                //Bitmap bm = BitmapFactory.decodeFile(savePic + "/sign.png").copy(Bitmap.Config.ARGB_8888, true);
                Bitmap bm = BitmapFactory.decodeFile(savePic + "/sign.png");
                Log.d("photo", savePic + "/sign.png");
                signView.bitmap = bm;
                signView.invalidate();
                Toast.makeText(view.getContext(), "불러오기 : sign.png", LENGTH_SHORT).show();

                //String[] fname = new String[0];

                /*for(int i =0;i>=cnt;i++){
                    fname[i] = "sign"+i+".png";
                }
                new AlertDialog.Builder(view.getContext())
                .setTitle("불러올 파일 선택")
                .setSingleChoiceItems(Integer.parseInt(fname[0]), mSelect, new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });*/

                /* try {
                    FileInputStream fis = null;
                    try {
                        fis = new FileInputStream(savePic + "/sign.png");
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }

                    //객체를 읽어들이기 위해서 objectInputStream으로 감싼다
                    ObjectInputStream ois = null;
                    try {
                        ois = new ObjectInputStream(fis);
                        Log.d("dkdkdkdkkd", " "+ois);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                    //객체를 읽어와서 원래 데이터 형태로 형변환한다.
                    ArrayList<Point> readedObject = new ArrayList<Point>();
                    try {
                        int data = 0;
                        while ((data = ois.read()) != -1){
                            Log.d("dkdkdkdkkd", " "+data);
                        }
                        readedObject = (ArrayList<Point>) ois.readObject();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                    //읽어온 객체를 View에 전달한다.
                    points.clear();
                    //points = readedObject;

                    //화면을 갱신해서 현재 객체의 정보를 바탕으로 화면을 그린다.
                    signView.invalidate();
                    Toast.makeText(view.getContext(), "불러오기성공!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("불러오기실패:", e.getMessage());
                    Toast.makeText(view.getContext(), "불러오기실패!", Toast.LENGTH_SHORT).show();
                }
                try{
                    //파일에서 읽어오기 위한 스트림 객체 얻어오기(약속된 위치가 있기 때문에 경로는 써주지 않아도 된다)
                    File savePic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    FileInputStream fis = new FileInputStream (new File(savePic + "/sign.png"));


                    //객체를 읽어들이기 위해서 objectInputStream으로 감싼다
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    fis.close();
                    //객체를 읽어와서 원래 데이터 형태로 형변환한다.
                    ArrayList<Point> readedObject = (ArrayList<Point>)ois.readObject();

                    //읽어온 객체를 View에 전달한다.
                    points = readedObject;

                    //화면을 갱신해서 현재 객체의 정보를 바탕으로 화면을 그린다.
                    signView.invalidate();
                    Toast.makeText(view.getContext(), "불러오기성공!", Toast.LENGTH_SHORT).show();
                } catch(Exception e){
                    Log.e("불러오기실패:", String.valueOf(e));
                    Toast.makeText(view.getContext(), "불러오기실패!", Toast.LENGTH_SHORT).show();
                }*/

            }
        });
        pan.addView(signView);

        return view;
    }

}