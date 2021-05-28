package com.fishnco.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.fishnco.trivia.data.AnswerListAsyncResponse;
import com.fishnco.trivia.data.Repository;
import com.fishnco.trivia.databinding.ActivityMainBinding;
import com.fishnco.trivia.model.Question;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";
    private ActivityMainBinding binding;
    private int currentQuestionIndex = 0;
    List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        /*
        Repository repository = new Repository();
        List<Question> questionList = repository.getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {
                // A filled questionArrayList can then be accessed here

            }
        });
        */

        questionList = new Repository().getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {
                binding.textViewQuestionNo.setText(getString(R.string.text_questionNo, currentQuestionIndex+1, questionArrayList.size()));
                binding.textViewQuestion.setText(questionArrayList.get(currentQuestionIndex).getAnswer());
            }
        });

        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuestionIndex = (currentQuestionIndex + 1 ) % questionList.size();
                updateQuestion();
            }
        });

        binding.buttonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        binding.buttonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });
    }

    private void updateQuestion() {
        String question = questionList.get(currentQuestionIndex).getAnswer();
        binding.textViewQuestion.setText(question);
        binding.textViewQuestionNo.setText(getString(R.string.text_questionNo, currentQuestionIndex+1, questionList.size()));
    }

    private void checkAnswer(boolean userChoseCorrect) {
        boolean answer = questionList.get(currentQuestionIndex).isAnswerTrue();
        int snackMessageId = 0;
        if (userChoseCorrect == answer) {
            snackMessageId = R.string.answerCorrect;
        } else {
            snackMessageId = R.string.answerWrong;
        }
        Snackbar.make(binding.cardViewQuestion, snackMessageId, Snackbar.LENGTH_SHORT).show();
    }
}