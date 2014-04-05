package ClassroomQuestions.student.service;

import ClassroomQuestions.system.IPAnswerDataPair;
import ClassroomQuestions.system.MulticastReceiver;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Created by clay on 4/1/14.
 */
public class QuestionGetter
    extends Service<IPAnswerDataPair>
{
    int mGroupNumber;

    public QuestionGetter(int pGroupNumber)
    {
        mGroupNumber = pGroupNumber;
    }

    protected Task<IPAnswerDataPair> createTask()
    {
        return new Task<IPAnswerDataPair>() {
            @Override
            protected IPAnswerDataPair call() throws Exception {
                MulticastReceiver receiver = new MulticastReceiver(mGroupNumber);
                while (true)
                {
                    return receiver.recieveData();
                }
            }
        };
    }
}
