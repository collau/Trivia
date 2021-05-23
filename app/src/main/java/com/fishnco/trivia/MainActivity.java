package com.fishnco.trivia;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.fishnco.trivia.data.AnswerListAsyncResponse;
import com.fishnco.trivia.data.Repository;
import com.fishnco.trivia.model.Question;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Repository repository = new Repository();
        List<Question> questionList = repository.getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {
                // A filled questionArrayList can then be accessed here

            }
        });
    }
}