package com.fishnco.trivia.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.fishnco.trivia.controller.AppController;
import com.fishnco.trivia.model.Question;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/*
 * A repository that will contain all the questions
 * Call the API, get the questions, then fill up the arrayList which is used to provide questions to the UI
 */

public class Repository {

    ArrayList<Question> questionArrayList = new ArrayList<>();
    String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";

    public List<Question> getQuestions( final AnswerListAsyncResponse callBack) { // Pass a callback class as a parameter
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++)
                {
                    try {
                        JSONArray questionArray = response.getJSONArray(i);

                        // Question question = new Question(questionArray.getString(0), questionArray.getBoolean(1));

                        Question question = new Question();
                        question.setAnswer(questionArray.getString(0));
                        question.setAnswerTrue(questionArray.getBoolean(1));

                        questionArrayList.add(question);
                    } catch (JSONException e)
                    {
                        Log.e("Repository", "Exception parsing JSONArray");
                        e.printStackTrace();
                    }
                }

                // Ensure that questionArrayList is filled to be passed back to calling class
                if (null != callBack)
                    callBack.processFinished(questionArrayList);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

        return questionArrayList;
    }
}
