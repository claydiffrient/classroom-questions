package com.simplydiffrient.ClassroomQuestions;

import java.util.Map;

/**
 * Created by clay on 3/28/14.
 */
public class QuestionMessage
{
    private String mQuestionText;
    private AnswerMap mAnswers;


    public String getQuestionText()
    {
        return mQuestionText;
    }

    public void setQuestionText(String pQuestionText)
    {
        mQuestionText = pQuestionText;
    }

    public AnswerMap getAnswers()
    {
        return mAnswers;
    }

    public void setAnswers(AnswerMap pAnswers)
    {
        mAnswers = pAnswers;
    }

    @Override
    public String toString()
    {
        String message = "Q:" + mQuestionText + ";";
        Map<Character, String> answers = mAnswers.getAllAnswerText();
        Character[] keys = answers.keySet().toArray(new Character[answers.size()]);
        for (int i = 0; i < keys.length; i++)
        {
            message += keys[i] + ":" + answers.get(keys[i]) + ";";
        }
        return message;
    }
}
