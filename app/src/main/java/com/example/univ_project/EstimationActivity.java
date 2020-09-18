package com.example.univ_project;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class EstimationActivity extends AppCompatActivity {

    private ListView estimationListView;
    private EstimationListAdapter adapter;
    private ArrayList<Estimation> estimationList;            //class 멤버 변수
    public static String estimateTitle;
    public static String estimateProfessor;
    public static String estimateMajor;
    public static String estimateUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimate);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 세로 고정

        estimateTitle = getIntent().getStringExtra("EstimateTitle");
        estimateMajor = getIntent().getStringExtra("EstimateMajor");
        estimateProfessor = getIntent().getStringExtra("EstimateProfessor");
        estimateUser = getIntent().getStringExtra("EstimateUser");

        TextView estT = (TextView)findViewById(R.id.estimationTitle);
        TextView estM = (TextView)findViewById(R.id.estimationMajor);
        TextView estP = (TextView)findViewById(R.id.estimationProfessor);
        estT.setText(estimateTitle);
        estM.setText(estimateMajor);
        estP.setText(estimateProfessor+" 교수님");


        final LinearLayout estH = (LinearLayout)findViewById(R.id.estimationHeader);
        final Button estimationBackButton = (Button) findViewById(R.id.estimationBackButton);
        final Button estimationWritingButton = (Button) findViewById(R.id.estimationWritingButton);
        final Button estimationListButton = (Button) findViewById(R.id.estimationListButton);


        estimationListView =(ListView)findViewById(R.id.estimationListView);
        estimationList = new ArrayList<Estimation>();
        adapter = new EstimationListAdapter(getApplicationContext(), estimationList);
        estimationListView.setAdapter(adapter);                 // adpater, list 설정


        estimationListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estimationListButton.setBackgroundColor(Color.parseColor("#77212E"));
                estimationWritingButton.setBackgroundColor(Color.parseColor("#BA1C19"));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentEstimate, new EstimationMyListFragment());
                fragmentTransaction.commit();
            }
        });

        estimationWritingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                estimationListView.setVisibility(view.GONE);
                estH.setVisibility(view.GONE);
                estimationListButton.setBackgroundColor(Color.parseColor("#BA1C19"));
                estimationWritingButton.setBackgroundColor(Color.parseColor("#77212E"));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentEstimate, new EstimationWriteFragment());
                fragmentTransaction.commit();
            }
        });

        estimationBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EstimationActivity.this,EvaluationSearchActivity.class);
                intent.putExtra("backupID",MainActivity.userID);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        new BackgroundTask().execute();


    }

    class BackgroundTask extends AsyncTask<Void, Void, String>{

        String target;

        @Override
        protected void onPreExecute(){
            try {
                target = "http://rkdalswn1209.cafe24.com/EstimationList.php?estimationTitle=" + URLEncoder.encode(estimateTitle, "UTF-8") +"&estimationProfessor="+URLEncoder.encode(estimateProfessor,"UTF-8");
            } catch (UnsupportedEncodingException e) {
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
                String estYear,estTerm,estContent,rating;           // 모든 공지사항 list 추가
                while(count < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    rating = object.getString("estRating");
                    estContent = object.getString("estContent");
                    estTerm = object.getString("estTerm");
                    estYear = object.getString("estYear");
                    Estimation estimation = new Estimation(rating, estYear,estTerm, estContent);
                    estimationList.add(estimation);
                    adapter.notifyDataSetChanged();
                    count++;
                }

//                if(count==0){
//                    rating = object.getInt("estRating");
//                    estContent = object.getString("estContent");
//                    estTerm = object.getString("estTerm");
//                    estYear = object.getString("estYear");
//                    Estimation estimation = new Estimation(rating, estYear,estTerm, estContent);
//                    estimationList.add(estimation);
//                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

  }
}
