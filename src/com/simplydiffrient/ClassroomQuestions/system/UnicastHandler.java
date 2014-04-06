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

    public UnicastHandler(ObservableList<Character> pDataHolder)
    {
        mDataHolder = pDataHolder;
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


    public void listen()
    {
        try
        {
            DatagramSocket serverSocket = new DatagramSocket(LISTEN_PORT);
            byte[] receiveData = new byte[1024];
            while (true)
            {
                DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(packet);
                String data = new String(packet.getData());
                data = data.trim();
                mDataHolder.add(data.charAt(0));
                receiveData = new byte[1024];
            }

        }
        catch (Exception ex)
        {
            //TODO: Better error handling.
            ex.printStackTrace();
        }

    }


    /*InetAddress mIPAddress;
    private static final int LISTEN_PORT = 20001;
    XYChart.Series mResponseHolder;

    public UnicastHandler(InetAddress pIPAddress)
    {
        mIPAddress = pIPAddress;
    }

    public UnicastHandler(XYChart.Series pResponseHolder)
    {
        mResponseHolder = pResponseHolder;
    }

    public void startListeningServer()
    {
        try
        {
            ServerSocket acceptSocket = new ServerSocket(LISTEN_PORT);
            while (true)
            {
                Socket acceptedSocket = acceptSocket.accept();
                Response resp = new Response(acceptedSocket);
                Thread thread = new Thread(resp);
                thread.setDaemon(true);
                thread.start();
            }

        }
        catch (IOException ex)
        {
            //TODO: Add better error handling here.
            ex.printStackTrace();
        }

    }

    private class Response
        extends Task<Void>
    {
        Socket mSocket;

        public Response(Socket pSocket)
        {
            mSocket = pSocket;
        }


        @Override
        protected Void call() throws Exception {
            BufferedReader fromClient = new BufferedReader(
                    new InputStreamReader((mSocket.getInputStream())));
            char response = (char) fromClient.read();
            //TODO: Patch this up some so that it doesn't have an upstream dependency

            return null;
        }
    }

    public void sendMessage(String pMessage)
    {

    }*/

}
