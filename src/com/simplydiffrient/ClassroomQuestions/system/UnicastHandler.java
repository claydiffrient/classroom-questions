package com.simplydiffrient.ClassroomQuestions.system;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.chart.XYChart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.List;

/**
 * Created by clay on 4/5/14.
 */
public class UnicastHandler
{
    private static final int LISTEN_PORT = 20001;
    private ObservableList<Character> mDataHolder;
    private InetAddress mHostAddress;
    private boolean mKeepListening;

    public UnicastHandler(ObservableList<Character> pDataHolder)
    {
        mDataHolder = pDataHolder;
        mKeepListening = true;
    }

    public UnicastHandler(InetAddress pHostAddress)
    {
        //TODO: Make this constructor a bit better
        //Only used for sending data.
        mHostAddress = pHostAddress;
    }

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

    public void setListening(boolean pValue)
    {
        mKeepListening = pValue;
    }



    public void listen()
    {
        try
        {
            DatagramSocket serverSocket = new DatagramSocket(null);
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
