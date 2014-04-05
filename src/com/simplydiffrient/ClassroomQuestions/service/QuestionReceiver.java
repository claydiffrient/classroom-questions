package com.simplydiffrient.ClassroomQuestions.service;

import com.simplydiffrient.ClassroomQuestions.system.MulticastHandler;

import java.net.UnknownHostException;

/**
 * Created by clay on 4/5/14.
 */
public class QuestionReceiver
{
    MulticastHandler mMulticastHandler;

    public QuestionReceiver(int pGroupNumber)
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

    public QuestionMessage getQuestion()
        throws Exception
    {
        String message = mMulticastHandler.getMessage();
        if (message.equals("ERROR"))
        {
            throw new Exception("Error getting message.");
        }
        return new QuestionMessage(message);
    }

}
