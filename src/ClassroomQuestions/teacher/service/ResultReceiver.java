package ClassroomQuestions.teacher.service;

import ClassroomQuestions.system.UnicastReceiver;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by clay on 4/2/14.
 */
public class ResultReceiver
    extends Service<String>
{

    UnicastReceiver mReceiver;

    public ResultReceiver()
    {
        mReceiver = new UnicastReceiver();
    }

    protected Task<String> createTask()
    {
        return new Task<String>() {
            @Override
            protected String call()
                    throws Exception
            {
                Socket connectionSocket = mReceiver.getAcceptedSocket();
                BufferedReader fromClient = new BufferedReader(
                        new InputStreamReader((connectionSocket.getInputStream())));
                return fromClient.readLine();
            }

        };
    }
}
