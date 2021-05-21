package com.fishnco.trivia;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.fishnco.trivia.data.Repository;

public class MainActivity extends AppCompatActivity {

    String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Repository repository = new Repository();
        repository.getQuestions();
    }
}