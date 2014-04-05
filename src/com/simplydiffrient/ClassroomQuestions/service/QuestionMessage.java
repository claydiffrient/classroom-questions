package com.simplydiffrient.ClassroomQuestions.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * Represents a question message.  It follows the format specified for the
 * Classroom Questions Protocol which is as follows:
 *
 *  Q:{Question asked here};A:{Answer A};B:{Answer B};C:{Answer C};D:{Answer D};R:{Teacher IP Address}
 */
public class QuestionMessage
{
    private String mQuestionText;
    private Map<String, String> mAnswers;
    private InetAddress mResponseAddress;

    /**
     * Constructor for a QuestionMessage from the various parts
     * @param pQuestionText
     * @param pAnswers
     * @param pResponseAddress
     */
    public QuestionMessage(String pQuestionText, Map<String, String> pAnswers, InetAddress pResponseAddress)
    {
        mQuestionText = pQuestionText;
        mAnswers = pAnswers;
        mResponseAddress = pResponseAddress;
    }

    /**
     * Constructor from a string.
     * @param message
     */
    public QuestionMessage(String message)
    {
        // Parse out the information.
        String[] tokens = message.split(";");
        for (int i = 0; i < tokens.length; i++)
        {
            String[] splitToken = tokens[i].split(":");
            if (splitToken[0] == "Q")
            {
                mQuestionText = splitToken[1];
            }
            else if (splitToken[0] == "R")
            {
                try
                {
                    mResponseAddress = InetAddress.getByName(splitToken[1]);
                }
                catch (UnknownHostException ex)
                {
                    // TODO: Add better error handling here.
                    // Perhaps even change it to
                }
            }
            else
            {
                mAnswers.put(splitToken[0], splitToken[1]);
            }
        }
    }

    public String getQuestionText()
    {
        return mQuestionText;
    }

    public Map<String, String> getAnswers()
    {
        return mAnswers;
    }

    public InetAddress getResponseAddress()
    {
        return mResponseAddress;
    }


    /**
     * Outputs the QuestionMessage in the proper format.
     * @return String
     */
    @Override
    public String toString()
    {
        String questionMessage = "Q:" + mQuestionText + ";";
        for (Map.Entry<String, String> entry : mAnswers.entrySet())
        {
            questionMessage += entry.getKey() + ":" + entry.getValue() + ";";
        }
        questionMessage += "R:" + mResponseAddress.getHostAddress();
        return questionMessage;
    }
}
