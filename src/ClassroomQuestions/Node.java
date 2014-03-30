package ClassroomQuestions;

import ClassroomQuestions.exceptions.InvalidGroupNumberException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by clay on 3/28/14.
 */
public class Node
{
    protected MulticastSocket mSocket;
    protected InetAddress mGroup;

    protected final static int PORT_NUMBER = 20000;

    public Node(int pNodeNumber)
        throws InvalidGroupNumberException
    {
        if ((pNodeNumber > 255) || (pNodeNumber < 1))
        {
            throw new InvalidGroupNumberException();
        }
        try
        {
            mSocket = new MulticastSocket(PORT_NUMBER);
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
