package com.simplydiffrient.ClassroomQuestions.service;

import com.simplydiffrient.ClassroomQuestions.system.UnicastHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * AnswerReceiver
 *
 * @author Clay Diffrient (claydiffrient)
 * @version 1.0.0
 *
 * This class handles receiving answers from the various students.
 */
public class AnswerReceiver
{
    /**
     * List which holds the answers received.
     */
    ObservableList<Character> mRawAnswersReceived;

    /**
     * Handler for all the unicast type operations.
     * @see com.simplydiffrient.ClassroomQuestions.system.UnicastHandler
     */
    UnicastHandler mHandler;

    /**
     * Default constructor
     */
    public AnswerReceiver()
    {
        mRawAnswersReceived = FXCollections.observableArrayList();
        mHandler = new UnicastHandler(mRawAnswersReceived);
    }

    /**
     * Getter for the answers contained in the list.
     * @return The list of received answers
     */
    public ObservableList<Character> getResponses()
    {
        return mRawAnswersReceived;
    }

    /**
     * Wrapper for the listen method in the underlying handler.
     * Starts listening for responses.
     * @see com.simplydiffrient.ClassroomQuestions.system.UnicastHandler#listen()
     */
    public void listen()
    {
        mHandler.listen();
    }

    /**
     * Tells the handler to stop listening.
     * @see com.simplydiffrient.ClassroomQuestions.system.UnicastHandler#setListening(boolean)
     */
    public void stopListening()
    {
        mHandler.setListening(false);
    }

    /**
     * Tells the handler to start listening.
     * Note this is only needed if it was stopped previously.
     * @see com.simplydiffrient.ClassroomQuestions.system.UnicastHandler#setListening(boolean)
     */
    public void startListening()
    {
        mHandler.setListening(true);
    }






}
