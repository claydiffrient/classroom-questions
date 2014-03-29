package ClassroomQuestions;

import ClassroomQuestions.exceptions.InvalidGroupNumberException;

/**
 * Created by clay on 3/28/14.
 */
public class StudentNode
    extends Node
        implements Runnable
{

    public StudentNode(int pGroupNumber)
            throws InvalidGroupNumberException
    {
        super(pGroupNumber);
    }

    public void run()
    {

    }
}
