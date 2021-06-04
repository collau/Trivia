package com.fishnco.trivia.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

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
}
