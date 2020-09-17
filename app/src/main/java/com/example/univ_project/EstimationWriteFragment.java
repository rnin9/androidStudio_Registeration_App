package com.example.univ_project;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

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


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EstimationWriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EstimationWriteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EstimationWriteFragment() {
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
    public static EstimationWriteFragment newInstance(String param1, String param2) {
        EstimationWriteFragment fragment = new EstimationWriteFragment();
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

    private ArrayAdapter writeYearAdapter;
    private Spinner writeYearSpinner;
    private ArrayAdapter writeTermAdapter;
    private Spinner writeTermSpinner;
    private AlertDialog dialog;


    @Override
    public void onActivityCreated(Bundle b){
        super.onActivityCreated(b);

        writeYearSpinner = (Spinner) getView().findViewById(R.id.estimationYearSpinner);
        writeTermSpinner = (Spinner) getView().findViewById(R.id.estimationTermSpinner);
        final String[] dataY = getResources().getStringArray(R.array.year);
        final String[] dataT = getResources().getStringArray(R.array.term);

        writeYearAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item, dataY);
        writeTermAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item, dataT);


        writeYearSpinner.setAdapter(writeYearAdapter);
        writeTermSpinner.setAdapter(writeTermAdapter);

        final EditText estText = (EditText)getView().findViewById(R.id.estimationWritingContent);
        final RatingBar estWRating =(RatingBar)getView().findViewById(R.id.estimationWriteRating);
        Button completeButton = (Button)getView().findViewById(R.id.estimationCompleteButton);

            completeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String writeTitle = EstimationActivity.estimateTitle;
                        final String writeProfessor = EstimationActivity.estimateProfessor;
                        final String writeMajor = EstimationActivity.estimateMajor;
                        String writeYear = writeYearSpinner.getSelectedItem().toString();
                        String writeTerm = writeTermSpinner.getSelectedItem().toString();
                        final String writeContext = estText.getText().toString();
                        String estRating = String.valueOf(estWRating.getRating());
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try{
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    if(success){
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                        dialog = builder.setMessage("작성을 완료했습니다!")
                                                .setPositiveButton("확인",null)
                                                .create();
                                        dialog.show();
//                                        Intent intent = new Intent(getActivity(),EstimationActivity.class);
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//                                        startActivity(intent);

                                    }
                                    else{
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                        dialog = builder.setMessage("작성 오류가 발생했습니다.")    //실패시 계정확인
                                                .setNegativeButton("확인",null)
                                                .create();
                                        dialog.show();
                                    }
                                }
                                catch(Exception e){
                                    e.printStackTrace();
                                }
                            }
                        };
                        WriteRequest writeRequest = new WriteRequest(writeTitle,writeProfessor,estRating,writeYear,writeTerm, writeContext, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(getActivity());
                        queue.add(writeRequest);
                    }
                });
            }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_writing, container, false);
    }
}
