package com.example.univ_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyInfoFragment newInstance(String param1, String param2) {
        MyInfoFragment fragment = new MyInfoFragment();
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
    public static TextView ID;
    public static TextView Email;
    public static TextView MajorGender;

    @Override
    public void onActivityCreated(Bundle b){
        super.onActivityCreated(b);


        ID = (TextView) getView().findViewById(R.id.myInfoID);
        Email = (TextView) getView().findViewById(R.id.myInfoEmail);
        MajorGender = (TextView) getView().findViewById(R.id.myInfoMajorGender);
        Button logoutButton = (Button)getView().findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                AlertDialog dialog = builder.setMessage("로그아웃 하시겠습니까? ")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getActivity(), "로그인 화면으로 이동합니다", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(),LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            }
                        })
                        .setNegativeButton("아니오",null)
                        .create();
                        dialog.show();
            }
        });

        Button kwHomepage = (Button)getView().findViewById(R.id.kwHomepageButton);
        Button kwSchedule = (Button)getView().findViewById(R.id.kwScheduleButton);
        Button kwKlas = (Button)getView().findViewById(R.id.kwKlasButton);


        kwHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.kw.ac.kr"));
                startActivity(intent);
            }
        });

        kwSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.kw.ac.kr/ko/life/bachelor_calendar.jsp"));
                startActivity(intent);
            }
        });

        kwKlas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://klas.kw.ac.kr"));
                startActivity(intent);
            }
        });

        new BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {

        String target;

        @Override
        protected void onPreExecute(){
            try{
                target = "http://rkdalswn1209.cafe24.com/MydataList.php?userID=" + URLEncoder.encode(MainActivity.userID, "UTF-8");
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); // 해당 서버에 접속하기위한 connection
                InputStream inputStream = httpURLConnection.getInputStream();   // 넘어오는 결과값들을 그대로 저장
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); // buffer에 담아서 읽게 만들도록 함
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null){      // buffer에서 받아온 값을 한줄씩 읽음
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();             // 처리가 끝난후, 연결 해제
                return stringBuilder.toString().trim();     // 문자열 반환
            }                                   // 데이터를 얻는 부분
            catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result){
            try{
                JSONObject jsonObject = new JSONObject(result);     //응답 부분(response) 처리
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String myID;
                String myGender;
                String myMajor;
                String myEmail;

                while(count < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);

                    myID = object.getString("userID");
                    myGender = object.getString("userGender");
                    myMajor = object.getString("userMajor");
                    myEmail = object.getString("userEmail");
                    ID.setText(" "+ myID);
                    Email.setText(" "+ myEmail);
                    MajorGender.setText(" "+ myMajor + " (" + myGender+")");
                    count++;
                  }

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_myinfo, container, false);
    }
}
