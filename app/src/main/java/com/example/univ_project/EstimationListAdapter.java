package com.example.univ_project;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class EstimationListAdapter extends BaseAdapter {

    private Context context;
    private List<Estimation> estList;

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
        TextView estContent = (TextView)v.findViewById(R.id.estimationContent);
        estRating.setRating(estList.get(i).getEstimationRating().floatValue());

        estYear.setText(estList.get(i).getEstimationYear()+"년도 ");
        estTerm.setText(estList.get(i).getEstimationTerm()+" 수강자");
        estContent.setText(estList.get(i).getEstimationContent());

        return v;
    }
}
