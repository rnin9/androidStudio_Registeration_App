package com.example.univ_project;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

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
import java.util.List;

public class EstimationListAdapter extends BaseAdapter {

    private Context context;
    private List<Estimation> estList;
    private AlertDialog dialog;
    private Fragment parent;

      public EstimationListAdapter(Context context, List<Estimation> estList, Fragment parent) {
        this.context = context;
        this.estList = estList;
        this.parent = parent;
    }

    public EstimationListAdapter(Context context, List<Estimation> estList) {
        this.context = context;
        this.estList = estList;
    }

    @Override
    public int getCount() {
        return estList.size();
    }

    @Override
    public Object getItem(int i) {
        return estList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) { //view 생성
        View v = View.inflate(context, R.layout.estimation,null);
        RatingBar estRating = (RatingBar) v.findViewById(R.id.estimationRating);
        TextView estYear = (TextView)v.findViewById(R.id.estimationYear);
        TextView estTerm = (TextView)v.findViewById(R.id.estimationTerm);
        final TextView estContent = (TextView)v.findViewById(R.id.estimationContent);
        estRating.setRating(Float.parseFloat(estList.get(i).getEstimationRating()));

        estYear.setText(estList.get(i).getEstimationYear());
        estTerm.setText(estList.get(i).getEstimationTerm()+" 수강자");
        estContent.setText(estList.get(i).getEstimationContent());


        return v;
    }
}
