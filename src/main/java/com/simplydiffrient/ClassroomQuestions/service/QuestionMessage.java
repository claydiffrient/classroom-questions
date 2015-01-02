package com.simplydiffrient.ClassroomQuestions.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents a question message.  It follows the format specified for the
 * Classroom Questions Protocol which is as follows:
 *
 *  Q:{Question asked here};A:{Answer A};B:{Answer B};C:{Answer C};D:{Answer D};R:{Teacher IP Address}
 *
 *  @author Clay Diffrient
 *  @version 1.0.0
 */
public class QuestionMessage
{
    private String mQuestionText;
    private Map<String, String> mAnswers;
    private InetAddress mResponseAddress;

    /**
     * Constructor for a QuestionMessage from the various parts
     * @param pQuestionText The text of the question
     * @param pAnswers A map of answers to the question
     * @param pResponseAddress The IP Address of the response.
     */
    public QuestionMessage(String pQuestionText, Map<String, String> pAnswers, InetAddress pResponseAddress)
    {
        mQuestionText = pQuestionText;
        mAnswers = pAnswers;
        mResponseAddress = pResponseAddress;
    }

    /**
     * Constructor from a string.
     * @param pMessage The string version of the message.
     */
    public QuestionMessage(String pMessage)
    {
        mAnswers = new LinkedHashMap<String, String>();
        // Get rid of extraneous information
        // int lastSemicolon = message.lastIndexOf(';');
        pMessage = pMessage.trim();
        // Parse out the information.
        String[] tokens = pMessage.split(";");
        for (String token : tokens)
        {
            String[] splitToken = token.split(":");
            if (splitToken[0].equals("Q"))
            {
                mQuestionText = splitToken[1];
            }
            else if (splitToken[0].equals("R"))
            {
                try
                {
                    mResponseAddress = InetAddress.getByName(splitToken[1]);
                }
                catch (UnknownHostException ex)
                {
                    // TODO: Add better error handling here.
                }
            }
            else
            {
                mAnswers.put(splitToken[0], splitToken[1]);
            }
        }
    }

    /**
     * Returns the text of the question.
     * @return Question Text
     */
    public String getQuestionText()
    {
        return mQuestionText;
    }

    /**
     * Returns the map of answers.
     * @return Answer Map
     */
    public Map<String, String> getAnswers()
    {
        return mAnswers;
    }

    /**
     * Returns the address to which a reponse will be sent.
     * @return Response Address
     */
    public InetAddress getResponseAddress()
    {
        return mResponseAddress;
    }


    /**
     * Outputs the QuestionMessage in the proper format.
     * @return String representation of the Question Message
     */
    @Override
    public String toString()
    {
        String questionMessage = "Q:" + mQuestionText + ";";
        for (Map.Entry<String, String> entry : mAnswers.entrySet())
        {
            questionMessage += entry.getKey() + ":" + entry.getValue() + ";";
        }
        questionMessage += "R:" + mResponseAddress.getHostAddress() + ";";
        return questionMessage;
    }
}
