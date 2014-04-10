package com.simplydiffrient.ClassroomQuestions.service;

import com.simplydiffrient.ClassroomQuestions.system.MulticastHandler;

import java.net.UnknownHostException;

/**
 * Handles all the dealings with sending questions from the teacher to students.
 *
 * @author Clay Diffrient
 * @version 1.0.0
 */
public class QuestionSender
{
    /**
     * The underlying handler for the multicast.
     * @see com.simplydiffrient.ClassroomQuestions.system.MulticastHandler
     */
    MulticastHandler mMulticastHandler;

    /**
     * Constructor
     * @param pGroupNumber The group number to join
     */
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

    /**
     * Sends questions to all the subscribed students
     * @param pMessage The QuestionMessage to send.
     */
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
