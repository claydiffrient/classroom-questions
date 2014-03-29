package ClassroomQuestions.exceptions;

/**
 * Created by clay on 3/28/14.
 */
public class AnswerNotFoundException
        extends Exception
{
    /**
     * Constructor
     */
    public AnswerNotFoundException()
    {
        super("That answer was not found in our database.");
    }
}
