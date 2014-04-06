package com.simplydiffrient.ClassroomQuestions.system;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by clay on 4/1/14.
 */
public class UnicastReceiver
    extends Unicast
{

    ServerSocket mSocket;

    public UnicastReceiver()
    {
        super();
        try
        {
            mSocket = new ServerSocket(REPLY_PORT);
        }
        catch (IOException ex)
        {
            System.out.println("Unable to create socket");
            ex.printStackTrace();
        }
    }

    public Socket getAcceptedSocket()
        throws IOException
    {
        return mSocket.accept();
    }

}
