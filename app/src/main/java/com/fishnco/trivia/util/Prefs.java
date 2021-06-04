package com.fishnco.trivia.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/*
 * Instantiate and create SharedPreferences
 */

public class Prefs {
    public static final String HISCORE = "hiScore";
    private SharedPreferences preferences;

    //Shared Preferences must be attached to a context
    public Prefs(Activity context) {
        this.preferences = context.getPreferences(Context.MODE_PRIVATE);
    }

    public void saveHighestScore(int score) {
        preferences.edit().putInt(HISCORE, score).apply();
    }

    public int getHighestScore() {
        return preferences.getInt(HISCORE, 0);
    }

    public void setState(int index, int currentScore) {
        preferences.edit().putInt("trivia_state", index).apply();
        preferences.edit().putInt("currentScore", currentScore).apply();
    }

    public HashMap<String, Integer> getState() {
        HashMap<String, Integer> stateDetails = new HashMap<>();
        stateDetails.put("trivia_state", preferences.getInt("trivia_state", 0));
        stateDetails.put("currentScore", preferences.getInt("currentScore", 0));

        return stateDetails;
    }
}
