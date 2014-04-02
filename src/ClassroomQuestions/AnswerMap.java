package ClassroomQuestions;

import ClassroomQuestions.exceptions.AnswerNotFoundException;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by clay on 3/28/14.
 */
public class AnswerMap
{
    private Map<Character, String> mAnswers;
    private Map<Character, Integer> mAnswerCount;

    public AnswerMap()
    {
        mAnswers = new LinkedHashMap<Character, String>();
        mAnswerCount = new LinkedHashMap<Character, Integer>();
    }

    /**
     * Adds an answer into the Answer Map.
     * @param pLetter
     * @param pAnswerText
     */
    public void addAnswer(Character pLetter, String pAnswerText)
    {
        if (!mAnswers.containsKey(pLetter) && !mAnswerCount.containsKey(pLetter))
        {
            mAnswers.put(pLetter, pAnswerText);
            mAnswerCount.put(pLetter, 0);
        }
    }

    /**
     * Gets the total number of answers contained in the map.
     * @return int
     */
    public int getTotalAnswers()
    {
        return mAnswers.size();
    }

    //TODO Comment here.
    public Map<Character, String> getAllAnswerText()
    {
        return mAnswers;
    }

    /**
     * Adds a response to the statistics for an answer.
     * @param pAnswer
     * @throws AnswerNotFoundException
     */
    public void addResponseStats(Character pAnswer)
        throws AnswerNotFoundException
    {
        Integer value = mAnswerCount.get(pAnswer);
        if (value == null)
        {
            throw new AnswerNotFoundException();
        }
        mAnswerCount.put(pAnswer, value + 1);
    }

    /**
     * Gets the number of values for a given answer.
     * @param pAnswer
     * @return
     * @throws AnswerNotFoundException
     */
    public int getResponseStatsForAnswer(Character pAnswer)
        throws AnswerNotFoundException
    {
        Integer value = mAnswerCount.get(pAnswer);
        if (value == null)
        {
            throw new AnswerNotFoundException();
        };
        return value;
    }

    public void printAllResponseStats(PrintStream pPrintStream)
    {
        pPrintStream.println("\nAnswer Statistics:");
        Iterator itr = mAnswerCount.entrySet().iterator();
        Integer[] totalsArray = mAnswerCount.values().toArray(new Integer[0]);
        int totalSum = 0;
        for (int i : totalsArray)
        {
            totalSum += i;
        }
        while (itr.hasNext())
        {
            Map.Entry entry = (Map.Entry) itr.next();
            //TODO: Fix this logic so the print out is correct.
            /*pPrintStream.println("\t" + entry.getKey() + ":" + entry.getValue() + " out of " + mAnswerCount.size() +
                                 " which is " + (((int) entry.getValue() / totalSum) * 100) + "%"); */
        }
    }



}
