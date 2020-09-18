package com.example.univ_project;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

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


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EstimationMyListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EstimationMyListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EstimationMyListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CourseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EstimationMyListFragment newInstance(String param1, String param2) {
        EstimationMyListFragment fragment = new EstimationMyListFragment();
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

    private ListView estimationListView;
    private EstimationMyListAdapter adapter;
    private ArrayList<EstimationMy> estimationList;            //class 멤버 변수

    @Override
    public void onActivityCreated(Bundle b){
        super.onActivityCreated(b);

        new BackgroundTask().execute();

        TextView estN = (TextView)getView().findViewById(R.id.estimationMyName);

        estimationListView =(ListView)getView().findViewById(R.id.estimationListView);
        estimationList = new ArrayList<EstimationMy>();
        adapter = new EstimationMyListAdapter(getContext().getApplicationContext(), estimationList);
        estimationListView.setAdapter(adapter);                 // adpater, list 설정
        estN.setText(EstimationActivity.estimateUser +"의 강의평");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_estlist, container, false);
    }


    class BackgroundTask extends AsyncTask<Void, Void, String> {

        String target;

        @Override
        protected void onPreExecute(){
            try {
                target = "http://rkdalswn1209.cafe24.com/EstimationMyList.php?estimateUser=" + URLEncoder.encode(EstimationActivity.estimateUser, "UTF-8");
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
                String estYear,estTerm,estContent,rating,estTitle,estProfessor;           // 모든 공지사항 list 추가
              while(count < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    estTitle =object.getString("estMyTitle");
                    estProfessor = object.getString("estMyProfessor");
                    rating = object.getString("estMyRating");
                    estContent = object.getString("estMyContent");
                    estTerm = object.getString("estMyTerm");
                    estYear = object.getString("estMyYear");
                    EstimationMy estimation = new EstimationMy(estTitle,estProfessor,rating, estYear,estTerm, estContent);

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
