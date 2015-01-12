package com.simplydiffrient.ClassroomQuestions.system;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * System level class to handle all multicast operations.
 */
public class MulticastHandler
{
    /**
     * The Multicast Group IP Address
     */
    private InetAddress mIPGroupAddress;

    /**
     * Multicast socket that will be bound to the Multicast IP Address
     */
    private MulticastSocket mSocket;

    /**
     * The port used for the multicast socket.
     */
    private final static int GROUP_PORT = Integer.parseInt(PropertyGetter.getInstance().getProperty("groupPort"));

    /**
     * Constructor.
     * @param pAddressNumber The last part of the IP for the multicast group.  Must be between 1 and 255.
     * @throws UnknownHostException
     */
    public MulticastHandler(int pAddressNumber)
        throws UnknownHostException
    {
        mIPGroupAddress = InetAddress.getByName(PropertyGetter.getInstance().getProperty("ipRange") + pAddressNumber);
        try
        {
            mSocket = new MulticastSocket(GROUP_PORT);
            mSocket.joinGroup(mIPGroupAddress);
            // Set up loopback so the same machine can receive the packet that was sent out.
            mSocket.setLoopbackMode(true);
        }
        catch (IOException ex)
        {
            System.out.println("Unable to join multicast group.");
            //TODO: Better error handling here is needed.
        }
    }

    /**
     * Sends a message over the multicast socket.
     * @param pMessage The message to send
     * @return True, if successful
     */
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

    /**
     * Gets the message received on the multicast socket
     * @return The message as a string.
     */
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
