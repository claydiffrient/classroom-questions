package ClassroomQuestions.system;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by clay on 4/1/14.
 */
public class UnicastSender
    extends Unicast
{
    InetAddress mDestinationIP;
    Socket mClientSocket;


    public UnicastSender(InetAddress pDestinationIP)
    {
        mDestinationIP = pDestinationIP;
        try
        {
            mClientSocket = new Socket(mDestinationIP, REPLY_PORT);
        }
        catch (IOException ex)
        {
            System.out.println("Unable to create socket.");
            ex.printStackTrace();
        }
    }

    public int sendCharacter(char character)
            throws IOException
    {
        DataOutputStream out = new DataOutputStream(mClientSocket.getOutputStream());
        out.writeChar(character);
        BufferedReader serverIn = new BufferedReader(new InputStreamReader(mClientSocket.getInputStream()));
        String statusCode = serverIn.readLine();
        return Integer.parseInt(statusCode);
    }

}
