package com.simplydiffrient.ClassroomQuestions;

import com.simplydiffrient.ClassroomQuestions.exceptions.InvalidGroupNumberException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Observable;

/**
 * Created by clay on 3/28/14.
 */
public class Node
    extends Observable
{
    protected MulticastSocket mSocket;
    protected InetAddress mGroup;

    protected final static int GROUP_PORT = 20000;
    protected final static int REPLY_PORT = 20001;

    public Node(int pNodeNumber)
        throws InvalidGroupNumberException
    {
        if ((pNodeNumber > 255) || (pNodeNumber < 1))
        {
            throw new InvalidGroupNumberException();
        }
        try
        {
            mSocket = new MulticastSocket(GROUP_PORT);
            mGroup = InetAddress.getByName("224.0.0" + pNodeNumber);
            mSocket.joinGroup(mGroup);
        }
        catch (IOException ex)
        {
            System.out.println("The socket could not be created.");
            System.exit(-1);
        }

    }
}
