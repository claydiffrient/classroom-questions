package com.simplydiffrient.ClassroomQuestions.system;

import com.simplydiffrient.ClassroomQuestions.exceptions.InvalidGroupNumberException;

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