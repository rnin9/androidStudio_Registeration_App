package com.example.univ_project;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class WriteRequest extends StringRequest {

    final static private String URL ="http://rkdalswn1209.cafe24.com/EstimationWriting.php";
    private Map<String, String> parameters;

    public WriteRequest(String estTitle, String estProfessor, String estRating, String estYear, String estTerm, String estContent, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("estimateTitle", estTitle);
        parameters.put("estimateProfessor", estProfessor);
        parameters.put("estimateRating", estRating);
        parameters.put("estimateYear", estYear);
        parameters.put("estimateTerm", estTerm);
        parameters.put("estimateContent", estContent);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
