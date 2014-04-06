package com.simplydiffrient.ClassroomQuestions.system;

import com.simplydiffrient.ClassroomQuestions.exceptions.InvalidGroupNumberException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by clay on 4/1/14.
 */
public class Multicast {
    protected MulticastSocket mSocket;
    protected InetAddress mGroup;

    protected final static int GROUP_PORT = 20000;

    public Multicast(int pGroupNumber)
        throws InvalidGroupNumberException
    {
        if ((pGroupNumber > 255) || (pGroupNumber < 1))
        {
            throw new InvalidGroupNumberException();
        }
        try
        {
            mSocket = new MulticastSocket(GROUP_PORT);
            mGroup = InetAddress.getByName("224.0.0." + pGroupNumber);
            mSocket.joinGroup(mGroup);
        }
        catch (IOException ex)
        {
            System.out.println("The socket could not be created.");
            System.exit(-1);
        }
    }
}
