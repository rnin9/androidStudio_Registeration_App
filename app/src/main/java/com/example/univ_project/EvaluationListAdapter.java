package com.example.univ_project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
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

public class EvaluationListAdapter extends BaseAdapter {

    private Context context;
    private List<Evaluation> evalList;
    public static int evalCount= 0;

    public EvaluationListAdapter(Context context, List<Evaluation> evalList) {
        this.context = context;
        this.evalList = evalList;
        evalCount = 0;
    }

    @Override
    public int getCount() {
        return evalList.size();
    }

    @Override
    public Object getItem(int i) {
        return evalList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) { //view 생성
        View v = View.inflate(context, R.layout.evaluation,null);
        TextView evalTitle = (TextView) v.findViewById(R.id.evalTitle);
        TextView evalProfessor = (TextView) v.findViewById(R.id.evalProfessor);
        TextView evalMajor = (TextView) v.findViewById(R.id.evalMajor);

        evalTitle.setText(evalList.get(i).getEvalTitle());
        evalProfessor.setText(evalList.get(i).getEvalProfessor());
        evalMajor.setText(" [" + evalList.get(i).getEvalMajor()+"]");

        v.setTag(evalList.get(i).getEvalTitle());

        return v;
    }
}
