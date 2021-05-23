package com.fishnco.trivia.data;

import com.fishnco.trivia.model.Question;

import java.util.ArrayList;

/*
 * Class that implements this interface will be able to call on class functions
 * Class functions will ensure that Object has been populated before returning back to another class
 */
public interface AnswerListAsyncResponse {
    void processFinished(ArrayList<Question> questionArrayList);
}
