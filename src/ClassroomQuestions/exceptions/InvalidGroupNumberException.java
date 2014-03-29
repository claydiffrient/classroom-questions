package ClassroomQuestions.exceptions;

/**
 * Created by clay on 3/28/14.
 */
public class InvalidGroupNumberException
    extends Exception
{
    public InvalidGroupNumberException()
    {
        super("The Group number must be between 1 and 255");
    }
}
