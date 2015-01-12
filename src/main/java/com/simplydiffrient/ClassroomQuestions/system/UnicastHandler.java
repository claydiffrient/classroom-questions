package com.simplydiffrient.ClassroomQuestions.system;

import javafx.collections.ObservableList;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * Handles the unicast operations for the application.
 *
 * @author Clay Diffrient
 * @version 1.0.0
 */
public class UnicastHandler
{
    /**
     * The port for receiving unicast messages.
     */
    private static final int LISTEN_PORT = Integer.parseInt(PropertyGetter.getInstance().getProperty("listenPort"));;

    /**
     * List in which to hold the data received.
     */
    private ObservableList<Character> mDataHolder;

    /**
     * The address of the host data is being sent to.
     */
    private InetAddress mHostAddress;

    /**
     * Should the server be listening for messages or not?
     */
    private boolean mKeepListening;

    /**
     * Constructor, sets the data holder.
     * @param pDataHolder Observable list which will be populated with the data.
     */
    public UnicastHandler(ObservableList<Character> pDataHolder)
    {
        mDataHolder = pDataHolder;
        mKeepListening = true;
    }

    /**
     * Constructor, sets the host address.
     * Only used when the handler will only be used for sending data.
     * @param pHostAddress The address of the host needing data
     */
    public UnicastHandler(InetAddress pHostAddress)
    {
        //TODO: Make this constructor a bit better
        //Only used for sending data.
        mHostAddress = pHostAddress;
    }

    /**
     * Send data to the host server (teacher)
     * @param data The actual data to be sent.
     */
    public void sendData(byte[] data)
    {
        try
        {
            DatagramSocket clientSocket = new DatagramSocket();
            DatagramPacket packet = new DatagramPacket(data, data.length, mHostAddress, LISTEN_PORT);
            clientSocket.send(packet);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            //TODO: Better error handling.
        }
    }

    /**
     * Sets the listening value.
     * @param pValue True, to keep listening or false to stop it.
     */
    public void setListening(boolean pValue)
    {
        mKeepListening = pValue;
    }

    /**
     * Starts the server listening for connections.
     */
    public void listen()
    {
        try
        {
            DatagramSocket serverSocket = new DatagramSocket(null);
            // Make it so we don't get binding errors when a new question is asked.
            serverSocket.setReuseAddress(true);
            serverSocket.bind(new InetSocketAddress(LISTEN_PORT));
            byte[] receiveData = new byte[1024];
            mKeepListening = true;
            while (mKeepListening)
            {
                DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(packet);
                String data = new String(packet.getData());
                data = data.trim();
                mDataHolder.add(data.charAt(0));
                receiveData = new byte[1024];
            }
            //Close the socket after the loop completes.
            serverSocket.close();

        }
        catch (Exception ex)
        {
            //TODO: Better error handling.
            ex.printStackTrace();
        }

    }

}
