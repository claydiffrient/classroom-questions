package com.simplydiffrient.ClassroomQuestions.service;

import com.simplydiffrient.ClassroomQuestions.system.UnicastHandler;

import java.net.InetAddress;

/**
 * Created by clay on 4/6/14.
 */
public class AnswerSender
{
    UnicastHandler mUnicastHandler;

    public AnswerSender(InetAddress pHost)
    {
        mUnicastHandler = new UnicastHandler(pHost);
    }

    public void send(char response)
    {
        byte charAsByte = (byte) response;
        byte[] dataToSend = new byte[2];
        dataToSend[0] = charAsByte;
        mUnicastHandler.sendData(dataToSend);
    }

}
