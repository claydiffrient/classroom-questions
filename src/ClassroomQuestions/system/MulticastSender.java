package ClassroomQuestions.system;

import ClassroomQuestions.exceptions.InvalidGroupNumberException;

/**
 * Created by clay on 4/1/14.
 */
public class MulticastSender
    extends Multicast
{
    public MulticastSender(int pGroupNumber) throws InvalidGroupNumberException {
        super(pGroupNumber);
    }
}
