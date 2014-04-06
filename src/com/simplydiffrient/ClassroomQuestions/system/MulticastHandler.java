package com.simplydiffrient.ClassroomQuestions.system;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * Created by clay on 4/5/14.
 */
public class MulticastHandler
{
    InetAddress mIPGroupAddress;
    MulticastSocket mSocket;

    protected final static int GROUP_PORT = 20000;

    public MulticastHandler(int pAddressNumber)
        throws UnknownHostException
    {
        mIPGroupAddress = InetAddress.getByName("224.0.0." + pAddressNumber);
        try
        {
            mSocket = new MulticastSocket(GROUP_PORT);
            mSocket.joinGroup(mIPGroupAddress);
            mSocket.setLoopbackMode(true);
        }
        catch (IOException ex)
        {
            System.out.println("Unable to join multicast group.");
            //TODO: Better error handling here is needed.
        }
    }

    public boolean sendMessage(String pMessage)
    {
        byte[] bytes = pMessage.getBytes();
        DatagramPacket data = new DatagramPacket(bytes, 0, bytes.length, mIPGroupAddress, GROUP_PORT);
        try
        {
            mSocket.send(data);
            return true;
        }
        catch (IOException e)
        {
            System.out.println("Unable to send message.");
            e.printStackTrace();
            return false;
        }
    }

    public String getMessage()
    {
        byte[] buffer = new byte[1024];
        DatagramPacket data = new DatagramPacket(buffer, buffer.length);
        try
        {
            mSocket.receive(data);
            System.out.println("Received:" + new String(data.getData()));
            return new String(data.getData());
        }
        catch (IOException ex)
        {
            //TODO: Better error handling here.
            return "ERROR";
        }
    }


}
