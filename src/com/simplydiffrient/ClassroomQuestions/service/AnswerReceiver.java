package com.simplydiffrient.ClassroomQuestions.service;

import com.simplydiffrient.ClassroomQuestions.system.MulticastHandler;
import com.simplydiffrient.ClassroomQuestions.system.UnicastHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.InetAddress;

/**
 * Created by clay on 4/5/14.
 */
public class AnswerReceiver
{
    ObservableList<Character> mRawAnswersReceived;
    UnicastHandler mHandler;

    public AnswerReceiver()
    {
        mRawAnswersReceived = FXCollections.observableArrayList();
        mHandler = new UnicastHandler(mRawAnswersReceived);
        getResponses();
    }

    public ObservableList<Character> getResponses()
    {
        return mRawAnswersReceived;
    }

    public void listen()
    {
        mHandler.listen();
    }

    public void stopListening()
    {
        mHandler.setListening(false);
    }

    public void startListening()
    {
        mHandler.setListening(true);
    }






}
