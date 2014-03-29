package ClassroomQuestions;

import ClassroomQuestions.exceptions.InvalidGroupNumberException;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by clay on 3/28/14.
 */
public class TeacherNode
    extends  Node
    implements Runnable
{
    QuestionMessage mQuestionMessage;

    public TeacherNode(int pGroupNumber)
            throws InvalidGroupNumberException
    {
        super(pGroupNumber);
        mQuestionMessage = new QuestionMessage();
    }


    public void getQuestionMessage()
    {
        // TODO: Get more answers working
        // TODO: Implement length restrictions (question = 300 chars, answer = 50 chars)
        AnswerMap answers = new AnswerMap();
        Scanner input = new Scanner(System.in);
        Character keyVal = 'C';
        boolean end = false;

        System.out.println("What question do you want to ask?");
        String question = input.nextLine();
        System.out.println("Please enter possible answers:");

        System.out.print("A:");
        answers.addAnswer('A', input.nextLine());
        System.out.print("B:");
        answers.addAnswer('B', input.nextLine());
        while (!end)
        {
            System.out.print("Are there more answers? (Y/N)");
            char selection = 'z';
            try
            {
                System.out.println(System.in.toString());
                selection = (char) System.in.read();
                System.in.reset();
            }
            catch (IOException ex)
            {
                System.out.println("Invalid input");
            }
            if (selection == 'n')
            {
                end = true;
            }
            else
            {
                System.out.print(keyVal + ":");
                answers.addAnswer(keyVal, input.nextLine());
            }
        }

        mQuestionMessage.setAnswers(answers);
        mQuestionMessage.setQuestionText(question);
    }

    public void run()
    {
        getQuestionMessage();
    }

    public static void main(String[] args)
    {
        try
        {
            new TeacherNode(Integer.parseInt(args[0])).run();
        }
        catch (Exception ex)
        {
            System.out.println("Error:" + ex.getMessage());
            System.out.println("Usage: TeacherNode {group number}");
        }

    }
}
