package com.simplydiffrient.ClassroomQuestions;

import com.simplydiffrient.ClassroomQuestions.exceptions.InvalidGroupNumberException;

import java.io.IOException;
import java.net.DatagramPacket;

/**
 * Created by clay on 3/28/14.
 */
public class StudentNode
    extends Node
        implements Runnable
{

    public StudentNode(int pGroupNumber)
            throws InvalidGroupNumberException
    {
        super(pGroupNumber);
    }

    public void run()
    {
        /**
         * Let's listen for some data.
         */
        byte[] buffer = new byte[1024];
        DatagramPacket data = new DatagramPacket(buffer, buffer.length);
        while (true)
        {
            try
            {
                mSocket.receive(data);
                setChanged();
                notifyObservers(new String(data.getData()));
            }
            catch (IOException ex)
            {
                // TODO: Handle this more gracefully.
            }


        }

    }
}
