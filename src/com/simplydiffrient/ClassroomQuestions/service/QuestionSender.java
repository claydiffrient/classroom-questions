package com.simplydiffrient.ClassroomQuestions.service;

import com.simplydiffrient.ClassroomQuestions.system.MulticastHandler;

import java.net.UnknownHostException;

/**
 * Created by clay on 4/5/14.
 */
public class QuestionSender
{
    MulticastHandler mMulticastHandler;

    public QuestionSender(int pGroupNumber)
    {
        try
        {
            mMulticastHandler = new MulticastHandler(pGroupNumber);
        }
        catch (UnknownHostException ex)
        {
            //TODO: Better error handling here.
        }
    }

    public void sendQuestion(QuestionMessage pMessage)
    {
        if (mMulticastHandler.sendMessage(pMessage.toString()))
        {
            System.out.println("Question sent.");
        }
        else
        {
            System.out.println("Question failed to send.");
        }
    }
}
