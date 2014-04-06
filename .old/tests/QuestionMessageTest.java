package com.simplydiffrient.ClassroomQuestions.tests;

import com.simplydiffrient.ClassroomQuestions.AnswerMap;
import com.simplydiffrient.ClassroomQuestions.QuestionMessage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by clay on 3/29/14.
 */
public class QuestionMessageTest {

    QuestionMessage mMessage;

    @BeforeMethod
    public void setUp() throws Exception
    {
        AnswerMap answers = new AnswerMap();
        answers.addAnswer('A', "One");
        answers.addAnswer('B', "Two");
        answers.addAnswer('C', "Three");
        answers.addAnswer('D', "Four");

        mMessage = new QuestionMessage();
        mMessage.setQuestionText("What is this?");
        mMessage.setAnswers(answers);

    }

    @Test
    public void testToString() throws Exception
    {
        String actual = mMessage.toString();
        String expected = "Q:What is this?;A:One;B:Two;C:Three;D:Four;";
        System.out.println("Actual:" + actual);
        assert actual.equals(expected);
    }
}
