/****************************************************************************
* Program:
*    Lab FinalProj, Computer Communication and Networking
*    Brother Jones, CS 460
* Author:
*    Clay Diffrient
* Summary:
*    This file contains the source for the Classroom Questions application.
*    It operates using the Classroom Questions Protocol (CQP) in order to
*    accomplish its work.  The protocol will be explained in the next
*    comment.  The application works with many clients and a psuedoserver.
*    The server is really just another client connected, but it is the
*    teacher's node which will take interaction from the teacher, receive
*    return messages from the students, and present results to the teacher.
*
*    Future improvements may include:
*      - Adding a GUI
*      - Keeping user statistics
****************************************************************************/

/****************************************************************************
* Classroom Questions Protocol:
*   Overview:
*     1. Teacher node joins a multicast group
*     2. Sends a message to all students in the same group
*     3. Students answer with a response
*     4. The student results are received by the teacher node
*   Specifics:
*     Question Message Format:
*       Q: Question Text (300 char max);
*       A: Answer A (50 char max);
*       B: Answer B (50 char max);
*       C: Answer C (50 char max);
*       D: Answer D (50 char max);
*          * NOTE: No newline is implied here, a ';' ends each section.
*     Answer Message Format:
*       A, B, C, or D
*          * NOTE: This should just be a single letter responding to the
*                  results.
****************************************************************************/

/**
 * The "server" code.  It's really just the teacher node
 */
public class ClassroomQuestionsServer
   implements Runnable
{

   //MulticastSocket
   /**
    * Constructor
    */
   public ClassroomQuestionsServer(int nodeNumber)
   {

   }

   public static void main(String[] args)
   {

   }
}
