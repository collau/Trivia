package com.fishnco.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.fishnco.trivia.data.AnswerListAsyncResponse;
import com.fishnco.trivia.data.Repository;
import com.fishnco.trivia.databinding.ActivityMainBinding;
import com.fishnco.trivia.model.Question;
import com.fishnco.trivia.util.Prefs;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String SCORE_PREFS = "score_prefs";

    String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";
    private ActivityMainBinding binding;
    private int currentQuestionIndex = 0;
    private int currentScore = 0;
    private int highScore = 0;
    private Prefs prefs;
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

        //Get high score from sharedPrefs
        prefs = new Prefs(this);
        highScore = prefs.getHighestScore();

        binding.textViewHiScore.setText(getString(R.string.text_hiScore, highScore));
        binding.textViewCurrent.setText(getString(R.string.text_current, currentScore));

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

    @Override
    protected void onPause() {
        //Save high score in shared preferences
        prefs.saveHighestScore(currentScore);

        super.onPause();
    }

    private void updateQuestion() {
        String question = questionList.get(currentQuestionIndex).getAnswer();
        binding.textViewQuestion.setText(question);
        binding.textViewQuestionNo.setText(getString(R.string.text_questionNo, currentQuestionIndex+1, questionList.size()));
    }

    private void checkAnswer(boolean userChoseCorrect) {
        boolean answer = questionList.get(currentQuestionIndex).isAnswerTrue();
        int snackMessageId;
        if (userChoseCorrect == answer) {
            snackMessageId = R.string.answerCorrect;
            currentScore++;
            updateScores();
            fadeAnimation();
        } else {
            snackMessageId = R.string.answerWrong;
            currentScore--;
            currentScore = Math.max(currentScore, 0);
            updateScores();
            shakeAnimation();
        }
        Snackbar.make(binding.cardViewQuestion, snackMessageId, Snackbar.LENGTH_SHORT).show();
    }

    private void updateScores() {

        if (currentScore > highScore)
        {
            //Make current score the high score
            highScore = currentScore;

            //Update high score as current score
            binding.textViewHiScore.setText(getString(R.string.text_hiScore, currentScore));
        }

        //Update current score
        binding.textViewCurrent.setText(getString(R.string.text_current, currentScore));

    }

    private void shakeAnimation() {
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake_animation);
        binding.cardViewQuestion.startAnimation(shake);

        // Add functions when animation is happening
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.textViewQuestion.setTextColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.textViewQuestion.setTextColor(Color.WHITE);
                //Activate next question after answer feedback
                binding.buttonNext.performClick();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void fadeAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(300);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE); // back to first state

        binding.cardViewQuestion.startAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.textViewQuestion.setTextColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.textViewQuestion.setTextColor(Color.WHITE);
                //Activate next question after answer feedback
                binding.buttonNext.performClick();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}