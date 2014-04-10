package com.simplydiffrient.ClassroomQuestions.service;

import com.simplydiffrient.ClassroomQuestions.system.MulticastHandler;

import java.net.UnknownHostException;

/**
 * Represents the actions needed to receive questions.
 * Wraps the methods of the system level MulticastHandler
 *
 * @author Clay Diffrient
 * @version 1.0.0
 *
 * @see com.simplydiffrient.ClassroomQuestions.system.MulticastHandler
 */
public class QuestionReceiver
{
    /**
     * The underlying MulticastHandler
     * @see com.simplydiffrient.ClassroomQuestions.system.MulticastHandler
     */
    MulticastHandler mMulticastHandler;

    /**
     * Constructor
     * @param pGroupNumber The group to join
     */
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


    /**
     * Returns the question received from the teacher
     * @return QuestionMessage based on the message received
     * @throws Exception
     */
    public QuestionMessage getQuestion()
        throws Exception
    {
        String message = mMulticastHandler.getMessage();
        System.out.println(message);
        if (message.equals("ERROR"))
        {
            throw new Exception("Error getting message.");
        }
        return new QuestionMessage(message);
    }

}
