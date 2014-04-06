package com.simplydiffrient.ClassroomQuestions.tests;

import com.simplydiffrient.ClassroomQuestions.AnswerMap;
import com.simplydiffrient.ClassroomQuestions.exceptions.AnswerNotFoundException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by clay on 3/28/14.
 */
public class AnswerMapTest {

    private AnswerMap mAnswers;

    @BeforeMethod
    public void setUp() throws Exception
    {
        mAnswers = new AnswerMap();
    }

    @Test
    public void testAddAnswer() throws Exception
    {
        mAnswers.addAnswer('A', "This is an answer");
        assert mAnswers.getTotalAnswers() == 1;
        mAnswers.addAnswer('B', "This is an answer");
        mAnswers.addAnswer('C', "This is an answer");
        mAnswers.addAnswer('D', "This is an answer");
        assert mAnswers.getTotalAnswers() == 4;
    }

    @Test
    public void testAddResponseStats() throws Exception
    {
        mAnswers.addAnswer('A', "This is an answer");
        mAnswers.addAnswer('B', "This is an answer");
        for (int i = 0; i < 10; i++)
        {
            mAnswers.addResponseStats('A');
            mAnswers.addResponseStats('A');
            mAnswers.addResponseStats('B');
        }
        try
        {
            assert mAnswers.getResponseStatsForAnswer('A') == 20;
            assert mAnswers.getResponseStatsForAnswer('B') == 10;
        }
        catch (AnswerNotFoundException ex)
        {
        }

    }

}
