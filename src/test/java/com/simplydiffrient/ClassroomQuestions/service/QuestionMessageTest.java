package com.simplydiffrient.ClassroomQuestions.service;

import com.simplydiffrient.ClassroomQuestions.service.QuestionMessage;

import java.net.InetAddress;
import java.util.Map;
import java.util.HashMap;

import org.testng.*;
import org.testng.annotations.*;



public class QuestionMessageTest
{

  @BeforeClass
  public void setUp()
  {

  }


  @Test()
  public void testStringConstructor()
  {
    QuestionMessage test = new QuestionMessage("Q:A question");
    Assert.assertNotNull(test);
  }

  @Test()
  public void testNormalConstructor()
  {
    InetAddress returnAddress;
    String text = "What is the square root of 9?";
    Map<String,String> answers = new HashMap<String, String>();
    answers.put("A", "1");
    answers.put("B", "3");
    answers.put("C", "8");
    answers.put("D", "5");
    try
    {
      returnAddress = InetAddress.getLocalHost();
    }
    catch (Exception ex)
    {
      returnAddress = null;
    }


    QuestionMessage test = new QuestionMessage(text, answers, returnAddress);
    Assert.assertNotNull(test);
  }
}