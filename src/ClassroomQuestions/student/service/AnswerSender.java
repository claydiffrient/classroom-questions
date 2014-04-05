package ClassroomQuestions.student.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Created by clay on 4/2/14.
 */
public class AnswerSender
    extends Service<Integer>
{
    @Override
    protected Task<Integer> createTask() {
        return new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                return null;
            }
        };
    }
}
