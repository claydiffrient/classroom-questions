package com.simplydiffrient.ClassroomQuestions.service;

import com.simplydiffrient.ClassroomQuestions.system.UnicastHandler;

import java.net.InetAddress;

/**
 * Handles all the sending of answers from students to the teacher.
 * @author Clay Diffrient
 * @version 1.0.0
 */
public class AnswerSender
{
    /**
     * Handler for the underlying unicast operations.
     * @see com.simplydiffrient.ClassroomQuestions.system.UnicastHandler
     */
    UnicastHandler mUnicastHandler;

    /**
     * Constructor
     * @param pHost Host to which answers should be sent.
     */
    public AnswerSender(InetAddress pHost)
    {
        mUnicastHandler = new UnicastHandler(pHost);
    }

    /**
     * Sends a response to the teacher.
     * @param response A single character response to the question.
     */
    public void send(char response)
    {
        byte charAsByte = (byte) response;
        // Use a two byte buffer to send the data in.
        // This could likely be reduced to a one byte buffer.
        byte[] dataToSend = new byte[2];
        dataToSend[0] = charAsByte;
        mUnicastHandler.sendData(dataToSend);
    }

}
