package ClassroomQuestions;

import ClassroomQuestions.exceptions.AnswerNotFoundException;

import java.io.*;
import java.net.Socket;

/**
 * Created by clay on 3/31/14.
 */
public class StudentResponse
    implements Runnable
{
    Socket mSocket;
    AnswerMap mAnswers;

    public StudentResponse(Socket pSocket, AnswerMap pAnswers)
    {
        mSocket = pSocket;
        mAnswers = pAnswers;
    }

    private void processRequest()
        throws IOException
    {
        InputStream is = mSocket.getInputStream();
        DataOutputStream os = new DataOutputStream(mSocket.getOutputStream());

        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        char response = br.readLine().charAt(0);
        try
        {
            mAnswers.addResponseStats(response);
            os.writeBytes("200");
        }
        catch (AnswerNotFoundException ex)
        {
            os.writeBytes("400");
        }
    }

    public void run()
    {
        try
        {
            processRequest();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }
}
