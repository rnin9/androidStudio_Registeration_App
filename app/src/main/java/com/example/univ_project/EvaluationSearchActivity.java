package com.example.univ_project;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

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

public class EvaluationSearchActivity extends AppCompatActivity {

    private ListView evalListView;
    private EvaluationListAdapter adapter;
    private static String evalSearch;
    private static String evaluationProfessor;
    private AlertDialog dialog;
    private ArrayList<Evaluation> evalArrayList;
    public static String backupID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evalsearch);
        final EditText evalSearchText = (EditText) findViewById(R.id.evalSearch);
        final Button evalSearchButton = (Button) findViewById(R.id.evalSearchButton);
        final Button evalSearchBackButton = (Button) findViewById(R.id.evalSearchBackButton);


        backupID = getIntent().getStringExtra("backupID");
        evalListView =(ListView)findViewById(R.id.evalSearchListView);
        evalArrayList = new ArrayList<Evaluation>();
        adapter = new EvaluationListAdapter(getApplicationContext(), evalArrayList);
        evalListView.setAdapter(adapter);                 // adpater, list 설정

        evalSearchButton.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                evalSearch = evalSearchText.getText().toString();
                evalSearchText.setText(evalSearch);
                new BackgroundTask().execute();

            }
        });

        evalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String evalTitle = evalArrayList.get(position).getEvalTitle();
                String evalProfessor = evalArrayList.get(position).getEvalProfessor();
                String evalMajor = evalArrayList.get(position).getEvalMajor();

                Intent intent = new Intent(EvaluationSearchActivity.this, EstimationActivity.class); // 성공시 이동
                intent.putExtra("EstimateTitle",evalTitle);
                intent.putExtra("EstimateProfessor",evalProfessor);
                intent.putExtra("EstimateMajor",evalMajor);
                intent.putExtra("EstimateUser",backupID);
                EvaluationSearchActivity.this.startActivity(intent);
            }
        });

        evalSearchBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EvaluationSearchActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
    });
    }
    @Override
    protected void onStop(){
        super.onStop();
        if(dialog != null) {
        dialog.dismiss();
        dialog = null;
        }
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {

        String target;

        @Override
        protected void onPreExecute(){
            try{
                target = "http://rkdalswn1209.cafe24.com/EvaluationList.php?evalSearch=" + URLEncoder.encode(evalSearch, "UTF-8");
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
                evalArrayList.clear();     //강의 목록 clear
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;

                String evalTitle; // 평가 과목명
                String evalProfessor; // 평가 과목 담당교수
                String evalMajor; // 평가 과목 전공
                if(evalSearch.equals("")){

                    AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(EvaluationSearchActivity.this);
                    dialog = builder.setMessage("검색된 강의가 없습니다.\n")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                }
                else{

                while(count < jsonArray.length())    //모든배열의 원소를 전부 돌면서 실행.
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    //각각의 변수에 해당 jsonobject를 통해서 가져온 문자열의 파싱된 내용을 가져온다.
                    //매번 변수에 각각의 강의정보가 들어가게됨.

                    evalTitle = object.getString("evalTitle");
                    evalProfessor = object.getString("evalProfessor");
                    evalMajor = object.getString("evalMajor");
                    Evaluation evaluation = new Evaluation(evalTitle,evalProfessor,evalMajor);
                    evalArrayList.add(evaluation);
                    count++;
                }
                if(count == 0){
                    AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(EvaluationSearchActivity.this);
                    dialog = builder.setMessage("검색된 강의가 없습니다.\n")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                }
                }
                adapter.notifyDataSetChanged();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}


