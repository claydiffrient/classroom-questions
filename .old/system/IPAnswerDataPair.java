package com.simplydiffrient.ClassroomQuestions.system;

import java.net.InetAddress;

/**
 * Created by clay on 4/2/14.
 */
public class IPAnswerDataPair
{
    private InetAddress mIPAddress;
    private String mAnswerData;

    public IPAnswerDataPair (InetAddress pIPAddress, String pAnswerData)
    {
        mIPAddress = pIPAddress;
        mAnswerData = pAnswerData;
    }

    public InetAddress getIPAddress() {
        return mIPAddress;
    }

    public void setIPAddress(InetAddress pIPAddress) {
        mIPAddress = pIPAddress;
    }

    public String getAnswerData() {
        return mAnswerData;
    }

    public void setAnswerData(String pAnswerData) {
        mAnswerData = pAnswerData;
    }
}
