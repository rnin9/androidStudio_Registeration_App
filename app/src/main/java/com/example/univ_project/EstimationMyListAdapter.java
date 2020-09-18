package com.example.univ_project;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.List;

public class EstimationMyListAdapter extends BaseAdapter {

    private Context context;
    private List<EstimationMy> estList;
    private AlertDialog dialog;
    private Fragment parent;

      public EstimationMyListAdapter(Context context, List<EstimationMy> estList, Fragment parent) {
        this.context = context;
        this.estList = estList;
        this.parent = parent;
    }

    public EstimationMyListAdapter(Context context, List<EstimationMy> estList) {
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
        View v = View.inflate(context, R.layout.my_estimation,null);
        TextView estTitle = (TextView)v.findViewById(R.id.estimationMyTitle);
        TextView estProfessor = (TextView)v.findViewById(R.id.estimationMyProfessor);
        RatingBar estRating = (RatingBar) v.findViewById(R.id.estimationMyRating);
        TextView estYear = (TextView)v.findViewById(R.id.estimationMyYear);
        TextView estTerm = (TextView)v.findViewById(R.id.estimationMyTerm);
        final TextView estContent = (TextView)v.findViewById(R.id.estimationMyContent);
        estRating.setRating(Float.parseFloat(estList.get(i).getEstMyRating()));

        estTitle.setText(estList.get(i).getEstTitle());
        estProfessor.setText(estList.get(i).getEstProfessor());
        estYear.setText(estList.get(i).getEstMyYear());
        estTerm.setText(estList.get(i).getEstMyTerm()+" 수강");
        estContent.setText(estList.get(i).getEstMyContent());

        return v;
    }
}
