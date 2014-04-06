package com.simplydiffrient.ClassroomQuestions.system;

import com.simplydiffrient.ClassroomQuestions.exceptions.InvalidGroupNumberException;

import java.io.IOException;
import java.net.DatagramPacket;

/**
 * Created by clay on 4/1/14.
 */
public class MulticastReceiver
    extends Multicast
{
    public MulticastReceiver(int pGroupNumber)
        throws InvalidGroupNumberException
    {
        super(pGroupNumber);
    }

    public String receiveString()
    {
        byte[] buffer = new byte[1024];
        DatagramPacket data = new DatagramPacket(buffer, buffer.length);
        try
        {
            mSocket.receive(data);
        }
        catch (IOException ex)
        {
            //TODO: Implement better error handling.
        }
        return new String(data.getData());
    }

    public IPAnswerDataPair recieveData()
    {
        byte[] buffer = new byte[1024];
        DatagramPacket data = new DatagramPacket(buffer, buffer.length);
        try
        {
            mSocket.receive(data);
        }
        catch (IOException ex)
        {
            //TODO: Implement better error handling.
        }
        return new IPAnswerDataPair(data.getAddress(), new String(data.getData()));
    }
}
